package com.github.funnygopher.parti.hosting;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
import com.github.funnygopher.parti.dao.tasks.UpdateEventTask;
import com.github.funnygopher.parti.model.Event;
import com.github.funnygopher.parti.model.HostedEvent;
import com.github.funnygopher.parti.model.LocalEvent;
import com.github.funnygopher.parti.util.DateUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class EventCreationActivity extends AppCompatActivity implements
        CreateEventTask.OnCreateEventListener, UpdateEventTask.OnUpdateEventListener {

    // Activity modes
    public static final String MODE = "mode";
    public static final int MODE_CREATE = 0;
    public static final int MODE_EDIT = 1;

    private int mMode = MODE_CREATE;
    private Event mEventToSave = new Event();

    private Calendar mStartDateTime = Calendar.getInstance();
    private Calendar mEndDateTime = Calendar.getInstance();

    private ProgressDialog mProgressDialog;

    private InputMethodManager mImm;

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

        mImm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

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
                mEventToSave = extras.getParcelable(Event.EVENT);
                fillFormWithEvent(mEventToSave);
                mToolbar.setTitle("Edit Event");
            }
        }

        if (mMode == MODE_CREATE) {
            // Initializes the start and end date and time with the current date and time
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
        setOnClickForTime(mStartTimeView, mStartDateView, mStartDateTime);
        setOnClickForTime(mEndTimeView, mEndDateView, mEndDateTime);

        // Click listener for the save button
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveEvent();
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
        mStartDateView.setText(DateUtil.dateToString(mStartDateTime));
        mStartTimeView.setText(DateUtil.timeToString(mStartDateTime));
        mEndDateView.setText(DateUtil.dateToString(mEndDateTime));
        mEndTimeView.setText(DateUtil.timeToString(mEndDateTime));
    }

    private void setOnClickForTime(final TextView timeTextView, final TextView dateTextView, final Calendar calendar) {
        final DatePickerDialog.OnDateSetListener dateSetListener= new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(year, monthOfYear, dayOfMonth);
                dateTextView.setText(DateUtil.dateToString(calendar));
            }
        };

        final TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                timeTextView.setText(DateUtil.timeToString(calendar));

                new DatePickerDialog(EventCreationActivity.this,
                        dateSetListener,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH))
                        .show();
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

                mImm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        };
        timeTextView.setOnClickListener(onTimeClick);
    }

    private void setOnClickForDate(final TextView dateTextView,  final Calendar calendar) {
        final DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(year, monthOfYear, dayOfMonth);
                dateTextView.setText(DateUtil.dateToString(calendar));
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

                mImm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        };
        dateTextView.setOnClickListener(onDateClick);
    }

    private void saveEvent() {
        if (!validate()) {
            return;
        }

        EventDao dao = new EventDao();
        if(mMode == MODE_CREATE) {
            mEventToSave = getEventFromForm();

            // Shows a progress dialog
            mProgressDialog.setMessage("Creating event...");
            mProgressDialog.show();

            // Creates the event in the remote DB
            dao.create(mEventToSave, this);
        }

        if(mMode == MODE_EDIT) {
            mEventToSave.copy(getEventFromForm());

            // Shows a progress dialog
            mProgressDialog.setMessage("Updating event...");
            mProgressDialog.show();

            dao.update(mEventToSave, this);
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

    private void createLocal() {
        HostedEvent hostedEvent = new HostedEvent(mEventToSave.getId());
        HostedEventDao hostedDao = new HostedEventDao(this);
        hostedDao.create(hostedEvent);

        LocalEventDao localEventDao = new LocalEventDao(this);
        LocalEvent localEvent = new LocalEvent(mEventToSave);
        localEventDao.create(localEvent);
    }

    private void updateLocal() {
        LocalEventDao localEventDao = new LocalEventDao(this);
        LocalEvent localEvent = localEventDao.find(mEventToSave);
        localEvent.copy(mEventToSave);
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

            // Save give the event the remote id and save it locally
            Long id = json.getLong(Event.REMOTE_ID_KEY);
            mEventToSave.setId(id);
            createLocal();

            returnResult();
        } catch (JSONException e) {
            if(mProgressDialog.isShowing()) mProgressDialog.dismiss();
            Toast.makeText(this, "Something really bad just happened...", Toast.LENGTH_SHORT).show();
            Log.e("OnCreateEvent", e.toString());
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

            // Update the event locally
            updateLocal();

            returnResult();
        } catch (JSONException e) {
            if(mProgressDialog.isShowing()) mProgressDialog.dismiss();
            Toast.makeText(this, "Something really bad just happened...", Toast.LENGTH_SHORT).show();
            Log.e("OnUpdateEvent", e.toString());
        }
    }
}
