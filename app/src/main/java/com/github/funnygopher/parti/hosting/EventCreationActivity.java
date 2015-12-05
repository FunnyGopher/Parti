package com.github.funnygopher.parti.hosting;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.github.funnygopher.parti.R;
import com.github.funnygopher.parti.dao.EventDao;
import com.github.funnygopher.parti.dao.HostedEventDao;
import com.github.funnygopher.parti.dao.LocalEventDao;
import com.github.funnygopher.parti.dao.tasks.CreateEventTask;
import com.github.funnygopher.parti.dao.tasks.GetEventTask;
import com.github.funnygopher.parti.dao.tasks.UpdateEventTask;
import com.github.funnygopher.parti.model.Event;
import com.github.funnygopher.parti.model.HostedEvent;
import com.github.funnygopher.parti.model.LocalEvent;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EventCreationActivity extends AppCompatActivity implements
        CreateEventTask.OnCreateEventListener, GetEventTask.OnGetEventListener,
        UpdateEventTask.OnUpdateEventListener {

    // Activity modes
    public static final String MODE = "mode";
    public static final int MODE_CREATE = 0;
    public static final int MODE_EDIT = 1;

    private int mMode = MODE_CREATE;
    private Event mEventToEdit;

    private Calendar mStartDateTime;
    private Calendar mEndDateTime;
    private SimpleDateFormat mTimeFormat = new SimpleDateFormat("h:mm a", Locale.getDefault());
    private SimpleDateFormat mDateFormat = new SimpleDateFormat("M/d/yyyy", Locale.getDefault());

    private ProgressDialog mProgressDialog;

    // Views
    private Toolbar mToolbar;

    private EditText mNameText;
    private EditText mHostText;
    private EditText mDescriptionText;
    private EditText mAdditionalInfoText;
    private EditText mAddressView;

    private TextView mStartDateView;
    private TextView mEndDateView;
    private TextView mStartTimeView;
    private TextView mEndTimeView;

    private Button mSaveButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_creation);

        mProgressDialog = new ProgressDialog(this);

        mToolbar = (Toolbar) findViewById(R.id.event_creation_toolbar);
        mNameText = (EditText) findViewById(R.id.event_creation_name_input);
        mHostText = (EditText) findViewById(R.id.event_creation_host_input);
        mDescriptionText = (EditText) findViewById(R.id.event_creation_description_input);
        mAdditionalInfoText = (EditText) findViewById(R.id.event_creation_additional_info_input);
        mAddressView = (EditText) findViewById(R.id.event_creation_address_input);
        mStartDateView = (TextView) findViewById(R.id.event_creation_start_date_input);
        mEndDateView = (TextView) findViewById(R.id.event_creation_end_date_input);
        mStartTimeView = (TextView) findViewById(R.id.event_creation_start_time_input);
        mEndTimeView = (TextView) findViewById(R.id.event_creation_end_time_input);
        mSaveButton = (Button) findViewById(R.id.event_creation_button_save);

        // Sets up the action bar
        setSupportActionBar(mToolbar);

        // Check the mMode
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null && !extras.isEmpty()) {
            mMode = extras.getInt(MODE, MODE_CREATE);
            if (mMode == MODE_EDIT) {
                mEventToEdit = extras.getParcelable(Event.EVENT);
                fillFormWithEvent(mEventToEdit);
                mToolbar.setTitle("Edit Event");
            }
        }

        if (mMode == MODE_CREATE) {
            // Initializes the start and end date and time with the current date and time
            mStartDateTime = Calendar.getInstance();
            mEndDateTime = Calendar.getInstance();
            updateDateTimeText();
            mToolbar.setTitle("Create Event");
        }

        // Allows the ImeOptions to still be actionNext while allowing more than one line of text
        mDescriptionText.setHorizontallyScrolling(false);
        mDescriptionText.setMaxLines(Integer.MAX_VALUE);

        mAdditionalInfoText.setHorizontallyScrolling(false);
        mAdditionalInfoText.setMaxLines(Integer.MAX_VALUE);

        // Sets click listeners for date and time
        setOnClickForDate(mStartDateView, mStartDateTime);
        setOnClickForDate(mEndDateView, mEndDateTime);
        setOnClickForTime(mStartTimeView, mStartDateTime);
        setOnClickForTime(mEndTimeView, mEndDateTime);

        // Click listener for the save button
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMode == MODE_CREATE) {
                    saveEvent(getEventFromForm());
                } else if (mMode == MODE_EDIT) {
                    saveEvent(mEventToEdit);
                }
            }
        });
    }

    private void fillFormWithEvent(Event event) {
        mNameText.setText(event.getName());
        mHostText.setText(event.getHost());
        mDescriptionText.setText(event.getDescription());
        mAdditionalInfoText.setText(event.getAdditionalInfo());

        mStartDateTime = event.getStartTime();
        mEndDateTime = event.getEndTime();
        updateDateTimeText();

        // TODO: Get address from longitude and latitude
    }

    private void updateDateTimeText() {
        mStartDateView.setText(mDateFormat.format(mStartDateTime.getTime()));
        mStartTimeView.setText(mTimeFormat.format(mStartDateTime.getTime()));
        mEndDateView.setText(mDateFormat.format(mEndDateTime.getTime()));
        mEndTimeView.setText(mTimeFormat.format(mEndDateTime.getTime()));
    }

    private void setOnClickForTime(final TextView textView, final Calendar calendar) {
        final TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                textView.setText(mTimeFormat.format(calendar.getTime()));
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
                textView.setText(mDateFormat.format(calendar.getTime()));
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

        if(mMode == MODE_CREATE) {
            // Shows a progress dialog
            mProgressDialog.setMessage("Creating event...");
            mProgressDialog.show();

            // Creates the event in the remote DB
            EventDao dao = new EventDao();
            dao.create(event, this);
        }

        if(mMode == MODE_EDIT) {
            // Shows a progress dialog
            mProgressDialog.setMessage("Editing event...");
            mProgressDialog.show();

            EventDao dao = new EventDao();
            dao.update(event, this);
            // update local db
        }
    }

    private boolean validate() {
        boolean valid = true;

        String name = mNameText.getText().toString();
        if (name.isEmpty()) {
            mNameText.setError("The event needs a name!");
            valid = false;
        }

        // TODO: Validate other things!

        return valid;
    }

    private Event getEventFromForm() {
        String name = mNameText.getText().toString();
        String host = mHostText.getText().toString();
        String description = mDescriptionText.getText().toString();
        String additionalInfo = mAdditionalInfoText.getText().toString();

        // TODO: Get longitude and latitude from address
        Event event = new Event(
                name, host, description, additionalInfo, mStartDateTime, mEndDateTime,
                33.3774338, -111.9759768, 0, 0);
        return event;
    }

    private void saveLocalEvent(Event event) {
        HostedEvent hostedEvent = new HostedEvent(event.getId());
        HostedEventDao hostedDao = new HostedEventDao(this);
        hostedDao.create(hostedEvent);

        LocalEventDao localEventDao = new LocalEventDao(this);
        LocalEvent localEvent = new LocalEvent(event);
        localEventDao.create(localEvent);
    }

    private void updateLocalEvent(Event event) {
        LocalEventDao localEventDao = new LocalEventDao(this);
        LocalEvent localEvent = new LocalEvent(event);
        localEventDao.update(localEvent);
    }

    private void returnResult() {
        if(mProgressDialog.isShowing()) mProgressDialog.dismiss();

        Intent intent = new Intent();
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    @Override
    public void onCreateEvent(String response) {
        try {
            JSONObject json = new JSONObject(response);

            // Check if something bad happened
            if (json.getInt("success") == 0) {
                if(mProgressDialog.isShowing()) mProgressDialog.dismiss();

                Toast.makeText(this, "Something really bad just happened...", Toast.LENGTH_SHORT).show();
                Log.e("OnCreateEvent", json.getString("error"));
                return;
            }

            // Get the event we just saved in the DB
            Long id = json.getLong(Event.REMOTE_ID_KEY);
            EventDao eventDao = new EventDao();
            eventDao.get(id, this);

        } catch (JSONException e) {
            if(mProgressDialog.isShowing()) mProgressDialog.dismiss();
            Toast.makeText(this, "Something really bad just happened...", Toast.LENGTH_SHORT).show();
            Log.e("OnCreateEvent", e.toString());
        }
    }

    @Override
    public void onGetEvent(String response) {
        try {
            JSONObject json = new JSONObject(response);

            // Check if something bad happened
            if (json.getInt("success") == 0) {
                if(mProgressDialog.isShowing()) mProgressDialog.dismiss();

                Toast.makeText(this, "Something really bad just happened...", Toast.LENGTH_SHORT).show();
                Log.e("OnGetEvent", json.getString("error"));
                return;
            }

            // Now with the event, save it in the local DB
            JSONObject result = json.getJSONArray("result").getJSONObject(0);
            Event event = new Event(result);

            if(mMode == MODE_CREATE) saveLocalEvent(event);
            if(mMode == MODE_EDIT) updateLocalEvent(event);

            returnResult();
        } catch (JSONException e) {
            if(mProgressDialog.isShowing()) mProgressDialog.dismiss();
            Toast.makeText(this, "Something really bad just happened...", Toast.LENGTH_SHORT).show();
            Log.e("OnGetEvent", e.toString());
        }
    }

    @Override
    public void onUpdateEvent(String response) {
        try {
            JSONObject json = new JSONObject(response);

            // Check if something bad happened
            if (json.getInt("success") == 0) {
                if(mProgressDialog.isShowing()) mProgressDialog.dismiss();

                Toast.makeText(this, "Something really bad just happened...", Toast.LENGTH_SHORT).show();
                Log.e("OnUpdateEvent", json.getString("error"));
                return;
            }

            // Get the event we just saved in the DB
            Long id = json.getLong(Event.REMOTE_ID_KEY);
            EventDao eventDao = new EventDao();
            eventDao.get(id, this);

        } catch (JSONException e) {
            if(mProgressDialog.isShowing()) mProgressDialog.dismiss();
            Toast.makeText(this, "Something really bad just happened...", Toast.LENGTH_SHORT).show();
            Log.e("OnUpdateEvent", e.toString());
        }
    }
}
