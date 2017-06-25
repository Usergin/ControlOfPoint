package data.remote.model.information;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by OldMan on 25.06.2017.
 */
public class Contact {
    @SerializedName("db_id")
    @Expose
    private int dbId;
    @SerializedName("number")
    @Expose
    private String number;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("work_number")
    @Expose
    private String workNumber;
    @SerializedName("home_number")
    @Expose
    private String homeNumber;
    @SerializedName("main_number")
    @Expose
    private String mainNumber;
    @SerializedName("e_mail")
    @Expose
    private String eMail;
    @SerializedName("info")
    @Expose
    private String info;

    public int getDbId() {
        return dbId;
    }

    public void setDbId(int db_id) {
        this.dbId = db_id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getWorkNumber() {
        return workNumber;
    }

    public void setWorkNumber(String work_number) {
        this.workNumber = work_number;
    }

    public String getHomeNumber() {
        return homeNumber;
    }

    public void setHomeNumber(String home_number) {
        this.homeNumber = home_number;
    }

    public String getMainNumber() {
        return mainNumber;
    }

    public void setMainNumber(String main_number) {
        this.mainNumber = main_number;
    }

    public String getEMail() {
        return eMail;
    }

    public void setEMail(String eMail) {
        this.eMail = eMail;
    }
}
