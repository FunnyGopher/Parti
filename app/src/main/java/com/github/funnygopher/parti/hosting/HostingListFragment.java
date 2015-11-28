package com.github.funnygopher.parti.hosting;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.funnygopher.parti.R;
import com.github.funnygopher.parti.event.EventCreationActivity;
import com.github.funnygopher.parti.model.Event;

import java.util.ArrayList;

/*
Created by Jackkell
 */
public class HostingListFragment extends Fragment {

    private HostingRecyclerAdapter mAdapter;
    private FloatingActionButton mCreateEventButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hosting_list, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.eventlist_recyclerview);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getBaseContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        mCreateEventButton = (FloatingActionButton) view.findViewById(R.id.create_event_button);
        mCreateEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), EventCreationActivity.class));
            }
        });

        mAdapter = new HostingRecyclerAdapter(new ArrayList<Event>());
        recyclerView.setAdapter(mAdapter);

        return view;
    }
}
