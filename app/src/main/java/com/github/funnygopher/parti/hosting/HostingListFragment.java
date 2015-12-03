package com.github.funnygopher.parti.hosting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.funnygopher.parti.R;
import com.github.funnygopher.parti.dao.EventDAO;
import com.github.funnygopher.parti.dao.HostedEventDAO;
import com.github.funnygopher.parti.dao.LocalEventDAO;
import com.github.funnygopher.parti.dao.tasks.CreateEventTask;
import com.github.funnygopher.parti.dao.tasks.GetEventTask;
import com.github.funnygopher.parti.model.Event;
import com.github.funnygopher.parti.model.HostedEvent;
import com.github.funnygopher.parti.model.LocalEvent;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/*
Created by Jackkell
 */
public class HostingListFragment extends Fragment implements CreateEventTask.OnCreateEventListener, GetEventTask.OnGetEventListener {

    public static final int REQUEST_CODE_CREATE_EVENT = 0;
    public static final int REQUEST_CODE_EDIT_EVENT = 1;

    private HostingRecyclerAdapter mRecyclerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hosting_list, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.hosting_list_recyclerview);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getBaseContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.hosting_list_create_event);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openForCreate();
            }
        });

        mRecyclerAdapter = new HostingRecyclerAdapter(getActivity(), new ArrayList<Event>());
        recyclerView.setAdapter(mRecyclerAdapter);

        return view;
    }

    private void openForCreate() {
        Intent intent = new Intent(getContext(), EventCreationActivity.class);
        intent.putExtra(EventCreationActivity.MODE, EventCreationActivity.MODE_CREATE);
        startActivityForResult(intent, REQUEST_CODE_CREATE_EVENT);
    }

    // TODO: This method probably belongs in RecyclerAdapter
    private void openForEdit(Event event) {
        Intent intent = new Intent(getContext(), EventCreationActivity.class);
        Bundle b = new Bundle();
        b.putParcelable(Event.EVENT, event);
        b.putInt(EventCreationActivity.MODE, EventCreationActivity.MODE_CREATE);
        intent.putExtras(b);
        startActivityForResult(intent, REQUEST_CODE_EDIT_EVENT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case REQUEST_CODE_CREATE_EVENT:
                onCreateEventResult(resultCode, data);
                break;

            case REQUEST_CODE_EDIT_EVENT:
                onEditEventResult(resultCode, data);
                break;
        }
    }

    private void onCreateEventResult(int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_CANCELED) {
            Toast.makeText(getActivity(), "Event creation was cancelled", Toast.LENGTH_SHORT).show();
            return;
        }

        if(resultCode == Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            if(extras != null && !extras.isEmpty()) {
                Event event = extras.getParcelable(Event.EVENT);
                saveEventToRemoteDB(event);
                Toast.makeText(getActivity(), "Successfully created an event", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void saveEventToRemoteDB(Event event) {
        EventDAO dao = new EventDAO();
        dao.create(event, this);
    }

    private void onEditEventResult(int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_CANCELED) {
            Toast.makeText(getActivity(), "Event edit was cancelled", Toast.LENGTH_SHORT).show();
            return;
        }

        if(resultCode == Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            if(extras != null && !extras.isEmpty()) {
                Event event = extras.getParcelable(Event.EVENT);
                // TODO: Update the event
            }
        }
    }

    @Override
    public void onCreateEvent(String response) {
        try {
            JSONObject json = new JSONObject(response);
            Long id = json.getLong(Event.REMOTE_ID_KEY);

            HostedEvent hostedEvent = new HostedEvent(id);
            HostedEventDAO hostedDAO = new HostedEventDAO(getActivity());
            hostedDAO.create(hostedEvent);

            EventDAO eventDAO = new EventDAO();
            eventDAO.get(id, this);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onGetEvent(String response) {
        try {
            JSONObject json = new JSONObject(response);
            JSONObject result = json.getJSONArray("result").getJSONObject(0);
            Event event = new Event(result);

            // Save event to local database
            LocalEventDAO localEventDAO = new LocalEventDAO(getActivity());
            LocalEvent localEvent = new LocalEvent(event);
            localEventDAO.create(localEvent);
            List<LocalEvent> localEvents = localEventDAO.list();

            mRecyclerAdapter.update();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
