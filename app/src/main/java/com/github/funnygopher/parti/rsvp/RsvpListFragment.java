package com.github.funnygopher.parti.rsvp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.funnygopher.parti.R;
import com.github.funnygopher.parti.model.Event;

import java.util.ArrayList;

public class RsvpListFragment extends Fragment {

    private RsvpRecyclerAdapter mRecyclerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rsvp_list, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rsvp_list_recyclerview);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getBaseContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        mRecyclerAdapter = new RsvpRecyclerAdapter(getContext(), new ArrayList<Event>());
        recyclerView.setAdapter(mRecyclerAdapter);

        return view;
    }
}
