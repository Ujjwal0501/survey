package com.svnit.civil.survey.models;

public class TransportDetails {

    private String stop_distance;
    private String time_to_stoppage;
    private String average_wait_time;
    private String usage_frequency;
    private String service_reliability;
    private String safety;
    private String fare;
    private String cleanliness_and_comfort;

    public void TransportDetails() {
        this.setStop_distance("");
        this.setTime_to_stoppage("");
        this.setAverage_wait_time("");
        this.setUsage_frequency("");
        this.setService_reliability("");
        this.setSafety("");
        this.setFare("");
        this.setCleanliness_and_comfort("");
    }

    public void TransportDetails(String stop_distance, String time_to_stoppage, String average_wait_time, String usage_frequency, String
                                 service_reliability, String safety, String fare, String cleanliness_and_comfort) {
        this.setStop_distance(stop_distance);
        this.setTime_to_stoppage(time_to_stoppage);
        this.setAverage_wait_time(average_wait_time);
        this.setUsage_frequency(usage_frequency);
        this.setService_reliability(service_reliability);
        this.setSafety(safety);
        this.setFare(fare);
        this.setCleanliness_and_comfort(cleanliness_and_comfort);
    }

    public String getStop_distance() {
        return stop_distance;
    }

    public void setStop_distance(String stop_distance) {
        this.stop_distance = stop_distance;
    }

    public String getTime_to_stoppage() {
        return time_to_stoppage;
    }

    public void setTime_to_stoppage(String time_to_stoppage) {
        this.time_to_stoppage = time_to_stoppage;
    }

    public String getAverage_wait_time() {
        return average_wait_time;
    }

    public void setAverage_wait_time(String average_wait_time) {
        this.average_wait_time = average_wait_time;
    }

    public String getUsage_frequency() {
        return usage_frequency;
    }

    public void setUsage_frequency(String usage_frequency) {
        this.usage_frequency = usage_frequency;
    }

    public String getService_reliability() {
        return service_reliability;
    }

    public void setService_reliability(String service_reliability) {
        this.service_reliability = service_reliability;
    }

    public String getSafety() {
        return safety;
    }

    public void setSafety(String safety) {
        this.safety = safety;
    }

    public String getFare() {
        return fare;
    }

    public void setFare(String fare) {
        this.fare = fare;
    }

    public String getCleanliness_and_comfort() {
        return cleanliness_and_comfort;
    }

    public void setCleanliness_and_comfort(String cleanliness_and_comfort) {
        this.cleanliness_and_comfort = cleanliness_and_comfort;
    }
}
