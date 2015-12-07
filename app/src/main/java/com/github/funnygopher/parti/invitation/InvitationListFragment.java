package com.github.funnygopher.parti.invitation;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.github.funnygopher.parti.dao.tasks.GetEventTask;
import com.github.funnygopher.parti.model.Event;
import com.github.funnygopher.parti.model.Invitation;
import com.github.funnygopher.parti.model.LocalEvent;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class InvitationListFragment extends Fragment implements GetEventTask.OnGetEventListener {

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
        recyclerView.setAdapter(mRecyclerAdapter);

        mProgressDialog = new ProgressDialog(getActivity());

        return view;
    }

    private void fabAction() {
        final EditText idInput = new EditText(getActivity());

        // Set the default text to a link of the Queen
        idInput.setHint("Parti ID Number...");

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
                })
                .show();
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
            LocalEventDao localEventDao = new LocalEventDao(getActivity());
            LocalEvent localEvent = new LocalEvent(event);
            localEventDao.create(localEvent);

            InvitationDao invDao = new InvitationDao(getActivity());
            Invitation invitation = new Invitation(event.getId());
            invDao.create(invitation);

            mRecyclerAdapter.update();
            if(mProgressDialog.isShowing()) mProgressDialog.dismiss();
        } catch (JSONException e) {
            if(mProgressDialog.isShowing()) mProgressDialog.dismiss();
            Toast.makeText(getActivity(), "Something really bad just happened...", Toast.LENGTH_SHORT).show();
            Log.e("OnCreateEvent", e.toString());
        }
    }
}
