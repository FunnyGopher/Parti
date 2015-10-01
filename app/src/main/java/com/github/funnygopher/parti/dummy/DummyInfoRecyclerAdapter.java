package com.github.funnygopher.parti.dummy;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.funnygopher.parti.R;

import java.util.List;

public class DummyInfoRecyclerAdapter extends RecyclerView.Adapter<DummyInfoRecyclerAdapter.DummyInfoViewHolder> {

    private List<DummyInfo> dummyList;

    public DummyInfoRecyclerAdapter(List<DummyInfo> list) {
        this.dummyList = list;
    }

    @Override
    public DummyInfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.cardview_dummy, parent, false);
        return new DummyInfoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DummyInfoViewHolder holder, int position) {
        DummyInfo info = dummyList.get(position);
        holder.textView.setText(info.text);
    }

    @Override
    public int getItemCount() {
        return dummyList.size();
    }

    public static class DummyInfoViewHolder extends RecyclerView.ViewHolder {

        protected TextView textView;

        public DummyInfoViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.dummy_textview);
        }
    }
}
