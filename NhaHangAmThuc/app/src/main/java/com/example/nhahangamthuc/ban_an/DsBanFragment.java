package com.example.nhahangamthuc.ban_an;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;

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
 * Use the {@link DsBanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DsBanFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private EditText edtSearch;
    private ImageButton imgbSearch;
    private RecyclerView rcvBan;
    private BanAnAdapter banAnAdapter;
    private DatabaseReference listBanRef = FirebaseDatabase.getInstance().
            getReference("list_ban_an");
    private DsBanService dsBanService;
    public DsBanFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DsBanFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DsBanFragment newInstance(String param1, String param2) {
        DsBanFragment fragment = new DsBanFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ds_ban, container, false);
        mapping(view);
        edtSearch.setText("");
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
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    BanAn banAn = dataSnapshot.getValue(BanAn.class);
                    listBan.add(banAn);
                    //banAnAdapter.notifyDataSetChanged();
                }
                dsBanService.setTrangThaiListBan(listBan,
                        new Timestamp(System.currentTimeMillis()));
                banAnAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("e", "Loi get ds ban ");
            }
        });

        imgbSearch.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                String keyword = edtSearch.getText().toString().trim();
                banAnAdapter.getFilter().filter(keyword);
                edtSearch.clearFocus();
                InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
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