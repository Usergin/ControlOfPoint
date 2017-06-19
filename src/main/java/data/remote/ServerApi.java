package data.remote;

import data.remote.model.request.Authentication;
import data.remote.model.information.Settings;
import data.remote.model.response.UserResponse;
import io.reactivex.Single;
import retrofit2.http.*;

/**
 * Created by OldMan on 17.06.2017.
 */
public interface ServerApi {
    @Headers({"Accept: application/json"})
//    @FormUrlEncoded
    @POST("user")
    Single<UserResponse> getUser(@Body Authentication authentication);

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST("/setting")
    Single<UserResponse> setSettingsDevice(@Field("imei") String imei,
                                         @Field("device") int device,
                                         @Field("settings") Settings settings);

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @GET("/devices")
    Single<UserResponse> getDevices();


    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @GET("/device/{id}")
    Single<UserResponse> getDeviceById(@Path("id") int device_id);


    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @GET("/device/{id}/rm")
    Single<UserResponse> rmDeviceById(@Path("id") int device_id);

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @GET("/device/all_rm")
    Single<UserResponse> rmAllDevice();

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @GET("/device/{id}/settings")
    Single<UserResponse> getSettingsDeviceById();

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @GET("/device/{id}/apps")
    Single<UserResponse> getInstallAppsDeviceById();

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @GET("/device/{id}/battery_status")
    Single<UserResponse> getBatteryStatsDeviceById();

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @GET("/device/{id}/calls")
    Single<UserResponse> getCallsDeviceById();

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @GET("/device/{id}/contacts")
    Single<UserResponse> getContactsDeviceById();

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @GET("/device/{id}/status")
    Single<UserResponse> getStatusDeviceById();

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @GET("/device/{id}/info")
    Single<UserResponse> getInfoDeviceById();

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @GET("/device/{id}/locations")
    Single<UserResponse> getLocationsDeviceById();

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @GET("/device/{id}/messages")
    Single<UserResponse> getMessagesDeviceById();

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @GET("/device/{id}/network")
    Single<UserResponse> getNetworkDeviceById();

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @GET("/device/{id}/service")
    Single<UserResponse> getServiceDeviceById();

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @GET("/device/{id}/battery")
    Single<UserResponse> getBatteryDeviceById();
}
