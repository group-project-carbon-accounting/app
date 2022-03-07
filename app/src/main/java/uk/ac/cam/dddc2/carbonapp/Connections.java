package uk.ac.cam.dddc2.carbonapp;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


import uk.ac.cam.dddc2.carbonapp.datastores.Offset;
import uk.ac.cam.dddc2.carbonapp.datastores.Transaction;
import uk.ac.cam.dddc2.carbonapp.datastores.UserData;
import uk.ac.cam.dddc2.carbonapp.exceptions.OffsetFailedException;
import uk.ac.cam.dddc2.carbonapp.exceptions.TransactionUpdateFailedException;


// TODO: Potentially replace timestamp datatype with Date instead of String after checking what format the server returns
public class Connections {
    private static final String serverURL = "http://10.30.62.138:8889";

    public static UserData getUserData() {
        try {
            // request is of the form /user/get/{user_id}
            URL url = new URL(serverURL + "/user/get/1");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            //connection.setReadTimeout(5000);

            String line;
            StringBuilder responseString = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((line = reader.readLine()) != null) {
                responseString.append(line);
            }
            reader.close();

            String response = responseString.toString();

            // {"carbon_offset": 0, "carbon_cost": 0, "name": "Albert"}

            /* At this point response should have the form:

            {
                "carbon_offset": ,
                "carbon_cost: *,
                "name": *
             }

             */

            String name;
            int carbonCost;
            int carbonOffset;

            // Extracts Information from JSON format into UserData Object
            int firstPointer;
            int secondPointer;
            firstPointer = response.indexOf(':');
            secondPointer = response.indexOf(',', firstPointer + 1);
            carbonOffset = Integer.parseInt(response.substring(firstPointer + 2, secondPointer));

            firstPointer = response.indexOf(':', secondPointer);
            secondPointer = response.indexOf(',', secondPointer + 1);
            carbonCost = Integer.parseInt(response.substring(firstPointer + 2, secondPointer));

            firstPointer = response.indexOf(':', secondPointer);
            secondPointer = response.lastIndexOf('}');
            name = response.substring(firstPointer + 2, secondPointer);

            return new UserData(name, carbonCost, carbonOffset);

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Should never get here
        return new UserData("Error", 0, 0);
    }

    public static ArrayList<Transaction> getTransactionsForPeriod(int dayCount) {
        ArrayList<Transaction> returnValue = new ArrayList<>();
        try {
            // request is of the form /transaction/get_recent/{user_id}/{num_of_days}
            URL url = new URL(serverURL + "/transaction/get_recent/1/" + dayCount);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            String line;
            StringBuilder responseString = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((line = reader.readLine()) != null) {
                responseString.append(line);
            }
            reader.close();

            String response = responseString.toString();

            //{"carbon_cost": 0, "carbon_offset": 0, "transactions": [{"transaction_id": 1, "price": 1200, "carbon_cost_offset": 0, "vendor": 6, "timestamp": 1646263071}]}

            /* At this point response should have the form:

            {
                "carbon_cost: *,
                "carbon_offset": *
                "transactions":
                    [
                        {
                            "transaction_id": *,
                            "price": *,
                            "carbon_cost_offset": *,
                            "vendor": *,
                            "timestamp": *
                        },
                        {
                            ...
                        },
                        ...
                    ]
             }

             */


            boolean continueLoop = true;

            int firstPointer = response.indexOf('[');
            int secondPointer = response.indexOf('{', firstPointer);
            // secondPointer == -1 iff the transaction list is empty as ',' will never appear
            // after the first appearance of '['

            // this will loop until secondPointer == -1 which only happens once all transactions
            // have been covered as there will be no more appearances of ',' in the JSON
            while (secondPointer != -1) {
                int transactionID;
                int price;
                int carbonCostOffset;
                String vendor;
                String timestamp;

                firstPointer = response.indexOf(':', secondPointer);
                secondPointer = response.indexOf(',', secondPointer + 1);
                transactionID = Integer.parseInt(response.substring(firstPointer + 2, secondPointer));

                firstPointer = response.indexOf(':', secondPointer);
                secondPointer = response.indexOf(',', secondPointer + 1);
                price = Integer.parseInt(response.substring(firstPointer + 2, secondPointer));

                firstPointer = response.indexOf(':', secondPointer);
                secondPointer = response.indexOf(',', secondPointer + 1);
                carbonCostOffset = Integer.parseInt(response.substring(firstPointer + 2, secondPointer));

                firstPointer = response.indexOf(':', secondPointer);
                secondPointer = response.indexOf(',', secondPointer + 1);
                vendor = response.substring(firstPointer + 2, secondPointer);

                firstPointer = response.indexOf(':', secondPointer);
                secondPointer = response.indexOf('}', secondPointer + 1);
                timestamp = response.substring(firstPointer + 2, secondPointer);

                returnValue.add(new Transaction(transactionID, price, carbonCostOffset, vendor, timestamp));

                secondPointer = response.indexOf(',', secondPointer);
            }

            return returnValue;

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Will only get here in the case of a connection error and so will return just an empty
        // ArrayList
        return returnValue;
    }

    public static ArrayList<Offset> getOffsetOptions() {
        ArrayList<Offset> returnValue = new ArrayList<>();
        try {
            // request is of the form /offset/get
            URL url = new URL(serverURL + "/offset/get");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            String line;
            StringBuilder responseString = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((line = reader.readLine()) != null) {
                responseString.append(line);
            }
            reader.close();

            String response = responseString.toString();

            /* At this point response should have the form:

            [
                {
                    "vendor_id": *,
                    "vendor": *,
                    "description": *,
                    "price": *
                },
                ...
            ]

             */


            boolean continueLoop = true;


            int firstPointer = response.indexOf('[');
            int secondPointer = response.indexOf(',', firstPointer);
            // secondPointer == -1 iff the transaction list is empty as ',' will never appear
            // after the first appearance of '['

            // this will loop until secondPointer == -1 which only happens once all transactions
            // have been covered as there will be no more appearances of ',' in the JSON
            while (secondPointer != -1) {
                int vendorID;
                String vendor;
                String description;
                int price;

                firstPointer = response.indexOf(':', firstPointer + 1);
                secondPointer = response.indexOf(',', firstPointer);
                vendorID = Integer.parseInt(response.substring(firstPointer + 2, secondPointer));

                firstPointer = response.indexOf(':', secondPointer);
                secondPointer = response.indexOf(',', secondPointer + 1);
                vendor = response.substring(firstPointer + 2, secondPointer);

                firstPointer = response.indexOf(':', secondPointer);
                secondPointer = response.indexOf(',', secondPointer + 1);
                description = response.substring(firstPointer + 2, secondPointer);

                firstPointer = response.indexOf(':', secondPointer);
                secondPointer = response.indexOf('}', secondPointer + 1);
                price = Integer.parseInt(response.substring(firstPointer + 2, secondPointer));

                returnValue.add(new Offset(vendorID, vendor, description, price));

                secondPointer = response.indexOf(',', secondPointer);
            }

            return returnValue;

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Will only get here in the case of a connection error and so will return just an empty
        // ArrayList
        return returnValue;
    }

    public static void doOffset(Offset offsetChoice, int offsetAmount) throws OffsetFailedException {
        try {
            // request is of the form /offset/offset
            URL url = new URL(serverURL + "/offset/offset");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.setDoOutput(true);

            // Sends the request JSON expected by the server
            String request = "{\"user_id\": 1 ,\"vendor_id\": " + offsetChoice.getVendorID() + " ,\"offset_amount\": " + offsetAmount+"}";
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(request.getBytes());

            // Receives the response JSON from the server
            String line;
            StringBuilder responseString = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((line = reader.readLine()) != null) {
                responseString.append(line);
            }
            reader.close();

            String response = responseString.toString();

            /* At this point response should have the form:

            {
                "transaction_id":*,
	            "price":*,
	            "carbon_cost_offset":*,
	            "vendor":*,
	            "timestamp":*
            }

             */

            System.out.println(response);

            int firstPointer = response.indexOf(':');
            int secondPointer = response.indexOf(',');
            int transactionID = Integer.parseInt(response.substring(firstPointer + 2, secondPointer));
            if (transactionID == 0) {
                throw new OffsetFailedException();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void updateTransaction(int id, int newOffsetAmount) throws TransactionUpdateFailedException {
        try {
            // request is of the form /offset/offset
            URL url = new URL(serverURL + "/transaction/update");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.setDoOutput(true);

            // Sends the request JSON expected by the server
            String request = "{\"transaction_id\": " + id + ", \"carbon_cost_offset\": " + newOffsetAmount + "}";
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(request.getBytes());

            // Receives the response JSON from the server
            String line;
            StringBuilder responseString = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((line = reader.readLine()) != null) {
                responseString.append(line);
            }
            reader.close();

            String response = responseString.toString();

            /* At this point response should have the form:

            {
                "success":true/false
            }

             */
            System.out.println(response);
            int firstPointer = response.indexOf(':');
            int secondPointer = response.indexOf('}');
            boolean success = Boolean.parseBoolean(response.substring(firstPointer + 2, secondPointer));
            System.out.println(success);
            if (!success) {
                throw new TransactionUpdateFailedException();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
