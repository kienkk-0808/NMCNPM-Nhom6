package com.example.nhahangamthuc.don_hang;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhahangamthuc.R;
import com.example.nhahangamthuc.do_choi.DoChoi;

import java.util.ArrayList;
import java.util.List;

public class ChonDoChoiAdapter extends RecyclerView.Adapter<ChonDoChoiAdapter.ChonDoChoiViewHolder>{

    List<DoChoi> doChoiList;

    public ChonDoChoiAdapter(List<DoChoi> doChoiList) {
        this.doChoiList = doChoiList;
    }

    @NonNull
    @Override
    public ChonDoChoiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chon_do_choi, parent, false);
        return new ChonDoChoiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChonDoChoiViewHolder holder, int position) {
        DoChoi doChoi = doChoiList.get(position);
        holder.tenDoChoi.setText(doChoi.getTen());
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            doChoi.setChecked(isChecked);
        });
    }

    @Override
    public int getItemCount() {
        if (doChoiList != null) return doChoiList.size();
        return 0;
    }

    class ChonDoChoiViewHolder extends RecyclerView.ViewHolder {

        TextView tenDoChoi;
        CheckBox checkBox;

        public ChonDoChoiViewHolder(@NonNull View itemView) {
            super(itemView);
            tenDoChoi = itemView.findViewById(R.id.txv_chon_do_choi);
            checkBox = itemView.findViewById(R.id.checkbox_chon_do_choi);
        }
    }

}
