package com.ford.navigation;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class FinalJourney implements Parcelable{

    private int totalcost;
    private int totaldistance;
    private int totaltime;
    private String kpi;
    private List<Hop1> hops;

    public FinalJourney(int totaltime, int totalcost, int totaldistance, String kpi, List<Hop1> hops) {
        this.totaltime = totaltime;
        this.totalcost = totalcost;
        this.totaldistance = totaldistance;
        this.kpi = kpi;
        this.hops = hops;
    }

    protected FinalJourney(Parcel in) {
        totalcost = in.readInt();
        totaldistance = in.readInt();
        totaltime = in.readInt();
        kpi = in.readString();
        hops = in.readParcelable(List.class.getClassLoader());
    }

    public static final Creator<FinalJourney> CREATOR = new Creator<FinalJourney>() {
        @Override
        public FinalJourney createFromParcel(Parcel in) {
            return new FinalJourney(in);
        }

        @Override
        public FinalJourney[] newArray(int size) {
            return new FinalJourney[size];
        }
    };

    public int getTotalcost() {
        return totalcost;
    }

    public int getTotaldistance() {
        return totaldistance;
    }

    public int getTotaltime() {
        return totaltime;
    }

    public String getKpi() {
        return kpi;
    }

    public List<Hop1> getHops() {
        return hops;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(totalcost);
        parcel.writeInt(totaldistance);
        parcel.writeInt(totaltime);
        parcel.writeString(kpi);
    }
}
