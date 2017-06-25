package data.remote;

import data.remote.model.request.Authentication;
import data.remote.model.request.SettingsRequest;
import data.remote.model.response.*;
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
    @POST("/setting")
    Single<StatusResponse> setDeviceSettings(@Body SettingsRequest settingsRequest);

    @Headers({"Accept: application/json"})
    @GET("/devices")
    Single<DeviceListResponse> getDevices();


    @Headers({"Accept: application/json"})
    @GET("/device/{id}")
    Single<DeviceResponse> getDeviceById(@Path("id") int device_id);


    @Headers({"Accept: application/json"})
    @GET("/device/{id}/rm")
    Single<StatusResponse> rmDeviceById(@Path("id") int device_id);

    @Headers({"Accept: application/json"})
    @GET("/all_rm")
    Single<StatusResponse> rmAllDevice();

    @Headers({"Accept: application/json"})
    @GET("/device/{id}/settings")
    Single<SettingsResponse> getDeviceSettings(@Path("id") int device_id);

    @Headers({"Accept: application/json"})
    @GET("/device/{id}/apps")
    Single<InstallAppResponse> getDeviceInstallApps(@Path("id") int device_id);

    @Headers({"Accept: application/json"})
    @GET("/device/{id}/battery")
    Single<BatteryEventResponse> getDeviceBatteryEvent(@Path("id") int device_id);

    @Headers({"Accept: application/json"})
    @GET("/device/{id}/calls")
    Single<CallResponse> getDeviceCalls(@Path("id") int device_id);

    @Headers({"Accept: application/json"})
    @GET("/device/{id}/contacts")
    Single<ContactResponse> getDeviceContacts(@Path("id") int device_id);

    @Headers({"Accept: application/json"})
    @GET("/device/{id}/status")
    Single<DeviceEventResponse> getDeviceStatus(@Path("id") int device_id);

    @Headers({"Accept: application/json"})
    @GET("/device/{id}/info")
    Single<DeviceInfoResponse> getDeviceInfoById(@Path("id") int device_id);

    @Headers({"Accept: application/json"})
    @GET("/device/{id}/locations")
    Single<LocationResponse> getDeviceLocations(@Path("id") int device_id);

    @Headers({"Accept: application/json"})
    @GET("/device/{id}/messages")
    Single<MessageResponse> getDeviceMessages(@Path("id") int device_id);

    @Headers({"Accept: application/json"})
    @GET("/device/{id}/network")
    Single<NetworkEventResponse> getDeviceNetworkEvents(@Path("id") int device_id);

    @Headers({"Accept: application/json"})
    @GET("/device/{id}/service")
    Single<ServiceEventResponse> getDeviceServiceEvents(@Path("id") int device_id);
}
