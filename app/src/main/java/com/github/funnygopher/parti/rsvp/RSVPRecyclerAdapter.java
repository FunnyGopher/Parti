package com.github.funnygopher.parti.rsvp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.github.funnygopher.parti.R;
import com.github.funnygopher.parti.model.Event;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class RSVPRecyclerAdapter extends RecyclerView.Adapter<RSVPRecyclerAdapter.EventViewHolder> {

    private List<Event> eventList;
    private Context context;

    public RSVPRecyclerAdapter(Context context, List<Event> list) {
        this.eventList = list;
        this.context = context;
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
        Event event = eventList.get(position);

        holder.eventName.setText(event.getName());
        holder.desc.setText(event.getDescription());
        holder.hostName.setText("Hosted by: " + event.getHost());

        // Formats the date and time
        SimpleDateFormat ft = new SimpleDateFormat ("MMM d, yyyy", Locale.US);
        Calendar startDate = event.getStartTime();
        Calendar endDate = event.getEndTime();
        StringBuilder dateString = new StringBuilder();
        dateString.append(ft.format(startDate.getTime()));
        if(endDate != null) {
            boolean sameDay = startDate.get(Calendar.YEAR) == endDate.get(Calendar.YEAR) &&
                    startDate.get(Calendar.DAY_OF_YEAR) == endDate.get(Calendar.DAY_OF_YEAR);

            if(!sameDay) {
                dateString.append(" - " + ft.format(endDate.getTime()));
            }
        }

        final String address = event.getAddress();

        holder.directions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchAddress = new  Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=" + address));
                context.startActivity(searchAddress);
            }
        });
        holder.date.setText(dateString.toString());

        holder.requirements.setText(event.getRequirements());
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder {

        private TextView eventName, hostName, desc, date, requirements;
        private Button directions;

        public EventViewHolder(View itemView) {
            super(itemView);
            eventName = (TextView) itemView.findViewById(R.id.event_detail_card_title);
            desc = (TextView) itemView.findViewById(R.id.event_detail_card_desc);
            hostName = (TextView) itemView.findViewById(R.id.event_detail_card_host);
            date = (TextView) itemView.findViewById(R.id.event_detail_card_date);
            requirements = (TextView) itemView.findViewById(R.id.event_detail_card_requirements);

            directions = (Button) itemView.findViewById(R.id.rsvp_directions_action_button);
        }

        public TextView getEventName() {
            return eventName;
        }

        public TextView getDescription() {
            return desc;
        }

        public TextView getHostName() {
            return hostName;
        }

        public TextView getDate() {
            return date;
        }

        public TextView getRequirements() {
            return requirements;
        }

        public Button getDirectionsButton() {
            return directions;
        }
    }
}
