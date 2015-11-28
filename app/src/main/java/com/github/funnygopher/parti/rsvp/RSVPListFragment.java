package com.github.funnygopher.parti.rsvp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.funnygopher.parti.event.EventCreationActivity;
import com.github.funnygopher.parti.R;
import com.github.funnygopher.parti.event.Event;
import com.github.funnygopher.parti.event.EventRecyclerAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RSVPListFragment extends Fragment {
    private RSVPRecyclerAdapter adapter;

    private FloatingActionButton createEventButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hosting_list, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.eventlist_recyclerview);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getBaseContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        createEventButton = (FloatingActionButton) view.findViewById(R.id.create_event_button);
        createEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), EventCreationActivity.class));
            }
        });

        adapter = new RSVPRecyclerAdapter(getContext(), list);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
