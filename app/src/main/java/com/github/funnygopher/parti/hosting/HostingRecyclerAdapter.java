package com.github.funnygopher.parti.hosting;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.github.funnygopher.parti.event.Event;

import java.util.List;

/**
 * Created by Brandon Olson
 */
public class HostingRecyclerAdapter extends RecyclerView.Adapter<HostingRecyclerAdapter.HostingViewHolder> {

    public List<Event> hostedEvents;

    @Override
    public HostingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(HostingViewHolder hostingViewHolder, int position) {

    }

    @Override
    public int getItemCount() {
        return hostedEvents.size();
    }

    public static class HostingViewHolder extends RecyclerView.ViewHolder{

        public HostingViewHolder(View itemView) {
            super(itemView);
        }
    }
}
