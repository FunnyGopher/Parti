package com.github.funnygopher.parti.rsvp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.github.funnygopher.parti.R;
import com.github.funnygopher.parti.dao.LocalEventDao;
import com.github.funnygopher.parti.dao.RsvpDao;
import com.github.funnygopher.parti.dao.tasks.DeleteEventTask;
import com.github.funnygopher.parti.model.Event;
import com.github.funnygopher.parti.model.LocalEvent;
import com.github.funnygopher.parti.model.Rsvp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class RsvpRecyclerAdapter extends RecyclerView.Adapter<RsvpRecyclerAdapter.EventViewHolder> {

    private List<Event> mRsvpList;
    private Context mContext;

    public RsvpRecyclerAdapter(Context context, List<Event> list) {
        this.mRsvpList = list;
        this.mContext = context;
        update();
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.rsvp_cardview, parent, false);

        return new EventViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(EventViewHolder holder, int position) {
        final Event event = mRsvpList.get(position);

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
        SimpleDateFormat ft = new SimpleDateFormat("MMM d, yyyy", Locale.getDefault());
        Calendar startDate = event.getStartTime();
        Calendar endDate = event.getEndTime();
        StringBuilder dateString = new StringBuilder();
        dateString.append(ft.format(startDate.getTime()));
        if (endDate != null) {
            boolean sameDay = startDate.get(Calendar.YEAR) == endDate.get(Calendar.YEAR) &&
                    startDate.get(Calendar.DAY_OF_YEAR) == endDate.get(Calendar.DAY_OF_YEAR);

            if (!sameDay) {
                dateString.append(" - " + ft.format(endDate.getTime()));
            }
        }
        holder.date.setText(dateString.toString());

        final String address = event.getAddress();

        holder.directions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uriString = "geo:0,0?q=" + address;
                Intent searchAddress = new Intent(Intent.ACTION_VIEW, Uri.parse(uriString));
                mContext.startActivity(searchAddress);
            }
        });
        holder.additional_info.setText(event.getAdditionalInfo());

    }

    public void add(Event event) {
        mRsvpList.add(event);
        notifyItemInserted(mRsvpList.indexOf(event));
    }

    public void remove(Event event) {
        LocalEventDao localEventDao = new LocalEventDao(mContext);
        LocalEvent localEvent = localEventDao.find(event);
        localEventDao.delete(localEvent.getId());

        RsvpDao rsvpDao = new RsvpDao(mContext);
        rsvpDao.delete(localEvent.getId());

        update();
    }

    @Override
    public int getItemCount() {
        return mRsvpList.size();
    }

    public void update() {
        RsvpDao rsvpDao = new RsvpDao(mContext);
        List<Rsvp> rsvpList = rsvpDao.list();

        // Adds each rsvp to a new list
        LocalEventDao localEventDao = new LocalEventDao(mContext);
        List<LocalEvent> localEvents = localEventDao.list();

        List<Event> newEvents = new ArrayList<Event>();
        for (Rsvp rsvp: rsvpList){
            LocalEvent event = localEventDao.query("remoteId = ?", rsvp.getEventId().toString());
            if (event != null) {
                newEvents.add(event.toEvent());
            }
        }

        // Updates the rsvp list with the new list
        mRsvpList.clear();
        mRsvpList.addAll(newEvents);
        this.notifyDataSetChanged();
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder {

        private Toolbar toolbar;
        private TextView eventName, hostName, desc, date, additional_info;
        private Button directions;

        public EventViewHolder(View itemView) {
            super(itemView);

            toolbar = (Toolbar) itemView.findViewById(R.id.rsvp_card_toolbar);
            toolbar.inflateMenu(R.menu.menu_invitation_card);
            eventName = (TextView) itemView.findViewById(R.id.rsvp_detail_card_title);
            desc = (TextView) itemView.findViewById(R.id.rsvp_detail_card_desc);
            hostName = (TextView) itemView.findViewById(R.id.rsvp_detail_card_host);
            additional_info = (TextView) itemView.findViewById(R.id.rsvp_detail_card_requirements);
            date = (TextView) itemView.findViewById(R.id.rsvp_detail_card_date);

            directions = (Button) itemView.findViewById(R.id.rsvp_directions_action_button);
        }
    }
}
