package data.model.information;

/**
 * Created by OldMan on 25.06.2017.
 */
public class ServiceEvent extends BaseInfo {
    private String area;
    private String event;

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }
}