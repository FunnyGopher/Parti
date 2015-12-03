package com.github.funnygopher.parti.rsvp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.funnygopher.parti.R;
import com.github.funnygopher.parti.model.Event;

import java.util.ArrayList;
import java.util.Calendar;

public class RSVPListFragment extends Fragment {

    private RSVPRecyclerAdapter mRecyclerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rsvp_list, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rsvp_list_recyclerview);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getBaseContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.rsvp_list_test_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabAction();
            }
        });

        mRecyclerAdapter = new RSVPRecyclerAdapter(getContext(), new ArrayList<Event>());
        recyclerView.setAdapter(mRecyclerAdapter);

        return view;
    }

    private void fabAction() {
        // Creating a dummy event for the crazy rave
        Calendar raveStartDate = Calendar.getInstance();
        raveStartDate.set(2015, 9, 31, 22, 0, 0);
        Calendar raveEndDate = Calendar.getInstance();
        raveEndDate.set(2015, 10, 6, 22, 0, 0);
        Event event = new Event(
                "The Crazy Rave", "The Rave Boys",
                "The craziest rave you've ever been to. Strap on your leaderhosen. It's about to get bumpy.",
                "$5.00 at the door. Glowsticks are a must.",
                raveStartDate, raveEndDate,
                33.3775468, -111.9811847, 0, 0);
        mRecyclerAdapter.add(event);
    }
}
