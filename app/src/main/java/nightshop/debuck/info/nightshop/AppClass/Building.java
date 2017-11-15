package nightshop.debuck.info.nightshop.AppClass;

import android.database.Cursor;

import java.io.Serializable;

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


    public Building(){}

    public Building(int id, String name, String address, String description, double lat, double lon) {
        this.id = id;
        this.name = name;
        this.address=address;
        this.lat=lat;
        this.lng =lon;
        this.description=description;
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
                getAddress() + ", lat=" + getLat()+ ", lng=" + getLng()+ ", description=" + getDescription()+ "]");
    }

}
