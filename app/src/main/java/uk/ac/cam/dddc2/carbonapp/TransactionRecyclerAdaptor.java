package uk.ac.cam.dddc2.carbonapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import uk.ac.cam.dddc2.carbonapp.datastores.Transaction;

public class TransactionRecyclerAdaptor extends RecyclerView.Adapter<TransactionRecyclerAdaptor.TransactionViewHolder> {

    private ArrayList<Transaction> transactionList;
    private FragmentManager fragmentManager;

    public class TransactionViewHolder extends RecyclerView.ViewHolder {
        private TextView timestampText;
        private TextView vendorText;
        private TextView priceText;
        private ImageButton editButton;

        public TransactionViewHolder(final View itemView) {
            super(itemView);
            timestampText = itemView.findViewById(R.id.transactionTimestampLabel);
            vendorText = itemView.findViewById(R.id.transactionVendorLabel);
            priceText = itemView.findViewById(R.id.transactionPrice);
            editButton = itemView.findViewById(R.id.editTransactionButton);
        }
    }

    public TransactionRecyclerAdaptor(ArrayList<Transaction> transactionList, FragmentManager fragmentManager) {
        this.transactionList = transactionList;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public TransactionRecyclerAdaptor.TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_list_item, parent, false);
        return new TransactionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionRecyclerAdaptor.TransactionViewHolder holder, int position) {
        String timestamp = "Transaction time: " + transactionList.get(position).getTimestamp();
        String vendor = "Vender: " + transactionList.get(position).getVendor();
        String price = "Price: " + Float.toString(transactionList.get(position).getPrice());
        String carbonCost = "Carbon Cost: " + Float.toString(transactionList.get(position).getCarbonCostOffset());
        holder.timestampText.setText(timestamp);
        holder.vendorText.setText(vendor);
        holder.priceText.setText(price);

        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }
}
