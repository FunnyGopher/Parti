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

public class RsvpRecyclerAdapter extends RecyclerView.Adapter<RsvpRecyclerAdapter.EventViewHolder> {

    private List<Event> mRsvpList;
    private Context mContext;

    public RsvpRecyclerAdapter(Context context, List<Event> list) {
        this.mRsvpList = list;
        this.mContext = context;
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
        Event event = mRsvpList.get(position);

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

        final double longitude = event.getLongitude();
        final double latitude = event.getLatitude();

        holder.directions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uriString = "geo:" + longitude + "," + latitude;
                Intent searchAddress = new  Intent(Intent.ACTION_VIEW, Uri.parse(uriString));
                mContext.startActivity(searchAddress);
            }
        });
        holder.additional_info.setText(event.getAdditionalInfo());
        holder.date.setText(dateString.toString());
    }

    public void add(Event event) {
        mRsvpList.add(event);
        notifyItemInserted(mRsvpList.indexOf(event));
    }

    public void remove(Event event) {
        mRsvpList.remove(event);
        notifyItemRemoved(mRsvpList.indexOf(event));
    }

    @Override
    public int getItemCount() {
        return mRsvpList.size();
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder {

        private TextView eventName, hostName, desc, date, additional_info;
        private Button directions;

        public EventViewHolder(View itemView) {
            super(itemView);
            eventName = (TextView) itemView.findViewById(R.id.rsvp_detail_card_title);
            desc = (TextView) itemView.findViewById(R.id.rsvp_detail_card_desc);
            hostName = (TextView) itemView.findViewById(R.id.rsvp_detail_card_host);
            additional_info = (TextView) itemView.findViewById(R.id.rsvp_detail_card_requirements);
            date = (TextView) itemView.findViewById(R.id.rsvp_detail_card_date);

            directions = (Button) itemView.findViewById(R.id.rsvp_directions_action_button);
        }
    }
}
