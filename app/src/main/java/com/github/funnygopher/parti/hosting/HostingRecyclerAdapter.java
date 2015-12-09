package com.github.funnygopher.parti.hosting;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.funnygopher.parti.R;
import com.github.funnygopher.parti.dao.HostedEventDao;
import com.github.funnygopher.parti.dao.LocalEventDao;
import com.github.funnygopher.parti.model.Event;
import com.github.funnygopher.parti.model.HostedEvent;
import com.github.funnygopher.parti.model.LocalEvent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by Jackkell
 */
public class HostingRecyclerAdapter extends RecyclerView.Adapter<HostingRecyclerAdapter.HostingViewHolder> {

    private Context mContext;
    private List<Event> mHostedEventList;

    public HostingRecyclerAdapter(Context context, List<Event> hostedEvents) {
        this.mContext = context;
        this.mHostedEventList = hostedEvents;
        update();
    }

    @Override
    public HostingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.hosting_cardview, parent, false);
        mContext = parent.getContext();
        return new HostingViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final HostingViewHolder hostingViewHolder, final int position) {
        Event currentEvent = mHostedEventList.get(position);

        hostingViewHolder.title.setText(currentEvent.getName());
        hostingViewHolder.host.setText(currentEvent.getHost());
        hostingViewHolder.description.setText(currentEvent.getDescription());
        hostingViewHolder.requirements.setText(currentEvent.getAdditionalInfo());

        // Formats the date and time
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d, yyyy", Locale.getDefault());
        Calendar startDate = currentEvent.getStartTime();
        Calendar endDate = currentEvent.getEndTime();
        StringBuilder dateString = new StringBuilder();
        dateString.append(dateFormat.format(startDate.getTime()));
        if (endDate != null) {
            boolean sameDay = startDate.get(Calendar.YEAR) == endDate.get(Calendar.YEAR) &&
                    startDate.get(Calendar.DAY_OF_YEAR) == endDate.get(Calendar.DAY_OF_YEAR);

            if (!sameDay) {
                dateString.append(" - " + dateFormat.format(endDate.getTime()));
            }
        }
        hostingViewHolder.date.setText(dateString);
        hostingViewHolder.acceptedTotal.setText(currentEvent.getAttending() + "");
        hostingViewHolder.declinedTotal.setText(currentEvent.getDeclined() + "");

        hostingViewHolder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Event event = mHostedEventList.get(hostingViewHolder.getAdapterPosition());
                openForEdit(event);
            }
        });

        hostingViewHolder.inviteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Replace this with code that selects contacts to send SMS messages to
                Event event = mHostedEventList.get(hostingViewHolder.getAdapterPosition());
                Toast.makeText(mContext, "This event's ID is " + Long.toString(event.getId()),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mHostedEventList.size();
    }

    public void update() {
        HostedEventDao hostedDao = new HostedEventDao(mContext);
        List<HostedEvent> hostedEvents = hostedDao.list();

        // Adds each hosted event to a new list
        LocalEventDao localEventDao = new LocalEventDao(mContext);
        List<Event> newEvents = new ArrayList<Event>();
        for(HostedEvent hostedEvent : hostedEvents) {
            LocalEvent event = localEventDao.query("remoteId = ?", hostedEvent.getEventId().toString());
            if(event != null) {
                newEvents.add(event.toEvent());
            }
        }

        // Updates the hosted event list with the new list
        mHostedEventList.clear();
        mHostedEventList.addAll(newEvents);
        this.notifyDataSetChanged();
    }

    private void openForEdit(Event event) {
        Intent intent = new Intent(mContext, EventCreationActivity.class);
        Bundle b = new Bundle();
        b.putParcelable(Event.EVENT, event);
        b.putInt(EventCreationActivity.MODE, EventCreationActivity.MODE_EDIT);
        intent.putExtras(b);
        ((Activity) mContext).startActivityForResult(intent, HostingListFragment
                .REQUEST_CODE_EDIT_EVENT);
    }

    public static class HostingViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView host;
        private TextView date;
        private TextView description;
        private TextView requirements;
        private TextView acceptedTotal;
        private TextView declinedTotal;
        private Button inviteButton;
        private Button editButton;

        public HostingViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.host_detail_card_title);
            host = (TextView) itemView.findViewById(R.id.host_card_host);
            date = (TextView) itemView.findViewById(R.id.host_card_date);
            description = (TextView) itemView.findViewById(R.id.host_card_desc);
            requirements = (TextView) itemView.findViewById(R.id.host_card_requirements);
            acceptedTotal = (TextView) itemView.findViewById(R.id.host_accepted_total);
            declinedTotal = (TextView) itemView.findViewById(R.id.host_declined_total);
            inviteButton = (Button) itemView.findViewById(R.id.host_detail_card_invite_button);
            editButton = (Button) itemView.findViewById(R.id.host_detail_card_edit_button);
        }
    }
}
