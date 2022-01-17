package com.example.nhahangamthuc.ban_an;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
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

public class DsBanFragment extends Fragment {

    private EditText edtSearch;
    private ImageButton imgbSearch;
    private RecyclerView rcvBan;
    private BanAnAdapter banAnAdapter;
    private DatabaseReference listBanRef = FirebaseDatabase.getInstance().
            getReference("list_ban_an");
    private DsBanService dsBanService;
    private ProgressDialog progressDialog;
    public DsBanFragment() {
        // Required empty public constructor
    }

    public static DsBanFragment newInstance() {

        return new DsBanFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ds_ban, container, false);
        mapping(view);
        edtSearch.setText("");
        progressDialog = new ProgressDialog(getContext());
        banAnAdapter = new BanAnAdapter(getActivity());
        dsBanService = new DsBanService();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        rcvBan.setLayoutManager(gridLayoutManager);

        List<BanAn> listBan = new ArrayList<>();
        banAnAdapter.setData(listBan);
        rcvBan.setAdapter(banAnAdapter);

        listBanRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listBan.clear();
                progressDialog.show();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    BanAn banAn = dataSnapshot.getValue(BanAn.class);
                    listBan.add(banAn);
                    //banAnAdapter.notifyDataSetChanged();
                }
                dsBanService.setTrangThaiListBan(listBan,
                        new Timestamp(System.currentTimeMillis()));
                banAnAdapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("e", "Loi get ds ban ");
            }
        });

        imgbSearch.setOnClickListener(v -> {
            String keyword = edtSearch.getText().toString().trim();
            banAnAdapter.getFilter().filter(keyword);
            edtSearch.clearFocus();
            InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });
        return  view;
    }
    private void mapping(View view){
        edtSearch = (EditText) view.findViewById(R.id.edt_search1);
        imgbSearch = (ImageButton) view.findViewById(R.id.imgb_search1);
        rcvBan = (RecyclerView) view.findViewById(R.id.rcv_dsban);
    }
}