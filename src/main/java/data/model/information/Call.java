package data.model.information;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by OldMan on 25.06.2017.
 */
public class Call extends BaseInfo{
    @SerializedName("number")
    @Expose
    private String number;
    @SerializedName("duration")
    @Expose
    private int duration;
    @SerializedName("type")
    @Expose
    private int type;

    public Call(String number, int duration, int type) {
        this.number = number;
        this.duration = duration;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
