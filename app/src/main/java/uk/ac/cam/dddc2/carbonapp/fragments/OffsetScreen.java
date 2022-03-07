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
import uk.ac.cam.dddc2.carbonapp.OffsetRecyclerAdaptor;
import uk.ac.cam.dddc2.carbonapp.R;
import uk.ac.cam.dddc2.carbonapp.datastores.Offset;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OffsetScreen} factory method to
 * create an instance of this fragment.
 */
public class OffsetScreen extends Fragment {

    private final ArrayList<Offset> offsetList = new ArrayList<>();
    private RecyclerView recyclerView;

    public OffsetScreen() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_offset_screen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.offsetListRecyclerView);
        OffsetRecyclerAdaptor adaptor = new OffsetRecyclerAdaptor(offsetList, getActivity().getSupportFragmentManager());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adaptor);
        setOffsetInfo(adaptor);
    }

    private void setOffsetInfo(OffsetRecyclerAdaptor adaptor) {
        Thread serverRequest = new Thread() {
            @Override
            public void run() {
                int oldSize = offsetList.size();
                ArrayList<Offset> newOffsets= Connections.getOffsetOptions();
                Collections.sort(newOffsets, (o1, o2) -> Integer.compare(o1.getVendorID(), o2.getVendorID()));
                int newSize = newOffsets.size();
                for (int i = oldSize; i < newSize; i++) {
                    offsetList.add(newOffsets.get(i));
                }

                Runnable updateView = () -> adaptor.notifyItemRangeInserted(oldSize, newSize - oldSize);
                getActivity().runOnUiThread(updateView);

                // TEMPORARY OFFSET OPTIONS FOR TESTING PURPOSES
                /*
                offsetList.add(new Offset(1,
                        "Trees4Life",
                        "The Amazon Rainforest has suffered from intense deforestation in recent years. We aim to try and reverse this with your help!",
                        10));

                 */
            }
        };
        serverRequest.start();
    }
}