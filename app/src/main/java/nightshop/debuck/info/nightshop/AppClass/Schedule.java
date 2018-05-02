package nightshop.debuck.info.nightshop.AppClass;

import android.database.Cursor;

import nightshop.debuck.info.nightshop.Tools.MyDBHelper;

import java.io.Serializable;

/**
 * Created by paul on 9/14/2016.
 */
public class Schedule implements Serializable {

    private int id;
    private int hour_open;
    private int minute_open;
    private int hour_close;
    private int minute_close;
    private int fk_buildings_id;

    /**
     * PUBLIC CONSTRUCTORS
     */
    public Schedule() {}

    public Schedule(int id, int hour_open, int minute_open, int hour_close, int minute_close,
                    int fk_buildings_id) {
        this.id = id;

        this.hour_open = hour_open;
        this.minute_open = minute_open;
        this.hour_close = hour_close;
        this.minute_close = minute_close;
        this.fk_buildings_id = fk_buildings_id;
    }

    public Schedule(Cursor c){
        this.populateObject(c);
    }

    /**
     * GETTERS AND SETTERS
     */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHour_open() {return hour_open;}

    public void setHour_open(int hour_open) {this.hour_open = hour_open;}

    public int getMinute_open() {return minute_open;}

    public void setMinute_open(int minute_open) {this.minute_open = minute_open;}

    public int getHour_close() {return hour_close;}

    public void setHour_close(int hour_close) {this.hour_close = hour_close;}

    public int getMinute_close() {return minute_close;}

    public void setMinute_close(int minute_close) {this.minute_close = minute_close;}

    public int getFk_buildings_id() {
        return fk_buildings_id;
    }

    public void setFk_buildings_id(int fk_buildings_id) {
        this.fk_buildings_id = fk_buildings_id;
    }

    public void populateObject(Cursor c){
        try{
            this.setId(c.getInt(
                    c.getColumnIndexOrThrow(MyDBHelper.MyDBContract.Schedule.COLUMN_NAME_SCHEDULE_ID)));
            this.setHour_open(c.getInt(
                    c.getColumnIndexOrThrow(MyDBHelper.MyDBContract.Schedule.COLUMN_NAME_SCHEDULE_TIME_START)));
            this.setMinute_close(c.getInt(
                    c.getColumnIndexOrThrow(MyDBHelper.MyDBContract.Schedule.COLUMN_NAME_SCHEDULE_TIME_END)));
            this.setFk_buildings_id(c.getInt(
                    c.getColumnIndexOrThrow(MyDBHelper.MyDBContract.Schedule.COLUMN_NAME_SCHEDULE_FK_BUILDINGS_ID)));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public String toString(){
        return ("[Schedule: id_day=" + getId() + ", hour_open=" + getHour_open() +", minute_open=" +
        getMinute_open() + ", hour_close=" + getHour_close() + ", minute_close=" + getMinute_close() + ", fk_buildings_id=" + getFk_buildings_id() + "]");
    }
}
