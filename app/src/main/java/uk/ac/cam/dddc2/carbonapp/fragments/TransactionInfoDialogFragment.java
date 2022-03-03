package uk.ac.cam.dddc2.carbonapp.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

import uk.ac.cam.dddc2.carbonapp.R;

public class TransactionInfoDialogFragment extends DialogFragment {
    private EditText transactionAmountEntry;

    public TransactionInfoDialogFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.change_transaction_amount, container);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        transactionAmountEntry = view.findViewById(R.id.editTransactionAmountEntry);
        transactionAmountEntry.requestFocus();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }
}
