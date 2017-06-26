package data.remote.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import data.remote.model.information.BatteryEvent;

import java.util.List;

/**
 * Created by OldMan on 25.06.2017.
 */
public class BatteryEventResponse extends BaseResponse {
    @SerializedName("data")
    @Expose
    private List<BatteryEvent> data;

    public BatteryEventResponse(int code, List<BatteryEvent> data) {
        super(code);
        this.data = data;
    }

    public List<BatteryEvent> getData() {
        return data;
    }

    public void setData(List<BatteryEvent> data) {
        this.data = data;
    }
}
