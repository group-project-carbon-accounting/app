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
import uk.ac.cam.dddc2.carbonapp.fragments.TransactionInfoDialogFragment;

public class TransactionRecyclerAdaptor extends RecyclerView.Adapter<TransactionRecyclerAdaptor.TransactionViewHolder> {

    private ArrayList<Transaction> transactionList;
    private FragmentManager fragmentManager;

    public class TransactionViewHolder extends RecyclerView.ViewHolder {
        private TextView timestampText;
        private TextView vendorText;
        private TextView priceText;
        private TextView carbonCostText;
        private ImageButton editButton;

        public TransactionViewHolder(final View itemView) {
            super(itemView);
            timestampText = itemView.findViewById(R.id.transactionTimestampLabel);
            vendorText = itemView.findViewById(R.id.transactionVendorLabel);
            priceText = itemView.findViewById(R.id.transactionPriceLabel);
            editButton = itemView.findViewById(R.id.editTransactionButton);
            carbonCostText = itemView.findViewById(R.id.carbonCostLabel);
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
        String vendor = "Vendor: " + transactionList.get(position).getVendor();
        String price = String.valueOf(transactionList.get(position).getPrice());
        int digitCount = price.length();
        if (digitCount > 2) {
            price = '$' + price.substring(0, digitCount - 2) + '.' + price.substring(digitCount - 2);
        } else if (digitCount == 2) {
            price = "$0." + price;
        } else {
            price = "$0.0" + price;
        }
        String carbonCost = "Carbon Cost: " + transactionList.get(position).getCarbonCostOffset() + 'g';
        holder.timestampText.setText(timestamp);
        holder.vendorText.setText(vendor);
        holder.priceText.setText(price);
        holder.carbonCostText.setText(carbonCost);

        holder.editButton.setOnClickListener(view -> {
            TransactionInfoDialogFragment transactionInfoDialogFragment;
            if (transactionList.get(position).getPrice() >= 0) {
                transactionInfoDialogFragment =
                        new TransactionInfoDialogFragment(transactionList.get(position),
                                this, position,
                                TransactionInfoDialogFragment.TransactionType.TRANSACTION);
            } else {
                transactionInfoDialogFragment =
                        new TransactionInfoDialogFragment(transactionList.get(position),
                                this, position,
                                TransactionInfoDialogFragment.TransactionType.OFFSET);
            }
            transactionInfoDialogFragment.show(fragmentManager, "change_transaction_amount");
        });

    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }
}
