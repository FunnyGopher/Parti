package com.github.funnygopher.parti.hosting;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.github.funnygopher.parti.R;
import com.github.funnygopher.parti.model.Event;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EventCreationActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    // View Elements
    private EditText eventNameInput;
    private EditText hostNameInput;
    private EditText addressInput;

    private Calendar startDateTime;
    private Calendar endDateTime;

    private TextView startDateInput;
    private TextView endDateInput;

    private TextView startTimeInput;
    private TextView endTimeInput;

    private EditText eventDescriptionInput;
    private EditText additionalInfoInput;

    private Button mSaveButton;
    private boolean hasName = false;

    private SimpleDateFormat timeFormat, dateFormat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_creation);

        mToolbar = (Toolbar) findViewById(R.id.event_creation_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("New Event");

        eventNameInput = (EditText) findViewById(R.id.event_creation_name_input);
        hostNameInput = (EditText) findViewById(R.id.event_creation_host_input);
        addressInput = (EditText) findViewById(R.id.event_creation_address_input);
        startDateInput = (TextView) findViewById(R.id.event_creation_start_date_input);
        endDateInput = (TextView) findViewById(R.id.event_creation_end_date_input);
        startTimeInput = (TextView) findViewById(R.id.event_creation_start_time_input);
        endTimeInput = (TextView) findViewById(R.id.event_creation_end_time_input);
        eventDescriptionInput = (EditText) findViewById(R.id.event_creation_description_input);
        additionalInfoInput = (EditText) findViewById(R.id.event_creation_additional_info_input);
        mSaveButton = (Button) findViewById(R.id.event_creation_button_save);

        timeFormat = new SimpleDateFormat ("h:mm a", Locale.getDefault());
        dateFormat = new SimpleDateFormat("M/d/yyyy", Locale.getDefault());

        startDateTime = Calendar.getInstance();
        endDateTime = Calendar.getInstance();

        setOnClickForDate(startDateInput, startDateTime);
        setOnClickForDate(endDateInput, endDateTime);
        setOnClickForTime(startTimeInput, startDateTime);
        setOnClickForTime(endTimeInput, endDateTime);

        startTimeInput.setText(timeFormat.format(startDateTime.getTime()));
        endTimeInput.setText(timeFormat.format(endDateTime.getTime()));

        startDateInput.setText(dateFormat.format(startDateTime.getTime()));
        endDateInput.setText(dateFormat.format(endDateTime.getTime()));

        Button saveButton = (Button) findViewById(R.id.event_creation_button_save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();

                if (hasName) {
                    Event event = createEvent();

                    //TODO: Need to make Event class implement parcalable
                    /*
                    Bundle b = new Bundle();
                    b.putParcelable("event", event);
                    intent.putExtras(b);
                    */
                    setResult(Activity.RESULT_OK, intent);
                } else {
                    setResult(Activity.RESULT_CANCELED, intent);
                }

                finish();
            }
        });

        updateSaveButton("");
    }

    private void setOnClickForTime(final TextView textView, final Calendar calendar) {
        final TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                textView.setText(timeFormat.format(calendar.getTime()));
            }
        };

        View.OnClickListener onTimeClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(EventCreationActivity.this,
                        timeSetListener,
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE), false)
                    .show();
            }
        };
        textView.setOnClickListener(onTimeClick);
    }

    private void setOnClickForDate(final TextView textView, final Calendar calendar) {
        final DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(year, monthOfYear, dayOfMonth);
                textView.setText(dateFormat.format(calendar.getTime()));
            }
        };

        View.OnClickListener onDateClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(EventCreationActivity.this,
                        dateSetListener,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH))
                    .show();
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

    private void updateSaveButton(String s) {
        hasName = s.length() > 0;
        if(hasName) {
            mSaveButton.setText("Save");
        } else {
            mSaveButton.setText("Cancel");
        }
    }

    private Event createEvent() {
        // Get all the information into variables
        // Store object in database
        String name = eventNameInput.getText().toString();
        String host = hostNameInput.getText().toString();
        String description = eventDescriptionInput.getText().toString();
        String additionalInfo = additionalInfoInput.getText().toString();
        
        Event event = new Event(name, host, description, startDateTime, endDateTime, additionalInfo, 33.3774338, -111.9759768, 0, 0);
        return event;
    }
}
