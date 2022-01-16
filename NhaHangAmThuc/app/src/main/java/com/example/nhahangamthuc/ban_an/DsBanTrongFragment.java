package com.example.nhahangamthuc.ban_an;

import static androidx.core.content.ContextCompat.getSystemService;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhahangamthuc.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DsBanTrongFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DsBanTrongFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private EditText edtSearch;
    private List<BanAn> listBan, listBanOld;
    private ImageButton imgbSearch;
    private Spinner spinnerNgay;
    private Spinner spinnerGio;
    private RecyclerView rcvBan;
    private BanAnAdapter banAnAdapter;
    private DsBanService dsBanService;
    private String ngay;
    private String gio;
    private Timestamp searchTime;

    private DatabaseReference listBanRef = FirebaseDatabase.getInstance().
            getReference("list_ban_an");

    public DsBanTrongFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DsBanTrongFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DsBanTrongFragment newInstance(String param1, String param2) {
        DsBanTrongFragment fragment = new DsBanTrongFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ds_ban_trong, container, false);
        mapping(view);
        edtSearch.setText("");
        banAnAdapter = new BanAnAdapter(getActivity());
        dsBanService = new DsBanService();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        rcvBan.setLayoutManager(gridLayoutManager);

        listBan = new ArrayList<>();
        listBanOld = new ArrayList<>();
        banAnAdapter.setData(listBan);
        rcvBan.setAdapter(banAnAdapter);

//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference listBanRef = database.getReference("list_ban_an");

        listBanRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listBan.clear();
                listBanOld.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    BanAn banAn = dataSnapshot.getValue(BanAn.class);
                    listBan.add(banAn);
                    listBanOld.add(banAn);
                    //banAnAdapter.notifyDataSetChanged();
                }
//                dsBanService.setTrangThaiListBan(listBan,
//                        new Timestamp(System.currentTimeMillis()));
//                banAnAdapter.notifyDataSetChanged();
                setListBanTrong(new Timestamp(System.currentTimeMillis()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("e", "Loi get ds ban ");
            }
        });
        searchHandle();

        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void searchHandle() {
        List<String> listNgay;
        //spinner ngay
        listNgay = dsBanService.getListNgay();
        SpinnerAdapter spnNgayAdapter = new SpinnerAdapter(getContext(),
                R.layout.spinner_item_selected, listNgay);

        spinnerNgay.setAdapter(spnNgayAdapter);
        //Bắt sự kiện cho Spinner, khi chọn phần tử nào thì hiển thị lên Toast
        spinnerNgay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ngay = listNgay.get(position);
                //Toast.makeText(getContext(), "aa", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getContext(), "onNothingSelected", Toast.LENGTH_SHORT).show();
            }
        });

        //spinner gio
        List<String> listGio = dsBanService.getListGio();
        SpinnerAdapter spnGioAdaper = new SpinnerAdapter(getContext(),
                R.layout.spinner_item_selected, listGio);
        spinnerGio.setAdapter(spnGioAdaper);
        //Bắt sự kiện cho Spinner, khi chọn phần tử nào thì hiển thị lên Toast
        spinnerGio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //String msg = "position :" + position + " value :" + listGio.get(position);
                //Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                gio = listGio.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getContext(), "onNothingSelected", Toast.LENGTH_SHORT).show();
            }
        });
        imgbSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(), ngay + gio +"x", Toast.LENGTH_SHORT).show();
                Timestamp searchTime = Timestamp.valueOf(ngay + " " + gio + ":10");
                if (searchTime != null) {
//                    dsBanService.setTrangThaiListBan(listBan, searchTime);
//                    banAnAdapter.notifyDataSetChanged();
                    setListBanTrong(searchTime);
                } else {
                    Toast.makeText(getContext(), "Error: Convert Date Time",
                            Toast.LENGTH_LONG).show();
                }

                String keyword = edtSearch.getText().toString().trim();
                banAnAdapter.getFilter().filter(keyword);
                edtSearch.clearFocus();
            }
        });
    }

    private void setListBanTrong(Timestamp time) {
        dsBanService.setTrangThaiListBan(listBanOld, time);
        listBan.clear();
        for (BanAn banAn : listBanOld)
            if (banAn.getTrangThai() == 0)
                listBan.add(banAn);
        banAnAdapter.notifyDataSetChanged();
    }

    private void mapping(View view) {
        rcvBan = (RecyclerView) view.findViewById(R.id.rcv_dsbantrong);
        spinnerNgay = (Spinner) view.findViewById(R.id.spns_ngay);
        spinnerGio = (Spinner) view.findViewById(R.id.spns_gio);
        edtSearch = (EditText) view.findViewById(R.id.edt_search2);
        imgbSearch = (ImageButton) view.findViewById(R.id.imgb_search2);
    }
}