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
import com.github.funnygopher.parti.model.Event;

import java.util.ArrayList;

/*
Created by Jackkell
 */
public class HostingListFragment extends Fragment {

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

        FloatingActionButton fab = (FloatingActionButton)
                view.findViewById(R.id.hosting_list_create_event);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_CODE_CREATE_EVENT:
                onCreateEventResult(resultCode, data);
                break;

            case REQUEST_CODE_EDIT_EVENT:
                onEditEventResult(resultCode, data);
                break;
        }
    }

    private void onCreateEventResult(int resultCode, Intent data) {
        switch (resultCode) {
            case Activity.RESULT_OK:
                mRecyclerAdapter.update();
                Toast.makeText(getActivity(), "Event creation was successful!", Toast.LENGTH_SHORT)
                        .show();
                break;

            case Activity.RESULT_CANCELED:
                Toast.makeText(getActivity(), "Event creation was cancelled", Toast.LENGTH_SHORT)
                        .show();
                break;
        }
    }

    private void onEditEventResult(int resultCode, Intent data) {
        switch (resultCode) {
            case Activity.RESULT_OK:
                mRecyclerAdapter.update();
                Toast.makeText(getActivity(), "Event update was successful!", Toast.LENGTH_SHORT)
                        .show();
                break;

            case Activity.RESULT_CANCELED:
                Toast.makeText(getActivity(), "Event update was cancelled", Toast.LENGTH_SHORT)
                        .show();
                break;
        }
    }
}
