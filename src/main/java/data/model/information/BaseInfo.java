package data.model.information;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by OldMan on 25.06.2017.
 */
public class BaseInfo {
    @SerializedName("date")
    @Expose
    private Date date;

    public Date getDate(){
        return this.date;
    }
    public  String getFormatDate(){
        SimpleDateFormat formatter = new
                SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date createDate = formatter.parse(created_at);
        return formatter.format(date);
    }
    public void setDate(Date date) {
        this.date = date;
    }
}
