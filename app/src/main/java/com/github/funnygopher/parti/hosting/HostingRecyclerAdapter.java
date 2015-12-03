package com.github.funnygopher.parti.hosting;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.github.funnygopher.parti.R;
import com.github.funnygopher.parti.dao.CupboardSQLiteHelper;
import com.github.funnygopher.parti.dao.HostedEventDAO;
import com.github.funnygopher.parti.dao.LocalEventDAO;
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

    private Context context;
    private List<Event> mHostedEventList;

    public HostingRecyclerAdapter(Context context, List<Event> hostedEvents) {
        this.context = context;
        this.mHostedEventList = hostedEvents;

        update();
    }

    @Override
    public HostingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.hosting_cardview, parent, false);

        return new HostingViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final HostingViewHolder hostingViewHolder, final int position) {
        Event currentEvent = mHostedEventList.get(position);
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
        hostingViewHolder.acceptedTotal.setText(Integer.toString(currentEvent.getAttending()));
        hostingViewHolder.declinedTotal.setText(Integer.toString(currentEvent.getDeclined()));

        hostingViewHolder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Event event = mHostedEventList.get(hostingViewHolder.getAdapterPosition());
                openForEdit(event);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mHostedEventList.size();
    }

    public void update() {
        HostedEventDAO hostedDAO = new HostedEventDAO(context);
        List<HostedEvent> hostedEvents = hostedDAO.list();

        // Adds each hosted event to a new list
        LocalEventDAO localEventDAO = new LocalEventDAO(context);
        List<Event> newEvents = new ArrayList<Event>();
        for(HostedEvent hostedEvent : hostedEvents) {
            LocalEvent event = localEventDAO.query("remoteId = ?", hostedEvent.getEventId().toString());
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
        Intent intent = new Intent(context, EventCreationActivity.class);
        Bundle b = new Bundle();
        b.putParcelable(Event.EVENT, event);
        b.putInt(EventCreationActivity.MODE, EventCreationActivity.MODE_EDIT);
        intent.putExtras(b);
        ((Activity) context).startActivityForResult(intent, HostingListFragment.REQUEST_CODE_EDIT_EVENT);
    }

    public static class HostingViewHolder extends RecyclerView.ViewHolder {
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
