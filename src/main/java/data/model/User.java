package data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by OldMan on 12.06.2017.
 */
@XmlRootElement(name = "user")
@XmlType(propOrder = {"login", "password", "department", "username", "rank"})
public class User {
    @SerializedName("login")
    @Expose
    private String login;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("department")
    @Expose
    private String department;
    @SerializedName("rank")
    @Expose
    private String rank;

    public String getLogin() {
        return login;
    }

    @XmlElement
    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    @XmlElement
    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    @XmlElement
    public void setUsername(String username) {
        this.username = username;
    }

    public String getDepartment() {
        return department;
    }

    @XmlElement
    public void setDepartment(String department) {
        this.department = department;
    }

    public String getRank() {
        return rank;
    }

    @XmlElement
    public void setRank(String rank) {
        this.rank = rank;
    }
}
