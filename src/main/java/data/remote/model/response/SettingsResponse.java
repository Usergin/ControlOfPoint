package data.remote.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import data.remote.model.information.Settings;

/**
 * Created by OldMan on 25.06.2017.
 */
public class SettingsResponse extends BaseResponse {
    @SerializedName("settings")
    @Expose
    private Settings settings;
    public SettingsResponse(int code, Settings settings) {
        super(code);
        this.settings = settings;
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }
}
