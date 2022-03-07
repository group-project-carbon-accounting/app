package uk.ac.cam.dddc2.carbonapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import uk.ac.cam.dddc2.carbonapp.Connections;
import uk.ac.cam.dddc2.carbonapp.OffsetRecyclerAdaptor;
import uk.ac.cam.dddc2.carbonapp.R;
import uk.ac.cam.dddc2.carbonapp.datastores.Offset;
import uk.ac.cam.dddc2.carbonapp.exceptions.OffsetFailedException;


public class OffsetChoiceDialogFragment extends DialogFragment {
    private EditText offsetAmountEntry;
    private TextView responseMessageText;
    private Button confirmButton;
    private Offset offset;
    private OffsetRecyclerAdaptor adaptor;
    private int position;

    public OffsetChoiceDialogFragment(Offset offset, OffsetRecyclerAdaptor adaptor, int position) {
        this.offset = offset;
        this.adaptor = adaptor;
        this.position = position;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_offset_amount, container);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        offsetAmountEntry = view.findViewById(R.id.offsetAmountEntry);
        offsetAmountEntry.requestFocus();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        responseMessageText = view.findViewById(R.id.responseMessageText);

        confirmButton = view.findViewById(R.id.confirmOffsetButton);
        confirmButton.setOnClickListener(view1 -> {
            try {
                int offsetAmount = Integer.parseInt(offsetAmountEntry.getText().toString());
                Thread serverPost = new Thread() {
                    @Override
                    public void run() {
                        try {
                            Connections.doOffset(offset, offsetAmount);
                            dismiss();
                        } catch (OffsetFailedException e) {
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
