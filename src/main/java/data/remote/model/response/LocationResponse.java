package data.remote.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import data.remote.model.information.Location;

import java.util.List;

/**
 * Created by OldMan on 25.06.2017.
 */
public class LocationResponse extends BaseResponse {
    @SerializedName("data")
    @Expose
    private List<Location> data;

    public LocationResponse(int code, List<Location> data) {
        super(code);
        this.data = data;
    }

    public List<Location> getData() {
        return data;
    }

    public void setData(List<Location> data) {
        this.data = data;
    }
}
