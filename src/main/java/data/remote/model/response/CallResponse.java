package data.remote.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import data.model.information.Call;

import java.util.List;

/**
 * Created by OldMan on 25.06.2017.
 */
public class CallResponse extends BaseResponse {
    @SerializedName("data")
    @Expose
    private List<Call> data;

    public CallResponse(int code, List<Call> data) {
        super(code);
        this.data = data;
    }

    public List<Call> getData() {
        return data;
    }

    public void setData(List<Call> data) {
        this.data = data;
    }
}
