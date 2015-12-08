package com.github.funnygopher.parti.invitation;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.github.funnygopher.parti.R;
import com.github.funnygopher.parti.dao.EventDao;
import com.github.funnygopher.parti.dao.InvitationDao;
import com.github.funnygopher.parti.dao.LocalEventDao;
import com.github.funnygopher.parti.dao.RsvpDao;
import com.github.funnygopher.parti.dao.tasks.GetEventTask;
import com.github.funnygopher.parti.model.Event;
import com.github.funnygopher.parti.model.Invitation;
import com.github.funnygopher.parti.model.LocalEvent;
import com.github.funnygopher.parti.model.Rsvp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class InvitationListFragment extends Fragment implements GetEventTask.OnGetEventListener,
        InvitationRecyclerAdapter.OnAcceptListener, InvitationRecyclerAdapter.OnDeclineListener {

    private InvitationRecyclerAdapter mRecyclerAdapter;
    private ProgressDialog mProgressDialog;

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

        mRecyclerAdapter = new InvitationRecyclerAdapter(getActivity(), new ArrayList<Event>());
        mRecyclerAdapter.setOnAcceptListener(this);
        mRecyclerAdapter.setOnDeclineListener(this);
        recyclerView.setAdapter(mRecyclerAdapter);

        mProgressDialog = new ProgressDialog(getActivity());

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mRecyclerAdapter.update();
    }

    private void fabAction() {
        final EditText idInput = new EditText(getActivity());
        idInput.setHint("Parti ID Number...");
        idInput.setInputType(InputType.TYPE_CLASS_NUMBER);

        // Creates the dialog to import an invitation
        new AlertDialog.Builder(getActivity())
                .setTitle("Import Invite")
                .setMessage("Enter the ID of the party!")
                .setView(idInput)
                .setPositiveButton("Party Time", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String partiId = idInput.getText().toString();
                        Long id = Long.parseLong(partiId);

                        mProgressDialog.setMessage("Looking for party...");
                        mProgressDialog.show();

                        // Get event from remote DB
                        EventDao eventDao = new EventDao();
                        eventDao.get(id, InvitationListFragment.this);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                }).show();
    }

    @Override
    public void onGetEvent(String response) {
        try {
            JSONObject json = new JSONObject(response);

            // Check if something bad happened
            if (json.getInt("success") == 0) {
                if(mProgressDialog.isShowing()) mProgressDialog.dismiss();

                Toast.makeText(getActivity(), "Something really bad just happened...", Toast.LENGTH_SHORT).show();
                Log.e("OnCreateEvent", json.getString("error"));
                return;
            }

            JSONObject result = json.getJSONArray("result").getJSONObject(0);
            Event event = new Event(result);

            // Create the local representation of the event, and save it in the local DB
            LocalEventDao localEventDao = new LocalEventDao(getActivity());
            LocalEvent localEvent = new LocalEvent(event);
            localEventDao.create(localEvent);

            // Create the invitation in the local DB
            InvitationDao invDao = new InvitationDao(getActivity());
            Invitation invitation = new Invitation(event.getId());
            invDao.create(invitation);

            // Update the UI
            mRecyclerAdapter.update();

            if(mProgressDialog.isShowing()) mProgressDialog.dismiss();
        } catch (JSONException e) {
            if(mProgressDialog.isShowing()) mProgressDialog.dismiss();
            Toast.makeText(getActivity(), "Something really bad just happened...", Toast.LENGTH_SHORT).show();
            Log.e("OnCreateEvent", e.toString());
        }
    }

    @Override
    public void onAccept(Event event) {
        // Add 1 to attending in remote DB
        AttendEventTask task = new AttendEventTask(event.getId());
        task.execute();

        // Get the local representation of the event
        LocalEventDao localEventDao = new LocalEventDao(getActivity());
        LocalEvent localEvent = localEventDao.find(event);

        // Remove the invitation from the local DB
        InvitationDao invDao = new InvitationDao(getActivity());
        invDao.delete(localEvent.getId());

        // Create the rsvp in the local DB
        Rsvp rsvp = new Rsvp(event.getId(), true);
        RsvpDao rsvpDao = new RsvpDao(getActivity());
        rsvpDao.create(rsvp);

        // Update the UI
        mRecyclerAdapter.update();
    }

    @Override
    public void onDecline(Event event) {
        // Add 1 to declined in remote DB
        DeclineEventTask task = new DeclineEventTask(event.getId());
        task.execute();

        // Get the local representation of the event and delete it from the local DB
        LocalEventDao localEventDao = new LocalEventDao(getActivity());
        LocalEvent localEvent = localEventDao.find(event);
        localEventDao.delete(localEvent.getId());

        // Remove the invitation from the local DB
        InvitationDao invDao = new InvitationDao(getActivity());
        invDao.delete(localEvent.getId());

        // Update the UI
        mRecyclerAdapter.update();
    }
}