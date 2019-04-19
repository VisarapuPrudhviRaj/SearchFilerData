package com.lokeshlabs.searchfilerdata;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rajkumar on 19/7/18.
 */

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.MyViewHolder> implements Filterable {


    List<ContactModel> contactModelList;
    List<ContactModel> dataFilteredList;
    Context context;

    public FilterAdapter(List<ContactModel> contactModelList, Context context, List<ContactModel> dataFilteredList) {
        this.contactModelList = contactModelList;
        this.context = context;
        this.dataFilteredList = contactModelList;
    }


    @Override
    public FilterAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.filter_row_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FilterAdapter.MyViewHolder holder, int position) {
        ContactModel pojo = dataFilteredList.get(position);
        holder.filterName.setText(pojo.getName());
        holder.filterPhone.setText(pojo.getPhone());
        Glide.with(context)
                .load(pojo.getImage())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.img_photo);



    }

    @Override
    public int getItemCount() {
        return dataFilteredList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<ContactModel> filterList = new ArrayList<>();
                filterList.clear();
                String strChar = constraint.toString();
                if (strChar.isEmpty()) {
                    dataFilteredList = contactModelList;
                } else {
                    for (ContactModel data : contactModelList) {
                        if (data.getName().toLowerCase().contains(strChar.toLowerCase())||data.getPhone().contains(constraint)) {
                            filterList.add(data);
                        }

                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filterList;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (constraint.toString().equals("")) {

                } else {

                    dataFilteredList = (List<ContactModel>) results.values;
                }
                notifyDataSetChanged();
            }
        };
    }

    public interface ContactListener {
        void onSelectedListener(ContactModel contactModel);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView filterName, filterPhone;
        ImageView img_photo;

        public MyViewHolder(View itemView) {
            super(itemView);

            filterName = itemView.findViewById(R.id.filterName);
            filterPhone = itemView.findViewById(R.id.filterPhone);
            img_photo = itemView.findViewById(R.id.img_photo);
/*
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onSelectedListener(dataFilteredList.get(getAdapterPosition()));
                }
            });
*/
        }
    }
}
