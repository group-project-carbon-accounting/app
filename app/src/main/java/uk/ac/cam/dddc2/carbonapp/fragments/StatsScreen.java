package uk.ac.cam.dddc2.carbonapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import uk.ac.cam.dddc2.carbonapp.Connections;
import uk.ac.cam.dddc2.carbonapp.R;
import uk.ac.cam.dddc2.carbonapp.TransactionRecyclerAdaptor;
import uk.ac.cam.dddc2.carbonapp.datastores.Transaction;

public class StatsScreen extends Fragment {
    private final ArrayList<Transaction> transactionList = new ArrayList<>();
    private RecyclerView recyclerView;

    public StatsScreen() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stats_screen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.transactionListRecyclerView);
        TransactionRecyclerAdaptor adaptor = new TransactionRecyclerAdaptor(transactionList, getActivity().getSupportFragmentManager());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adaptor);
        setTransactionInfo(adaptor);
    }

    private void setTransactionInfo(TransactionRecyclerAdaptor adaptor) {
        Thread serverRequest = new Thread() {
            @Override
            public void run() {
                // This assumes that transactions can't be removed from the database
                int oldSize = transactionList.size();
                ArrayList<Transaction> newTransactions = Connections.getTransactionsForPeriod(7);
                Collections.sort(newTransactions, (t1, t2) -> Integer.compare(t1.getTransactionID(), t2.getTransactionID()));
                int newSize = newTransactions.size();
                for (int i = oldSize; i < newSize; i++) {
                    transactionList.add(newTransactions.get(i));
                }
                Runnable updateView = () -> adaptor.notifyItemRangeInserted(oldSize, newSize - oldSize);
                getActivity().runOnUiThread(updateView);
            }
        };
        serverRequest.start();
    }

}