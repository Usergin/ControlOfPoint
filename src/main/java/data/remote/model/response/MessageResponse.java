package data.remote.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import data.remote.model.information.Message;

import java.util.List;

/**
 * Created by OldMan on 25.06.2017.
 */
public class MessageResponse extends BaseResponse {
    @SerializedName("info")
    @Expose
    private List<Message> data;

    public MessageResponse(int code, List<Message> data) {
        super(code);
        this.data = data;
    }

    public List<Message> getData() {
        return data;
    }

    public void setData(List<Message> data) {
        this.data = data;
    }
}
