package data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by OldMan on 12.06.2017.
 */

@XmlRootElement(name = "device")
@XmlType(propOrder = {"id", "imei", "model", "version_os", "longitude", "latitude"})
public class Device {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("imei")
    @Expose
    private String imei;
    @SerializedName("model")
    @Expose
    private String model;
    @SerializedName("version_os")
    @Expose
    private String version_os;
    @SerializedName("longitude")
    @Expose
    private Double longitude;
    @SerializedName("latitude")
    @Expose
    private Double latitude;

    public int getId() {
        return id;
    }

    @XmlElement
    public void setId(int id) {
        this.id = id;
    }

    public String getImei() {
        return imei;
    }

    @XmlElement
    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getModel() {
        return model;
    }

    @XmlElement
    public void setModel(String model) {
        this.model = model;
    }

    public String getVersion_os() {
        return version_os;
    }

    @XmlElement
    public void setVersion_os(String version_os) {
        this.version_os = version_os;
    }

    public Double getLongitude() {
        return longitude;
    }

    @XmlElement
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    @XmlElement
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
}
