package data.remote.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import data.model.information.ServiceEvent;

import java.util.List;

/**
 * Created by OldMan on 25.06.2017.
 */
public class ServiceEventResponse extends BaseResponse {
    @SerializedName("data")
    @Expose
    private List<ServiceEvent> data;

    public ServiceEventResponse(int code, List<ServiceEvent> data) {
        super(code);
        this.data = data;
    }

    public List<ServiceEvent> getData() {
        return data;
    }

    public void setData(List<ServiceEvent> data) {
        this.data = data;
    }
}
