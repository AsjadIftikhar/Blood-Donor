package com.example.blooddonors;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.view.ContentInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Hashtable;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> implements Filterable{

    Hashtable<String, Integer> mapping;
    private static ArrayList<Donor> localDataSet;
    private static ArrayList<Donor> filteredDataSet;
    private Filter filter;
    private Context context;

    // Constructor
    public CustomAdapter(ArrayList<Donor> dataSet, Context context) {
        localDataSet = dataSet;
        filteredDataSet = new ArrayList<>();
        filteredDataSet = dataSet;

        mapping = new Hashtable<>();
        mapping.put("A+", R.drawable.apos);
        mapping.put("A-", R.drawable.aneg);
        mapping.put("B+", R.drawable.bpos);
        mapping.put("B-", R.drawable.bneg);
        mapping.put("O+", R.drawable.opos);
        mapping.put("O-", R.drawable.oneg);
        mapping.put("AB+", R.drawable.abpos);
        mapping.put("AB-", R.drawable.abneg);

        this.context = context;
    }

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new donorFilter();
        }
        return filter;
    }

    @Override
    public int getItemCount() {
        if (filteredDataSet != null) {
            return filteredDataSet.size();
        }
        return 0;
    }

    // View Holder Class
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final TextView address;
        private final TextView availability;
        private final TextView phone;
        private final ImageView image;
        private final ImageView call;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            name = view.findViewById(R.id.name);
            address = view.findViewById(R.id.address);
            availability = view.findViewById(R.id.availability);
            phone = view.findViewById(R.id.phone);
            image = view.findViewById(R.id.image);
            call = view.findViewById(R.id.Call);
        }

        public TextView getName() {
            return name;
        }

        public TextView getAddress() {
            return address;
        }

        public TextView getAvailability() {
            return availability;
        }

        public TextView getPhone() {
            return phone;
        }

        public ImageView getImage() {
            return image;
        }

        public ImageView getCall() {
            return call;
        }

    }


    // Create new views (invoked by the layout manager)
//    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.text_row_item, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getName().setText(filteredDataSet.get(position).getName());
        viewHolder.getAddress().setText(filteredDataSet.get(position).getAddress());
        viewHolder.getAvailability().setText(filteredDataSet.get(position).getAvailability());

        if (filteredDataSet.get(position).getStatus()) {
            viewHolder.getPhone().setTextColor(Color.rgb(143, 188, 187));
            viewHolder.getPhone().setTypeface(viewHolder.getPhone().getTypeface(), Typeface.BOLD);
            viewHolder.getPhone().setText(R.string.avail);
            viewHolder.getCall().setVisibility(View.VISIBLE);

        } else {
            viewHolder.getPhone().setTextColor(Color.rgb(76, 86, 106));
            viewHolder.getPhone().setText(R.string.hidden);
            viewHolder.getCall().setVisibility(View.INVISIBLE);
        }

        int image_id = mapping.get(filteredDataSet.get(position).getBloodGroup());
        viewHolder.getImage().setImageResource(image_id);

        viewHolder.getCall().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contactDialogue contactDialogue = new contactDialogue();
                contactDialogue.setPhone(filteredDataSet.get(position).getNumber());
                contactDialogue.show(((AppCompatActivity) context).getSupportFragmentManager(), "Whatever");
            }
        });
    }


    //    Inner Class
    private class donorFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            ArrayList<Donor> filtered = new ArrayList<>();

            if (constraint != null && constraint.length() > 0) {
                for (Donor d : localDataSet) {
                    if (d.getName().contains(constraint) || d.getBloodGroup().contains(constraint) || d.getAddress().contains(constraint)) {
                        filtered.add(d);
                    }
                }
                results.count = filtered.size();
                results.values = filtered;
            } else {
                results.count = localDataSet.size();
                results.values = localDataSet;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {
            filteredDataSet = (ArrayList<Donor>) results.values;
            notifyDataSetChanged();
        }
    }


}


