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
    @SerializedName("device_id")
    @Expose
    private int deviceId;
    @SerializedName("settings")
    @Expose
    private Settings settings;

    public SettingsRequest(String imei, int device_id, Settings settings) {
        this.imei = imei;
        this.deviceId = device_id;
        this.settings = settings;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }
}
