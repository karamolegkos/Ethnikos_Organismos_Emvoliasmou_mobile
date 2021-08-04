package com.example.ethinkosorganismosemboliasmou.ui.radevou;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.ethinkosorganismosemboliasmou.ui.radevou.AppointmentModel;

public class DataBaseHelper extends SQLiteOpenHelper {

    public DataBaseHelper(@Nullable Context context){
        super(context, "appointment.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String createTableStatement = "CREATE TABLE APPOINTMENT_TABLE "+
                "(ID INTEGER PRIMARY KEY, " +
                "NAME TEXT, " +
                "SURNAME TEXT, " +
                "AMKA TEXT, " +
                "PHONE TEXT, " +
                "EMAIL TEXT, " +
                "DATE TEXT, " +
                "TIME TEXT)";

        db.execSQL(createTableStatement);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }

    // Inserts an Appointment in APPOINTMENT_TABLE
    public void addAppointment(AppointmentModel appointmentModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("ID",appointmentModel.getId());
        cv.put("NAME",appointmentModel.getName());
        cv.put("SURNAME",appointmentModel.getSurname());
        cv.put("AMKA",appointmentModel.getAmka());
        cv.put("PHONE",appointmentModel.getPhone());
        cv.put("EMAIL",appointmentModel.getEmail());
        cv.put("DATE",appointmentModel.getDate());
        cv.put("TIME",appointmentModel.getTime());

        db.insert("APPOINTMENT_TABLE", null, cv);
    }

    // Returns true if an appointments exists
    // Returns false if there is no appointment
    public boolean checkForAppointment(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM APPOINTMENT_TABLE ", null);
        boolean appointmentFound = false;
        // see if there is a first record in the answer
        if (c.moveToFirst()){
            appointmentFound = true;
        }
        return appointmentFound;
    }

    // Gets the first appointment
    public AppointmentModel getAppointment(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM APPOINTMENT_TABLE ", null);
        AppointmentModel appointmentModel = null;
        if (c.moveToFirst()){
            int appointmentID = c.getInt(0);
            String appointmentName = c.getString(1);
            String appointmentSurname = c.getString(2);
            String appointmentAmka = c.getString(3);
            String appointmentPhone = c.getString(4);
            String appointmentEmail = c.getString(5);
            String appointmentDate = c.getString(6);
            String appointmentTime = c.getString(7);
            appointmentModel = new AppointmentModel(appointmentID,
                    appointmentDate,
                    appointmentTime,
                    appointmentName,
                    appointmentSurname,
                    appointmentAmka,
                    appointmentEmail,
                    appointmentPhone);
        }
        return appointmentModel;
    }

    // Deletes an appointment
    public void deleteAppointment(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM APPOINTMENT_TABLE WHERE ID = "+id;

        Cursor c = db.rawQuery(queryString, null);
        c.moveToFirst();
    }
}
