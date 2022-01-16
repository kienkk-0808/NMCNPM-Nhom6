package com.example.nhahangamthuc.ban_an;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.nhahangamthuc.R;

import java.util.List;

public class SpinnerAdapter extends ArrayAdapter<String> {

    public SpinnerAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner_item_selected, parent, false);
        TextView tvSelected = convertView.findViewById(R.id.tv_spinner_item_selected);

        String data = this.getItem(position);
        if (data != null)
            tvSelected.setText(data);
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner_item_category, parent, false);
        TextView tvItem = convertView.findViewById(R.id.tv_spinner_item);

        String data = this.getItem(position);
        if (data != null)
            tvItem.setText(data);
        return convertView;
    }
}
