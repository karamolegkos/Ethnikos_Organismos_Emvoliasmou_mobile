package com.example.ethinkosorganismosemboliasmou.ui.radevou;

public class AppointmentModel {

    // Fields for an appointment
    private int id;

    private String date;
    private String time;

    private String name;
    private String surname;
    private String amka;
    private String email;
    private String phone;

    // Constructor
    public AppointmentModel(int id,
                            String date,
                            String time,
                            String name,
                            String surname,
                            String amka,
                            String email,
                            String phone) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.name = name;
        this.surname = surname;
        this.amka = amka;
        this.email = email;
        this.phone = phone;
    }

    // My toString Method
    @Override
    public String toString() {
        return "AppointmentModel{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", amka='" + amka + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

    // Default Constructor
    public AppointmentModel() {
    }

    // Getter and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAmka() {
        return amka;
    }

    public void setAmka(String amka) {
        this.amka = amka;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
