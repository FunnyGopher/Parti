package com.github.funnygopher.parti.invitation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.funnygopher.parti.R;

/**
 * Created by Kyle on 11/9/2015.
 */
public class InvitationListFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_invitation_list, container, false);



        return view;
    }
}
