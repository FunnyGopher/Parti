package com.github.funnygopher.parti.invitation;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.github.funnygopher.parti.R;
import com.github.funnygopher.parti.dao.InvitationDao;
import com.github.funnygopher.parti.dao.LocalEventDao;
import com.github.funnygopher.parti.dao.tasks.DeleteEventTask;
import com.github.funnygopher.parti.model.Event;
import com.github.funnygopher.parti.model.Invitation;
import com.github.funnygopher.parti.model.LocalEvent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by FunnyGopher
 */
public class InvitationRecyclerAdapter extends RecyclerView.Adapter<InvitationRecyclerAdapter.InvitationViewHolder> {

    private Context mContext;
    private List<Event> mInvitationList;

    private OnAcceptListener mAcceptListener = new OnAcceptListener() {
        @Override
        public void onAccept(Event event) {
            // Do nothing...
        }
    };
    private OnDeclineListener mDeclineListener = new OnDeclineListener() {
        @Override
        public void onDecline(Event event) {
            // Do nothing...
        }
    };

    public InvitationRecyclerAdapter(Context context, List<Event> invitations) {
        this.mContext = context;
        this.mInvitationList = invitations;
        update();
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
        final Event event = mInvitationList.get(position);

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
        SimpleDateFormat ft = new SimpleDateFormat("E MMM d, yyyy '@' h:mm a", Locale.US);
        Calendar startDate = event.getStartTime();
        Calendar endDate = event.getEndTime();
        StringBuilder dateString = new StringBuilder();
        dateString.append(ft.format(startDate.getTime()));
        if (endDate != null) {
            boolean sameDay = startDate.get(Calendar.YEAR) == endDate.get(Calendar.YEAR) &&
                    startDate.get(Calendar.DAY_OF_YEAR) == endDate.get(Calendar.DAY_OF_YEAR);

            if (sameDay) {
                SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a", Locale.US);
                dateString.append(" - " + timeFormat.format(endDate.getTime()));
            } else {
                dateString.append(" - " + ft.format(endDate.getTime()));
            }
        }
        holder.date.setText(dateString.toString());
        holder.requirements.setText(event.getAdditionalInfo());

        // TODO: Move event to Rsvp when user presses accept or decline
        // When the user presses the attend button
        holder.actionAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAcceptListener.onAccept(event);
            }
        });

        // TODO: Move event to Rsvp when user presses accept or decline
        // When the user presses the decline button
        holder.actionDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDeclineListener.onDecline(event);
            }
        });
    }

    public void add(Event event) {
        LocalEventDao localEventDao = new LocalEventDao(mContext);
        LocalEvent localEvent = new LocalEvent(event);
        localEventDao.create(localEvent);

        InvitationDao invDao = new InvitationDao(mContext);
        Invitation invitation = new Invitation(event.getId());
        invDao.create(invitation);

        notifyItemInserted(mInvitationList.indexOf(event));
    }

    public void remove(Event event) {
        mInvitationList.remove(event);

        LocalEventDao localEventDao = new LocalEventDao(mContext);
        InvitationDao invDao = new InvitationDao(mContext);
        LocalEvent localEvent = localEventDao.find(event);

        invDao.delete(localEvent.getId());
        notifyItemRemoved(mInvitationList.indexOf(event));
    }

    @Override
    public int getItemCount() {
        return mInvitationList.size();
    }

    public void update() {
        InvitationDao invDao = new InvitationDao(mContext);
        List<Invitation> invitations = invDao.list();

        // Adds each invitation to a new list
        LocalEventDao localEventDao = new LocalEventDao(mContext);
        List<Event> newEvents = new ArrayList<Event>();
        for(Invitation invitation : invitations) {
            LocalEvent event = localEventDao.query("remoteId = ?", invitation.getEventId().toString());
            if(event != null) {
                newEvents.add(event.toEvent());
            }
        }

        // Updates the invitation list with the new list
        mInvitationList.clear();
        mInvitationList.addAll(newEvents);
        this.notifyDataSetChanged();
    }

    public void setOnAcceptListener(OnAcceptListener listener) {
        mAcceptListener = listener;
    }

    public void setOnDeclineListener(OnDeclineListener listener) {
        mDeclineListener = listener;
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

    public interface OnAcceptListener {
        void onAccept(Event event);
    }

    public interface OnDeclineListener {
        void onDecline(Event event);
    }
}
