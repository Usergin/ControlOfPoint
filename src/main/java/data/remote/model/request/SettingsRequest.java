package data.remote.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import data.remote.model.information.Settings;

/**
 * Created by OldMan on 25.06.2017.
 */
public class SettingsRequest {
    @SerializedName("imei")
    @Expose
    private String imei;
    @SerializedName("device")
    @Expose
    private int device;
    @SerializedName("data")
    @Expose
    private Settings settings;

    public SettingsRequest(String imei, int device_id, Settings settings) {
        this.imei = imei;
        this.device = device_id;
        this.settings = settings;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public int getDevice() {
        return device;
    }

    public void setDevice(int device) {
        this.device = device;
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }
}
