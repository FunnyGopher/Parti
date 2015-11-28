package com.github.funnygopher.parti.hosting;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

/**
 * Created by Jackkell
 */
public class HostingRecyclerAdapter extends RecyclerView.Adapter<HostingRecyclerAdapter.HostingViewHolder> {

    public List<Event> hostedEvents;

    public HostingRecyclerAdapter(List<Event> hostedEvents) {
        this.hostedEvents = hostedEvents;
    }

    @Override
    public HostingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.hosting_cardview, parent, false);

        return new HostingViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(HostingViewHolder hostingViewHolder, int position) {
        Event currentEvent = hostedEvents.get(position);
        Log.d("HostingRecyclerAdapter", "CurrentEventName: " + currentEvent.getName());
        hostingViewHolder.title.setText(currentEvent.getName());

        SimpleDateFormat dateFormat = new SimpleDateFormat("E MMM d, yyyy '@' h:mm a", Locale.US);

        Calendar startDate = currentEvent.getStartTime();
        Calendar endDate = currentEvent.getEndTime();

        hostingViewHolder.startDate.setText(dateFormat.format(startDate.getTime()));

        if(endDate != null) {
            boolean sameDay = startDate.get(Calendar.YEAR) == endDate.get(Calendar.YEAR) &&
                    startDate.get(Calendar.DAY_OF_YEAR) == endDate.get(Calendar.DAY_OF_YEAR);

            if(sameDay) {
                SimpleDateFormat timeFormat = new SimpleDateFormat ("h:mm a", Locale.US);
                hostingViewHolder.endDate.setText(timeFormat.format(endDate.getTime()));
            } else {
                hostingViewHolder.endDate.setText(dateFormat.format(endDate.getTime()));
            }
        }
        hostingViewHolder.acceptedTotal.setText(currentEvent.getAttending());
        hostingViewHolder.declinedTotal.setText(currentEvent.getDeclined());
    }

    @Override
    public int getItemCount() {
        return hostedEvents.size();
    }

    public static class HostingViewHolder extends RecyclerView.ViewHolder{
        private CardView card;
        private TextView title;
        private TextView startDate;
        private TextView endDate;
        private TextView acceptedTotal;
        private TextView declinedTotal;
        private Button inviteButton;
        private Button editButton;

        public HostingViewHolder(View itemView) {
            super(itemView);
            card = (CardView) itemView.findViewById(R.id.host_card);
            title = (TextView) itemView.findViewById(R.id.host_detail_card_title);
            startDate = (TextView) itemView.findViewById(R.id.host_start_date);
            endDate = (TextView) itemView.findViewById(R.id.host_end_date);
            acceptedTotal = (TextView) itemView.findViewById(R.id.host_accepted_total);
            declinedTotal = (TextView) itemView.findViewById(R.id.host_declined_total);
            inviteButton = (Button) itemView.findViewById(R.id.host_detail_card_invite_button);
            editButton = (Button) itemView.findViewById(R.id.host_detail_card_edit_button);
        }
    }
}
