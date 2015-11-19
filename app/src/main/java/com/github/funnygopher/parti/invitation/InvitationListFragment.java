package com.github.funnygopher.parti.invitation;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.funnygopher.parti.hosting.InsertEventTask;
import com.github.funnygopher.parti.R;
import com.github.funnygopher.parti.event.Event;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class InvitationListFragment extends Fragment {

    private FloatingActionButton mCreateInvitationFab;
    private InvitationRecyclerAdapter mRecyclerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_invitation_list, container, false);

        mCreateInvitationFab = (FloatingActionButton) view.findViewById(R.id.invitation_list_create_invitation);
        mCreateInvitationFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            // Creating a dummy event for the crazy rave
            Calendar raveStartDate = Calendar.getInstance();
            raveStartDate.set(2015, 9, 31, 22, 0, 0);
            Calendar raveEndDate = Calendar.getInstance();
            raveEndDate.set(2015, 10, 6, 22, 0, 0);
            Event event2 = new Event(
                    "The Crazy Rave", "The Rave Boys",
                    "The craziest rave you've ever been to. Strap on your leaderhosen. It's about to get bumpy.",
                    "$5.00 at the door. Glowsticks are a must.", raveStartDate, raveEndDate);
            mRecyclerAdapter.add(event2);

            createEventForDatabase(event2);
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

    private void createEventForDatabase(Event event) {
        InsertEventTask task = new InsertEventTask(event);
        task.setOnResponseListener(new InsertEventTask.OnResponseListener() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsoonObject = new JSONObject(response);
                    int id = jsoonObject.getInt("new_id");
                    acceptEvent(id);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("Invitation#createEvent", e.toString());
                }
            }
        });
        task.execute();
    }

    private void acceptEvent(int eventId) {
        AcceptEventTask acceptEventTask = new AcceptEventTask(eventId);
        acceptEventTask.execute();
    }

    private void declineEvent(int eventId) {
        DeclineEventTask declineEventTask = new DeclineEventTask(eventId);
        declineEventTask.execute();
    }
}
