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

    // Activity modes
    public static final String MODE = "mode";
    public static final int MODE_CREATE = 0;
    public static final int MODE_EDIT = 1;

    private int mode = MODE_CREATE;
    private Event eventToEdit;
    private SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a", Locale.getDefault());
    private SimpleDateFormat dateFormat = new SimpleDateFormat("M/d/yyyy", Locale.getDefault());

    // Views
    private Toolbar mToolbar;

    private EditText eventNameInput;
    private EditText hostNameInput;
    private EditText addressInput;

    private Calendar startDateTime;
    private Calendar endDateTime;

    private TextView startDateInput;
    private TextView endDateInput;

    private TextView startTimeInput;
    private TextView endTimeInput;

    private EditText descriptionInput;
    private EditText additionalInfoInput;

    private Button mSaveButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_creation);

        mToolbar = (Toolbar) findViewById(R.id.event_creation_toolbar);
        eventNameInput = (EditText) findViewById(R.id.event_creation_name_input);
        hostNameInput = (EditText) findViewById(R.id.event_creation_host_input);
        descriptionInput = (EditText) findViewById(R.id.event_creation_description_input);
        additionalInfoInput = (EditText) findViewById(R.id.event_creation_additional_info_input);
        addressInput = (EditText) findViewById(R.id.event_creation_address_input);
        startDateInput = (TextView) findViewById(R.id.event_creation_start_date_input);
        endDateInput = (TextView) findViewById(R.id.event_creation_end_date_input);
        startTimeInput = (TextView) findViewById(R.id.event_creation_start_time_input);
        endTimeInput = (TextView) findViewById(R.id.event_creation_end_time_input);
        mSaveButton = (Button) findViewById(R.id.event_creation_button_save);

        // Sets up the action bar
        setSupportActionBar(mToolbar);

        // Check the mode
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null && !extras.isEmpty()) {
            mode = extras.getInt(MODE, MODE_CREATE);
            if (mode == MODE_EDIT) {
                eventToEdit = extras.getParcelable(Event.EVENT);
                fillFormWithEvent(eventToEdit);
                mToolbar.setTitle("Edit Event");
            }
        }

        if (mode == MODE_CREATE) {
            // Initializes the start and end date and time with the current date and time
            startDateTime = Calendar.getInstance();
            endDateTime = Calendar.getInstance();
            updateDateTimeText();
            mToolbar.setTitle("Create Event");
        }

        // Allows the ImeOptions to still be actionNext while allowing more than one line of text
        descriptionInput.setHorizontallyScrolling(false);
        descriptionInput.setMaxLines(Integer.MAX_VALUE);

        additionalInfoInput.setHorizontallyScrolling(false);
        additionalInfoInput.setMaxLines(Integer.MAX_VALUE);

        // Sets click listeners for date and time
        setOnClickForDate(startDateInput, startDateTime);
        setOnClickForDate(endDateInput, endDateTime);
        setOnClickForTime(startTimeInput, startDateTime);
        setOnClickForTime(endTimeInput, endDateTime);

        // Click listener for the save button
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mode == MODE_CREATE) {
                    saveEvent(createEvent());
                } else if (mode == MODE_EDIT) {
                    saveEvent(eventToEdit);
                }
            }
        });
    }

    private void fillFormWithEvent(Event event) {
        eventNameInput.setText(event.getName());
        hostNameInput.setText(event.getHost());
        descriptionInput.setText(event.getDescription());
        additionalInfoInput.setText(event.getAdditionalInfo());

        startDateTime = event.getStartTime();
        endDateTime = event.getEndTime();
        updateDateTimeText();

        // TODO: Get address from longitude and latitude
    }

    private void updateDateTimeText() {
        startDateInput.setText(dateFormat.format(startDateTime.getTime()));
        startTimeInput.setText(timeFormat.format(startDateTime.getTime()));
        endDateInput.setText(dateFormat.format(endDateTime.getTime()));
        endTimeInput.setText(timeFormat.format(endDateTime.getTime()));
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

    private void saveEvent(Event event) {
        if (!validate()) {
            return;
        }

        Intent intent = new Intent();
        Bundle extras = new Bundle();
        extras.putParcelable(Event.EVENT, event);
        intent.putExtras(extras);

        setResult(Activity.RESULT_OK, intent);

        finish();
    }

    private boolean validate() {
        boolean valid = true;

        String name = eventNameInput.getText().toString();
        if (name.isEmpty()) {
            eventNameInput.setError("The event needs a name!");
            valid = false;
        }

        // TODO: Validate other things!

        return valid;
    }

    private Event createEvent() {
        String name = eventNameInput.getText().toString();
        String host = hostNameInput.getText().toString();
        String description = descriptionInput.getText().toString();
        String additionalInfo = additionalInfoInput.getText().toString();
        // TODO: Get longitude and latitude from address

        Event event = new Event(
                name, host, description, additionalInfo, startDateTime, endDateTime,
                33.3774338, -111.9759768, 0, 0);
        return event;
    }
}
