package com.example.nhahangamthuc.mon_an;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nhahangamthuc.R;
import com.example.nhahangamthuc.dinh_danh.QuanLyActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MonAnFragment extends Fragment {

    FloatingActionButton fab;

    EditText inputSearch;
    RecyclerView recyclerView_menu;

    ArrayList<MonAn> mListMonAn;
    MonAnAdapter monanAdapter;

    public MonAnFragment() {
        // Required empty public constructor
    }

    public static MonAnFragment newInstance() {
        MonAnFragment fragment = new MonAnFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mon_an, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fab = view.findViewById(R.id.fab);
        recyclerView_menu = view.findViewById(R.id.all_menu_recycler);
        inputSearch = view.findViewById(R.id.editText);

        mListMonAn = new ArrayList<>();
        monanAdapter = new MonAnAdapter(getActivity(), mListMonAn);
        recyclerView_menu.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView_menu.setAdapter(monanAdapter);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Danh_sach_mon_an");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mListMonAn.clear();
                for(DataSnapshot ds: snapshot.getChildren()){
                    MonAn monAn = ds.getValue(MonAn.class);
                    mListMonAn.add(monAn);
                    monanAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Get list mon an faild", Toast.LENGTH_SHORT).show();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddMonAn.class);
                startActivity(intent);
            }
        });
    }
}