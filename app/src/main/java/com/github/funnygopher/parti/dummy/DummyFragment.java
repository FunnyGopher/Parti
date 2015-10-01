package com.github.funnygopher.parti.dummy;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.github.funnygopher.parti.R;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("ValidFragment")
public class DummyFragment extends Fragment {

    private int color;
    private DummyInfoRecyclerAdapter adapter;

    @SuppressLint("ValidFragment")
    public DummyFragment(int color) {
        this.color = color;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dummy, container, false);

        FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.dummy_framelayout);
        frameLayout.setBackgroundColor(color);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.dummy_recyclerview);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getBaseContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        List<DummyInfo> list = new ArrayList<>();
        for(int i = 0; i < 20; i++) {
            DummyInfo dummy = new DummyInfo("Item " + i);
            list.add(dummy);
        }

        adapter = new DummyInfoRecyclerAdapter(list);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
