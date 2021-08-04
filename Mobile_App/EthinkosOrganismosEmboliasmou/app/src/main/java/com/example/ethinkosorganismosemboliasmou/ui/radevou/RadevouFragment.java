package com.example.ethinkosorganismosemboliasmou.ui.radevou;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.ethinkosorganismosemboliasmou.R;

import java.util.Calendar;

public class RadevouFragment extends Fragment {

    private RadevouModel radevouModel;

    // variables for my date and time pickers
    private Button btnDatePicker, btnTimePicker;
    private EditText txtDate, txtTime;
    private int mYear, mMonth, mDay, mHour, mMinute;

    // variable to know if an appointment is made
    private boolean appointmentMade;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        radevouModel =
                new ViewModelProvider(this).get(RadevouModel.class);
        View root = inflater.inflate(R.layout.fragment_radevou, container, false);

        btnDatePicker=(Button)root.findViewById(R.id.date_button);
        btnTimePicker=(Button)root.findViewById(R.id.time_button);
        txtDate=(EditText)root.findViewById(R.id.date_field);
        txtTime=(EditText)root.findViewById(R.id.time_field);

        // Listener to handle date picks by the user
        btnDatePicker.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        // Listener to handle time picks by the user
        btnTimePicker.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // Get Current Time
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                txtTime.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });

        /** Below is the code to handle the SQLite DB**/
        // Check if there is an appointment to show the right Views
        DataBaseHelper dataBaseHelper = new DataBaseHelper(getActivity());
        appointmentMade = dataBaseHelper.checkForAppointment();
        if(appointmentMade){
            ScrollView appointmentExistsView = root.findViewById(R.id.appointment_exists_view);
            ScrollView getAppointmentView = root.findViewById(R.id.get_appointment_view);

            getAppointmentView.setVisibility(View.GONE);
            appointmentExistsView.setVisibility(View.VISIBLE);

            // Show the inserted appointment
            AppointmentModel appointmentModel = dataBaseHelper.getAppointment();
            TextView appointmentName = (TextView)root.findViewById(R.id.ready_appointment_name);
            TextView appointmentSurname = (TextView)root.findViewById(R.id.ready_appointment_surname);
            TextView appointmentAmka = (TextView)root.findViewById(R.id.ready_appointment_amka);
            TextView appointmentPhone = (TextView)root.findViewById(R.id.ready_appointment_phone);
            TextView appointmentEmail = (TextView)root.findViewById(R.id.ready_appointment_email);
            TextView appointmentDate = (TextView)root.findViewById(R.id.ready_appointment_date);
            TextView appointmentTime = (TextView)root.findViewById(R.id.ready_appointment_time);

            appointmentName.setText(appointmentModel.getName());
            appointmentSurname.setText(appointmentModel.getSurname());
            appointmentAmka.setText(appointmentModel.getAmka());
            appointmentPhone.setText(appointmentModel.getPhone());
            appointmentEmail.setText(appointmentModel.getEmail());
            appointmentDate.setText(appointmentModel.getDate());
            appointmentTime.setText(appointmentModel.getTime());
        }
        else{
            ScrollView appointmentExistsView = root.findViewById(R.id.appointment_exists_view);
            ScrollView getAppointmentView = root.findViewById(R.id.get_appointment_view);

            appointmentExistsView.setVisibility(View.GONE);
            getAppointmentView.setVisibility(View.VISIBLE);
        }

        // If an Appointment is made then add it and change the showing views
        Button submitAppointmentButton = root.findViewById(R.id.appointment_request_button);
        submitAppointmentButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                EditText name = root.findViewById(R.id.name_field);
                EditText surname = root.findViewById(R.id.surname_field);
                EditText amka = root.findViewById(R.id.amka_field);
                EditText phone = root.findViewById(R.id.phone_field);
                EditText email = root.findViewById(R.id.email_field);
                EditText date = root.findViewById(R.id.date_field);
                EditText time = root.findViewById(R.id.time_field);

                AppointmentModel appointmentModel = new AppointmentModel(
                        0,
                        date.getText().toString(),
                        time.getText().toString(),
                        name.getText().toString(),
                        surname.getText().toString(),
                        amka.getText().toString(),
                        email.getText().toString(),
                        phone.getText().toString());

                DataBaseHelper dataBaseHelper = new DataBaseHelper(getActivity());
                dataBaseHelper.addAppointment(appointmentModel);

                ScrollView appointmentExistsView = root.findViewById(R.id.appointment_exists_view);
                ScrollView getAppointmentView = root.findViewById(R.id.get_appointment_view);

                getAppointmentView.setVisibility(View.GONE);
                appointmentExistsView.setVisibility(View.VISIBLE);

                // Show the inserted appointment
                AppointmentModel appointmentModelFromDB = dataBaseHelper.getAppointment();
                TextView appointmentName = (TextView)root.findViewById(R.id.ready_appointment_name);
                TextView appointmentSurname = (TextView)root.findViewById(R.id.ready_appointment_surname);
                TextView appointmentAmka = (TextView)root.findViewById(R.id.ready_appointment_amka);
                TextView appointmentPhone = (TextView)root.findViewById(R.id.ready_appointment_phone);
                TextView appointmentEmail = (TextView)root.findViewById(R.id.ready_appointment_email);
                TextView appointmentDate = (TextView)root.findViewById(R.id.ready_appointment_date);
                TextView appointmentTime = (TextView)root.findViewById(R.id.ready_appointment_time);

                appointmentName.setText(appointmentModelFromDB.getName());
                appointmentSurname.setText(appointmentModelFromDB.getSurname());
                appointmentAmka.setText(appointmentModelFromDB.getAmka());
                appointmentPhone.setText(appointmentModelFromDB.getPhone());
                appointmentEmail.setText(appointmentModelFromDB.getEmail());
                appointmentDate.setText(appointmentModelFromDB.getDate());
                appointmentTime.setText(appointmentModelFromDB.getTime());

            }
        });

        // If an Appointment is deleted then delete it and change the showing views
        Button deleteButton = root.findViewById(R.id.cancel_appointment_button);
        deleteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                DataBaseHelper dataBaseHelper = new DataBaseHelper(getActivity());
                dataBaseHelper.deleteAppointment(0);

                ScrollView appointmentExistsView = root.findViewById(R.id.appointment_exists_view);
                ScrollView getAppointmentView = root.findViewById(R.id.get_appointment_view);

                appointmentExistsView.setVisibility(View.GONE);
                getAppointmentView.setVisibility(View.VISIBLE);
            }
        });
        /*
        final Button button = root.findViewById(R.id.button_radevou);
        radevouModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                button.setText(s);
            }
        });
        */
        return root;
    }
}