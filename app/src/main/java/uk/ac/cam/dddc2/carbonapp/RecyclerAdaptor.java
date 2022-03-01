package uk.ac.cam.dddc2.carbonapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import uk.ac.cam.dddc2.carbonapp.datastores.Offset;

public class RecyclerAdaptor extends RecyclerView.Adapter<RecyclerAdaptor.MyViewHolder> {
    private ArrayList<Offset> offsetList;

    public RecyclerAdaptor(ArrayList<Offset> offsetList) {
        this.offsetList = offsetList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView nameText;

        public MyViewHolder(final View view) {
            super(view);
            nameText = view.findViewById(R.id.offsetProviderLabel);
        }
    }

    @NonNull
    @Override
    public RecyclerAdaptor.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdaptor.MyViewHolder holder, int position) {
        String name = offsetList.get(position).getVendor();
        holder.nameText.setText(name);
    }

    @Override
    public int getItemCount() {
        return offsetList.size();
    }
}
