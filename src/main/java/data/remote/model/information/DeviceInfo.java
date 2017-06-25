package data.remote.model.information;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

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
    @SerializedName("serial_num")
    @Expose
    private String serialNum;
    @SerializedName("version_os")
    @Expose
    private String versionOs;
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
    private String screenSize;
    @SerializedName("is_dual_sim")
    @Expose
    private String isDualSim;
    @SerializedName("imei_sim1")
    @Expose
    private String imeiSim1;
    @SerializedName("imei_sim2")
    @Expose
    private String imeiSim2;
    @SerializedName("mcc")
    @Expose
    private String mcc;
    @SerializedName("mnc")
    @Expose
    private String mnc;
    @SerializedName("phone_type")
    @Expose
    private String phoneType;
    @SerializedName("network_type")
    @Expose
    private String networkType;
    @SerializedName("network")
    @Expose
    private String network;
    @SerializedName("operator_name")
    @Expose
    private String operatorName;
    @SerializedName("is_root")
    @Expose
    private boolean isRoot;

    public boolean isRoot() {
        return isRoot;
    }

    public void setRoot(boolean is_root) {
        this.isRoot = is_root;
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

    public String getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(String serial_num) {
        this.serialNum = serial_num;
    }

    public String getVersionOS() {
        return versionOs;
    }

    public void setVersionOS(String version_os) {
        this.versionOs = version_os;
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


    public String getScreenSize() {
        return screenSize;
    }

    public void setScreenSize(String screen_size) {
        this.screenSize = screen_size;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }


    public String getIsDualSim() {
        return isDualSim;
    }

    public void setIsDualSim(String is_dual_sim) {
        this.isDualSim = is_dual_sim;
    }

    public String getImeiSim1() {
        return imeiSim1;
    }

    public void setImeiSim1(String imei_sim1) {
        this.imeiSim1 = imei_sim1;
    }

    public String getImeiSim2() {
        return imeiSim2;
    }

    public void setImeiSim2(String imei_sim2) {
        this.imeiSim2 = imei_sim2;
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

    public String getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(String phone_type) {
        this.phoneType = phone_type;
    }

    public String getNetworkType() {
        return networkType;
    }

    public void setNetworkType(String network_type) {
        this.networkType = network_type;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operator_name) {
        this.operatorName = operator_name;
    }

//    public void getListItem(){
//        List<String> strings = new ArrayList<>();
//        strings
//    }
}
