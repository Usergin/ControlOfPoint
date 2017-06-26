package utils;

import data.model.Device;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import javafx.collections.ObservableList;

/**
 * Created by OldMan on 24.06.2017.
 */
public class RxBus {
    private static RxBus instance;

    private PublishSubject<String> subjectInfoDevice = PublishSubject.create();
    private BehaviorSubject<String> subjectSettingsDevice = BehaviorSubject.create();

    private BehaviorSubject<Device> subjectShortInfoDevice = BehaviorSubject.create();
    private BehaviorSubject<ObservableList<Device>> subjectDeviceList = BehaviorSubject.create();

    public static RxBus instanceOf() {
        if (instance == null) {
            instance = new RxBus();
        }
        return instance;
    }

    /**
     * Pass any event down to event listeners.
     */
    public void setShortDeviceInfo(Device object) {subjectShortInfoDevice.onNext(object);}
    public Observable<Device> getShortDeviceInfo() {return subjectShortInfoDevice;}
    public void setDeviceList(ObservableList<Device> object) {
        subjectDeviceList.onNext(object);
    }
    public Observable<ObservableList<Device>> getDeviceList() {
        return subjectDeviceList;
    }
    public void setDeviceInfo(String device_id) {
        subjectInfoDevice.onNext(device_id);
    }
    public Observable<String> getDeviceInfo() {
        return subjectInfoDevice;
    }
    public BehaviorSubject<String> getSubjectSettingsDevice() {return subjectSettingsDevice;}
    public void setSubjectSettingsDevice(BehaviorSubject<String> subjectSettingsDevice) {this.subjectSettingsDevice = subjectSettingsDevice;}
}
