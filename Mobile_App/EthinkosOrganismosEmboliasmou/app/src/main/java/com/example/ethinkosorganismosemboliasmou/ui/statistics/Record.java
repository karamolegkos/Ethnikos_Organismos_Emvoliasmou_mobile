package com.example.ethinkosorganismosemboliasmou.ui.statistics;

import com.google.gson.annotations.SerializedName;

public class Record {

    @SerializedName("area")
    private String area;

    @SerializedName("areaid")
    private int areaid;

    @SerializedName("dailydose1")
    private int dailydose1;

    @SerializedName("dailydose2")
    private int dailydose2;

    @SerializedName("daydiff")
    private int daydiff;

    @SerializedName("daytotal")
    private int daytotal;

    @SerializedName("referencedate")
    private String referencedate;

    @SerializedName("totaldistinctpersons")
    private int totaldistinctpersons;

    @SerializedName("totaldose1")
    private int totaldose1;

    @SerializedName("totaldose2")
    private int totaldose2;

    @SerializedName("totalvaccinations")
    private int totalvaccinations;

    public String getArea() {
        return area;
    }

    public int getAreaid() {
        return areaid;
    }

    public int getDailydose1() {
        return dailydose1;
    }

    public int getDailydose2() {
        return dailydose2;
    }

    public int getDaydiff() {
        return daydiff;
    }

    public int getDaytotal() {
        return daytotal;
    }

    public String getReferencedate() {
        return referencedate;
    }

    public int getTotaldistinctpersons() {
        return totaldistinctpersons;
    }

    public int getTotaldose1() {
        return totaldose1;
    }

    public int getTotaldose2() {
        return totaldose2;
    }

    public int getTotalvaccinations() {
        return totalvaccinations;
    }
}
