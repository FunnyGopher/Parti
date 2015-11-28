package com.github.funnygopher.parti.invitation;

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

public class InvitationListFragment extends Fragment {

    private InvitationRecyclerAdapter mRecyclerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_invitation_list, container, false);

        final FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.invitation_list_test_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabAction();
            }
        });

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.invitation_list_recyclerview);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getBaseContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        mRecyclerAdapter = new InvitationRecyclerAdapter(new ArrayList<Event>());
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
                raveStartDate, raveEndDate,
                "$5.00 at the door. Glowsticks are a must.", 0, 0, 0, 0);
        mRecyclerAdapter.add(event);
    }
}
