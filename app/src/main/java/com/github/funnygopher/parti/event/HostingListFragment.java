package com.github.funnygopher.parti.event;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.funnygopher.parti.EventCreationActivity;
import com.github.funnygopher.parti.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HostingListFragment extends Fragment {
    private EventRecyclerAdapter adapter;

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

        List<Event> list = new ArrayList<>();

        adapter = new EventRecyclerAdapter(list);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
