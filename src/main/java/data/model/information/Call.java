package data.model.information;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by OldMan on 25.06.2017.
 */
@XmlRootElement(name = "call")
@XmlType(propOrder = {"number", "duration", "type", "date"})
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

    public int getType() {
        return type;
    }

    @XmlElement
    public void setType(int type) {
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    @XmlElement
    public void setNumber(String number) {
        this.number = number;
    }

    public int getDuration() {
        return duration;
    }

    @XmlElement
    public void setDuration(int duration) {
        this.duration = duration;
    }
}
