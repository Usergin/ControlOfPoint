package data.remote;

import data.remote.model.information.Settings;
import data.remote.model.request.Authentication;
import data.remote.model.response.DeviceListResponse;
import data.remote.model.response.DeviceResponse;
import data.remote.model.response.UserResponse;
import io.reactivex.Single;
import retrofit2.http.*;

/**
 * Created by OldMan on 17.06.2017.
 */
public interface ServerApi {
    @Headers({"Accept: application/json"})
    @POST("user")
    Single<UserResponse> getUser(@Body Authentication authentication);

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST("/setting")
    Single<UserResponse> setSettingsDevice(@Field("imei") String imei,
                                           @Field("control_panel") int device,
                                           @Field("settings") Settings settings);

    @Headers({"Accept: application/json"})
    @GET("/devices")
    Single<DeviceListResponse> getDevices();


    @Headers({"Accept: application/json"})
    @GET("/control_panel/{id}")
    Single<DeviceResponse> getDeviceById(@Path("id") int device_id);


    @Headers({"Accept: application/json"})
    @GET("/control_panel/{id}/rm")
    Single<UserResponse> rmDeviceById(@Path("id") int device_id);

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @GET("/control_panel/all_rm")
    Single<UserResponse> rmAllDevice();

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @GET("/control_panel/{id}/settings")
    Single<UserResponse> getSettingsDeviceById();

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @GET("/control_panel/{id}/apps")
    Single<UserResponse> getInstallAppsDeviceById();

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @GET("/control_panel/{id}/battery_status")
    Single<UserResponse> getBatteryStatsDeviceById();

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @GET("/control_panel/{id}/calls")
    Single<UserResponse> getCallsDeviceById();

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @GET("/control_panel/{id}/contacts")
    Single<UserResponse> getContactsDeviceById();

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @GET("/control_panel/{id}/status")
    Single<UserResponse> getStatusDeviceById();

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @GET("/control_panel/{id}/info")
    Single<UserResponse> getInfoDeviceById();

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @GET("/control_panel/{id}/locations")
    Single<UserResponse> getLocationsDeviceById();

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @GET("/control_panel/{id}/messages")
    Single<UserResponse> getMessagesDeviceById();

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @GET("/control_panel/{id}/network")
    Single<UserResponse> getNetworkDeviceById();

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @GET("/control_panel/{id}/service")
    Single<UserResponse> getServiceDeviceById();

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @GET("/control_panel/{id}/battery")
    Single<UserResponse> getBatteryDeviceById();
}
