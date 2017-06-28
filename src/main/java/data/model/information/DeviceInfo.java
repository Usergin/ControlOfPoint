package data.model.information;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by OldMan on 25.06.2017.
 */
public class DeviceInfo {
    @SerializedName("imei")
    @Expose
    private String imei;
    @SerializedName("model")
    @Expose
    private String model;
    @SerializedName("serial_number")
    @Expose
    private String serial_number;
    @SerializedName("version_os")
    @Expose
    private String version_os;
    @SerializedName("brand")
    @Expose
    private String brand;
    @SerializedName("imsi")
    @Expose
    private String imsi;
    @SerializedName("manufactured")
    @Expose
    private String manufactured;
    @SerializedName("product")
    @Expose
    private String product;
    @SerializedName("sdk")
    @Expose
    private String sdk;
    @SerializedName("screen_size")
    @Expose
    private String screen_size;
    @SerializedName("is_dual_sim")
    @Expose
    private String dual_sim;
    @SerializedName("imei_sim1")
    @Expose
    private String imei_sim1;
    @SerializedName("imei_sim2")
    @Expose
    private String imei_sim2;
    @SerializedName("mcc")
    @Expose
    private String mcc;
    @SerializedName("mnc")
    @Expose
    private String mnc;
    @SerializedName("phone_type")
    @Expose
    private String phone_type;
    @SerializedName("network_type")
    @Expose
    private String network_type;
    @SerializedName("network")
    @Expose
    private String network;
    @SerializedName("operator_name")
    @Expose
    private String operator_name;
    @SerializedName("is_root")
    @Expose
    private boolean root;

    public boolean isRoot() {
        return root;
    }

    public void setRoot(boolean is_root) {
        this.root = is_root;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSerial_number() {
        return serial_number;
    }

    public void setSerial_number(String serial_number) {
        this.serial_number = serial_number;
    }

    public String getVersionOS() {
        return version_os;
    }

    public void setVersionOS(String version_os) {
        this.version_os = version_os;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public String getManufactured() {
        return manufactured;
    }

    public void setManufactured(String manufactured) {
        this.manufactured = manufactured;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getSdk() {
        return sdk;
    }

    public void setSdk(String sdk) {
        this.sdk = sdk;
    }


    public String getScreen_size() {
        return screen_size;
    }

    public void setScreen_size(String screen_size) {
        this.screen_size = screen_size;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }


    public String getDual_sim() {
        return dual_sim;
    }

    public void setDual_sim(String is_dual_sim) {
        this.dual_sim = is_dual_sim;
    }

    public String getImei_sim1() {
        return imei_sim1;
    }

    public void setImei_sim1(String imei_sim1) {
        this.imei_sim1 = imei_sim1;
    }

    public String getImei_sim2() {
        return imei_sim2;
    }

    public void setImei_sim2(String imei_sim2) {
        this.imei_sim2 = imei_sim2;
    }

    public String getMcc() {
        return mcc;
    }

    public void setMcc(String mcc) {
        this.mcc = mcc;
    }

    public String getMnc() {
        return mnc;
    }

    public void setMnc(String mnc) {
        this.mnc = mnc;
    }

    public String getPhone_type() {
        return phone_type;
    }

    public void setPhone_type(String phone_type) {
        this.phone_type = phone_type;
    }

    public String getNetwork_type() {
        return network_type;
    }

    public void setNetwork_type(String network_type) {
        this.network_type = network_type;
    }

    public String getOperator_name() {
        return operator_name;
    }

    public void setOperator_name(String operator_name) {
        this.operator_name = operator_name;
    }

//    public void getListItem(){
//        List<String> strings = new ArrayList<>();
//        strings
//    }
}
