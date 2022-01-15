package com.example.nhahangamthuc.mon_an;

import android.widget.Filter;

import java.util.ArrayList;
import java.util.Locale;

public class FilterMonAn extends Filter {

    ArrayList<MonAn> filterList;

    MonAnAdapter monAnAdapter;

    public FilterMonAn(ArrayList<MonAn> filterList, MonAnAdapter monAnAdapter) {
        this.filterList = filterList;
        this.monAnAdapter = monAnAdapter;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();

        if(constraint != null && constraint.length() > 0){
            constraint = constraint.toString().toUpperCase();
            ArrayList<MonAn> filterMonAn = new ArrayList<>();
            for (int i = 0; i < filterList.size(); i++){
                //validate
                if(filterList.get(i).getTenmonan().toUpperCase().contains(constraint)){
                    filterMonAn.add(filterList.get(i));
                }
            }

            results.count = filterMonAn.size();
            results.values = filterMonAn;
        }
        else {
            results.count = filterList.size();
            results.values = filterList;
        }

        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        //apply filter change
        monAnAdapter.mListMonAn = (ArrayList<MonAn>) results.values;

        //notify changes
        monAnAdapter.notifyDataSetChanged();
    }
}
