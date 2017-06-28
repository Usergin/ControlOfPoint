package data.model.information;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by OldMan on 25.06.2017.
 */
public class Message extends BaseInfo {
    @SerializedName("number")
    @Expose
    private String number;
    @SerializedName("data")
    @Expose
    private String data;
    @SerializedName("type")
    @Expose
    private int type;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
