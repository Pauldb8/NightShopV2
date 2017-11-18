package nightshop.debuck.info.nightshop.AppClass;

import android.database.Cursor;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import nightshop.debuck.info.nightshop.Tools.MyDBHelper;

/**
 * Created by Paul on 10/5/2017.
 */

public class Building implements Serializable {

    private int id;
    private String name;
    private String address;
    private String description;
    private double lat;
    private double lng;
    private double distance;
    private int hour_open;
    private  int minute_open;
    private int hour_end;
    private int minute_end;
    private SimpleDateFormat formatAsHour = new SimpleDateFormat("HH:mm");
    private Calendar start = Calendar.getInstance();
    private Calendar end = Calendar.getInstance();


    public Building(){}

    public Building(int id, String name, String address, String description, double lat, double lon,
                    double distance, int hour_open,int minute_open,int hour_end, int minute_end) {
        this.id = id;
        this.name = name;
        this.address=address;
        this.lat=lat;
        this.lng =lon;
        this.description=description;
        this.distance=distance;
        this.hour_open=hour_open;
        this.minute_open=minute_open;
        this.hour_end=hour_end;
        this.minute_end=minute_end;
    }



    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public  double getLat(){ return lat;}
    public  void setLat(double lat){ this.lat=lat;}

    public  double getLng(){ return lng;}
    public  void setLng(double lng){ this.lng = lng;}


    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public  String getDistance(){ return String.format("%.2f", this.distance);}
    public  void setDistance(double distance){ this.distance = distance;}

    public String getHoursFormatted(){
        start.set(Calendar.HOUR_OF_DAY, this.hour_open);
        start.set(Calendar.MINUTE, this.minute_open);
        end.set(Calendar.HOUR_OF_DAY, this.hour_end);
        end.set(Calendar.MINUTE, this.minute_end);
        return formatAsHour.format(start.getTime()) + " - " + formatAsHour.format(end.getTime()) ;
    }


    public int getHour_open() {return hour_open;}

    public void setHour_open(int hour_open) {this.hour_open = hour_open;}

    public int getMinute_open() {return minute_open;}

    public void setMinute_open(int minute_open) {this.minute_open = minute_open;}

    public int getHour_end() {return hour_end;}

    public void setHour_end(int hour_end) {this.hour_end = hour_end;}

    public int getMinute_end() {return minute_end;}

    public void setMinute_end(int minute_end) {this.minute_end = minute_end;}

    public String getHours(){
        return this.hour_open + ":" + minute_open + " - " + hour_end + ":" + minute_end;
    }

    public String getTwoLinesAddress(){
        return this.address.replace(", ", "\n");
    }

    public void populateObject(Cursor c){
        try{
            this.setId(c.getInt(
                    c.getColumnIndexOrThrow(MyDBHelper.MyDBContract.Building.COLUMN_NAME_BUILDING_ID)));
            this.setName(c.getString(
                    c.getColumnIndexOrThrow(MyDBHelper.MyDBContract.Building.COLUMN_NAME_BUILDING_NAME)));
            this.setAddress((c.getString(
                    c.getColumnIndexOrThrow(MyDBHelper.MyDBContract.Building.COLUMN_NAME_BUILDING_ADDRESS))));
            this.setLat((c.getDouble(
                    c.getColumnIndexOrThrow(MyDBHelper.MyDBContract.Building.COLUMN_NAME_BUILDING_LAT))));
            this.setLng((c.getDouble(
                    c.getColumnIndexOrThrow(MyDBHelper.MyDBContract.Building.COLUMN_NAME_BUILDING_LNG))));
            this.setDescription(c.getString(
                    c.getColumnIndexOrThrow(MyDBHelper.MyDBContract.Building.COLUMN_NAME_BUILDING_DESCRIPTION)));

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public String toString(){
        return ("[Building: id=" + getId() + ", name=" + getName() +", address=" +
                getAddress() + ", lat=" + getLat()+ ", lng=" + getLng()+ ", description=" +
                getDescription() + ", distance=" + getDistance()
                + ", hour_open=" + getHour_open()
                + ", minute_open=" + getMinute_open()
                + ", hour_end=" + getHour_end()
                + ", minute_end=" + getMinute_end()
                +"]");
    }

}
