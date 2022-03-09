package uk.ac.cam.dddc2.carbonapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import uk.ac.cam.dddc2.carbonapp.datastores.Offset;
import uk.ac.cam.dddc2.carbonapp.fragments.OffsetChoiceDialogFragment;

public class OffsetRecyclerAdaptor extends RecyclerView.Adapter<OffsetRecyclerAdaptor.OffsetViewHolder> {

    private ArrayList<Offset> offsetList;
    private FragmentManager fragmentManager;

    public class OffsetViewHolder extends RecyclerView.ViewHolder {
        private TextView nameText;
        private TextView descriptionText;
        private TextView priceText;
        private Button offsetButton;

        public OffsetViewHolder(final View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.offsetProviderLabel);
            descriptionText = itemView.findViewById(R.id.offsetDescriptionTextView);
            priceText = itemView.findViewById(R.id.offsetPrice);
            offsetButton = itemView.findViewById(R.id.doOffsetButton);
        }
    }

    public OffsetRecyclerAdaptor(ArrayList<Offset> offsetList, FragmentManager fragmentManager) {
        this.offsetList = offsetList;
        this.fragmentManager = fragmentManager;
    }


    @NonNull
    @Override
    public OffsetRecyclerAdaptor.OffsetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.offset_list_item, parent, false);
        return new OffsetViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OffsetRecyclerAdaptor.OffsetViewHolder holder, int position) {
        String name = "Vendor: " + offsetList.get(position).getVendor();
        String description = offsetList.get(position).getDescription();
        String price = "Price: " + offsetList.get(position).getPrice() + "$/kg";
        holder.nameText.setText(name);
        holder.descriptionText.setText(description);
        holder.priceText.setText(price);


        holder.offsetButton.setOnClickListener(view -> {
            OffsetChoiceDialogFragment offsetChoiceDialogFragment = new OffsetChoiceDialogFragment(offsetList.get(position), this, position);
            offsetChoiceDialogFragment.show(fragmentManager, "fragment_offset_amount");
        });
    }

    @Override
    public int getItemCount() {
        return offsetList.size();
    }
}
