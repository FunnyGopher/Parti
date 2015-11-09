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
import java.util.Calendar;
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

        // Creating a dummy event for Jim
        Calendar jimStartDate = Calendar.getInstance();
        jimStartDate.set(2015, 9, 24, 18, 0, 0);
        Calendar jimEndDate = Calendar.getInstance();
        jimEndDate.set(2015, 9, 24, 22, 0, 0);
        Event event1 = new Event(
                "Jim's Birthday", "Jim",
                "It's Jim's birthday! Everybody come out for some fun and good food!",
                "Bring a bathing suit and towel!", jimStartDate, jimEndDate);
        list.add(event1);

        // Creating a dummy event for the crazy rave
        Calendar raveStartDate = Calendar.getInstance();
        raveStartDate.set(2015, 9, 31, 22, 0, 0);
        Calendar raveEndDate = Calendar.getInstance();
        raveEndDate.set(2015, 10, 6, 22, 0, 0);
        Event event2 = new Event(
                "The Crazy Rave", "The Rave Boys",
                "The craziest rave you've ever been to. Strap on your leaderhosen. It's about to get bumpy.",
                "$5.00 at the door. Glowsticks are a must.", raveStartDate, raveEndDate);
        event2.rsvp();
        list.add(event2);

        for(int i = 3; i < 20; i++) {
            Calendar now = Calendar.getInstance();
            Event dummy = new Event(
                    "Lorem Ipsum", "dolor sit amet",
                    "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                    "Ut enim ad minim veniam", now, null);
            list.add(dummy);
        }

        adapter = new EventRecyclerAdapter(list);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
