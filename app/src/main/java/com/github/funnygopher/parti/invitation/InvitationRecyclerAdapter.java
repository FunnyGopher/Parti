package com.github.funnygopher.parti.invitation;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.github.funnygopher.parti.R;
import com.github.funnygopher.parti.dao.DeleteEventTask;
import com.github.funnygopher.parti.model.Event;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by Kyle on 11/9/2015.
 */
public class InvitationRecyclerAdapter extends RecyclerView.Adapter<InvitationRecyclerAdapter.InvitationViewHolder> {

    List<Event> invitations;

    public InvitationRecyclerAdapter(List<Event> invitations) {
        this.invitations = invitations;
    }

    @Override
    public InvitationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View invitationView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.invitation_cardview, parent, false);

        return new InvitationViewHolder(invitationView);
    }

    @Override
    public void onBindViewHolder(InvitationViewHolder holder, int position) {
        final Event event = invitations.get(position);

        holder.toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_invitation_card_delete:
                        remove(event);
                        DeleteEventTask deleteTask = new DeleteEventTask(event.getId());
                        deleteTask.execute();
                        break;
                }
                return false;
            }
        });

        holder.eventName.setText(event.getName());
        holder.desc.setText(event.getDescription());
        holder.hostName.setText("Hosted by: " + event.getHost());

        // Formats the date and time
        SimpleDateFormat ft = new SimpleDateFormat ("E MMM d, yyyy '@' h:mm a", Locale.US);
        Calendar startDate = event.getStartTime();
        Calendar endDate = event.getEndTime();
        StringBuilder dateString = new StringBuilder();
        dateString.append(ft.format(startDate.getTime()));
        if(endDate != null) {
            boolean sameDay = startDate.get(Calendar.YEAR) == endDate.get(Calendar.YEAR) &&
                    startDate.get(Calendar.DAY_OF_YEAR) == endDate.get(Calendar.DAY_OF_YEAR);

            if(sameDay) {
                SimpleDateFormat timeFormat = new SimpleDateFormat ("h:mm a", Locale.US);
                dateString.append(" - " + timeFormat.format(endDate.getTime()));
            } else {
                dateString.append(" - " + ft.format(endDate.getTime()));
            }
        }
        holder.date.setText(dateString.toString());
        holder.requirements.setText(event.getAdditionalInfo());

        // When the user presses the attend button
        holder.actionAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AttendEventTask task = new AttendEventTask(event.getId());
                task.execute();
            }
        });

        // When the user presses the decline button
        holder.actionDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeclineEventTask task = new DeclineEventTask(event.getId());
                task.execute();
            }
        });
    }

    public void add(Event event) {
        invitations.add(event);
        notifyItemInserted(invitations.indexOf(event));
    }

    public void remove(Event event) {
        invitations.remove(event);
        notifyItemRemoved(invitations.indexOf(event));
    }

    @Override
    public int getItemCount() {
        return invitations.size();
    }

    public class InvitationViewHolder extends RecyclerView.ViewHolder {

        private Toolbar toolbar;
        private TextView eventName, hostName, desc, date, requirements;
        private Button actionAccept, actionDecline;

        public InvitationViewHolder(View itemView) {
            super(itemView);

            toolbar = (Toolbar) itemView.findViewById(R.id.invitation_card_toolbar);
            toolbar.inflateMenu(R.menu.menu_invitation_card);

            eventName = (TextView) itemView.findViewById(R.id.invitation_card_title);
            desc = (TextView) itemView.findViewById(R.id.invitation_card_desc);
            hostName = (TextView) itemView.findViewById(R.id.invitation_card_host);
            date = (TextView) itemView.findViewById(R.id.invitation_card_date);
            requirements = (TextView) itemView.findViewById(R.id.invitation_card_requirements);

            actionAccept = (Button) itemView.findViewById(R.id.invitation_card_action_accept);
            actionAccept.setText(R.string.invitation_card_action_accept);
            actionDecline = (Button) itemView.findViewById(R.id.invitation_card_action_decline);
            actionDecline.setText(R.string.invitation_card_action_decline);
        }
    }
}
