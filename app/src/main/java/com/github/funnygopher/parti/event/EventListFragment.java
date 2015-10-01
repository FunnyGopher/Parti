package com.github.funnygopher.parti.event;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.funnygopher.parti.R;

import java.util.ArrayList;
import java.util.List;

public class EventListFragment extends Fragment {

    private EventRecyclerAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_eventlist, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.eventlist_recyclerview);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getBaseContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        List<Event> list = new ArrayList<>();
        Event event1 = new Event("Tim's Birthday", "Tim");
        list.add(event1);
        Event event2 = new Event("ASU Football Game", "Arizona State University");
        list.add(event2);
        for(int i = 3; i < 20; i++) {
            Event dummy = new Event(Integer.toString(i), Integer.toString(i + 100));
            list.add(dummy);
        }

        adapter = new EventRecyclerAdapter(list);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
