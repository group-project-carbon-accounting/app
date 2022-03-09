package uk.ac.cam.dddc2.carbonapp.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import uk.ac.cam.dddc2.carbonapp.Connections;
import uk.ac.cam.dddc2.carbonapp.R;
import uk.ac.cam.dddc2.carbonapp.TransactionRecyclerAdaptor;
import uk.ac.cam.dddc2.carbonapp.datastores.Transaction;
import uk.ac.cam.dddc2.carbonapp.exceptions.TransactionUpdateFailedException;

public class TransactionInfoDialogFragment extends DialogFragment {
    private EditText transactionAmountEntry;
    private TextView responseMessageText;
    private Button confirmButtom;
    private Transaction transaction;
    private TransactionRecyclerAdaptor adaptor;
    private int position;
    private TransactionType transactiontype;
    public enum TransactionType {
        TRANSACTION,
        OFFSET
    }


    public TransactionInfoDialogFragment(Transaction transaction, TransactionRecyclerAdaptor adaptor, int position, TransactionType transactionType) {
        this.transaction = transaction;
        this.adaptor = adaptor;
        this.position = position;
        this.transactiontype = transactionType;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.change_transaction_amount, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        transactionAmountEntry = view.findViewById(R.id.editTransactionAmountEntry);
        transactionAmountEntry.requestFocus();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        responseMessageText = view.findViewById(R.id.responseMessageText);

        confirmButtom = view.findViewById(R.id.confirmTransactionUpdateButton);
        confirmButtom.setOnClickListener(view1 -> {
            try {
                int newCarbonCost = Integer.parseInt(transactionAmountEntry.getText().toString());
                Thread serverPost = new Thread() {
                    @Override
                    public void run() {
                        try {
                            switch (transactiontype) {
                                case TRANSACTION:
                                    Connections.updateTransaction(transaction.getTransactionID(), newCarbonCost);
                                    break;
                                case OFFSET:
                                    Connections.updateTransaction(transaction.getTransactionID(), -1 * newCarbonCost);
                                    break;
                            }
                            transaction.setNewCarbonCost(newCarbonCost);
                            Runnable updateView = () -> adaptor.notifyItemChanged(position);
                            getActivity().runOnUiThread(updateView);

                            dismiss();
                        } catch (TransactionUpdateFailedException e) {
                            Runnable updateView = () -> responseMessageText.setText(R.string.failed_update);
                            getActivity().runOnUiThread(updateView);
                        }
                    }
                };
                serverPost.start();

            } catch (NumberFormatException e) {
                Runnable updateView = () -> responseMessageText.setText(R.string.invalid_input);
                getActivity().runOnUiThread(updateView);

            }
        });

    }
}
