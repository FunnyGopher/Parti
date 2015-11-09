package com.github.funnygopher.parti.event;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.github.funnygopher.parti.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class EventRecyclerAdapter extends RecyclerView.Adapter<EventRecyclerAdapter.EventViewHolder> {

    private List<Event> eventList;

    public EventRecyclerAdapter(List<Event> list) {
        this.eventList = list;
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.event_cardview, parent, false);

        return new EventViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(EventViewHolder holder, int position) {
        Event event = eventList.get(position);

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

        holder.requirements.setText(event.getRequirements());
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder {

        private TextView eventName, hostName, desc, date, requirements;
        private Button action1, action2;

        public EventViewHolder(View itemView) {
            super(itemView);
            eventName = (TextView) itemView.findViewById(R.id.event_detail_card_title);
            desc = (TextView) itemView.findViewById(R.id.event_detail_card_desc);
            hostName = (TextView) itemView.findViewById(R.id.event_detail_card_host);
            date = (TextView) itemView.findViewById(R.id.event_detail_card_date);
            requirements = (TextView) itemView.findViewById(R.id.event_detail_card_requirements);

            action1 = (Button) itemView.findViewById(R.id.event_detail_card_action_button_1);
            action2 = (Button) itemView.findViewById(R.id.event_detail_card_action_button_2);
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

        public Button getActionButton1() {
            return action1;
        }

        public Button getActionButton2() {
            return action2;
        }
    }
}
