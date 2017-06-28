package data.model.information;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by OldMan on 25.06.2017.
 */
public class BaseInfo {
    @SerializedName("date")
    @Expose
    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
//        java.util.Calendar cal = Calendar.getInstance();
//        cal.setTimeInMillis(date);
//        this.date = cal.getTime();
    }
}
