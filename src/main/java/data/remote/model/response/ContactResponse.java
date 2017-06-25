package data.remote.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import data.remote.model.information.Contact;

import java.util.List;

/**
 * Created by OldMan on 25.06.2017.
 */
public class ContactResponse extends BaseResponse {
    @SerializedName("info")
    @Expose
    private List<Contact> data;

    public ContactResponse(int code, List<Contact> data) {
        super(code);
        this.data = data;
    }

    public List<Contact> getData() {
        return data;
    }

    public void setData(List<Contact> data) {
        this.data = data;
    }
}
