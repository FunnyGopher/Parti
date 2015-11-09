package com.github.funnygopher.parti.invitation;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.github.funnygopher.parti.event.Event;
import com.github.funnygopher.parti.event.EventRecyclerAdapter;

import java.util.List;

/**
 * Created by Kyle on 11/9/2015.
 */
public class InvitationRecyclerAdapter extends RecyclerView.Adapter<InvitationRecyclerAdapter.InvitationViewHolder> {

    List<Event> invitations;

    @Override
    public InvitationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(InvitationViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return invitations.size();
    }

    public class InvitationViewHolder extends RecyclerView.ViewHolder {

        public InvitationViewHolder(View itemView) {
            super(itemView);
        }
    }
}
