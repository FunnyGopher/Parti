package com.github.funnygopher.parti;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.github.funnygopher.parti.event.Event;

import java.util.Calendar;


public class EventCreationFragment extends Fragment {
    // View Elements
    private EditText eventNameInput;
    private EditText hostNameInput;
    private EditText addressInput;
    private TextView startDateInput;
    private TextView endDateInput;
    private TextView startTimeInput;
    private TextView endTimeInput;
    private EditText eventDescriptionInput;
    private EditText maxInvitesInput;
    private EditText additionalInfo;
    private Button createEventButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event_creation, container, false);
        eventNameInput = (EditText) view.findViewById(R.id.event_name_input);
        hostNameInput = (EditText) view.findViewById(R.id.host_name_input);
        addressInput = (EditText) view.findViewById(R.id.address_input);
        startDateInput = (TextView) view.findViewById(R.id.start_date_input);
        endDateInput = (TextView) view.findViewById(R.id.end_date_input);
        startTimeInput = (TextView) view.findViewById(R.id.start_time_input);
        endTimeInput = (TextView) view.findViewById(R.id.end_time_input);
        eventDescriptionInput = (EditText) view.findViewById(R.id.event_description_input);
        maxInvitesInput = (EditText) view.findViewById(R.id.max_invites_input);
        additionalInfo = (EditText) view.findViewById(R.id.additional_info_input);
        createEventButton = (Button) view.findViewById(R.id.create_event_button);

        setOnClickForDate(startDateInput);
        setOnClickForDate(endDateInput);
        setOnClickForTime(startTimeInput);
        setOnClickForTime(endTimeInput);

        createEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCreateEventButtonClick();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private void setOnClickForTime(final TextView textView) {
        final TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                textView.setText(String.valueOf(hourOfDay + ":" + String.valueOf(minute)));
            }
        };

        final Calendar now = Calendar.getInstance();
        View.OnClickListener onTimeClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(getActivity().getApplicationContext(),
                        timeSetListener, now.get(Calendar.HOUR_OF_DAY),
                        now.get(Calendar.MINUTE), false);
            }
        };
        textView.setOnClickListener(onTimeClick);
    }

    private void setOnClickForDate(final TextView textView) {
        final DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                textView.setText(String.valueOf(monthOfYear + "/" + dayOfMonth + "/" + year));
            }
        };

        final Calendar now = Calendar.getInstance();
        View.OnClickListener onDateClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity().getApplicationContext(),
                        dateSetListener, now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
            }
        };
        textView.setOnClickListener(onDateClick);
    }

    private boolean validateEventInformation() {
        boolean validate = true;

        if (eventNameInput.getText().toString().isEmpty()){
            // TODO: tell user of issue
            validate = false;
        }
        if (hostNameInput.getText().toString().isEmpty()){
            // TODO: tell user of issue
            validate = false;
        }
        if (addressInput.getText().toString().isEmpty()){
            // TODO: tell user of issue
            validate = false;
        }
        validate = true;
        return validate;
    }

    private void onCreateEventButtonClick() {
        if (!validateEventInformation()){
            return;
        }
    }
}
