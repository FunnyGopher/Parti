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
import com.github.funnygopher.parti.dao.CreateEventTask;
import com.github.funnygopher.parti.dao.InvitationDAO;
import com.github.funnygopher.parti.model.Event;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class InvitationListFragment extends Fragment implements CreateEventTask.OnCreateEvent {

    private InvitationRecyclerAdapter mRecyclerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_invitation_list, container, false);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.invitation_list_create_invitation);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

                fabAction(event);
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

    private void fabAction(final Event event) {
        CreateEventTask createTask = new CreateEventTask(event);
        createTask.setOnResponseListener(this);
        createTask.execute();
    }

    @Override
    public void onCreateEvent(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            Long id = jsonObject.getLong("id");

            Invitation invitation = new Invitation(id);
            InvitationDAO invDAO = new InvitationDAO(getContext());
            invDAO.create(invitation);
        } catch(JSONException e){
            e.printStackTrace();
        }
    }
}
