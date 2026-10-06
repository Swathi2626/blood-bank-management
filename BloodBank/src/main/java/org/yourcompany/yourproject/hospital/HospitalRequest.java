package org.yourcompany.yourproject.hospital;

public class HospitalRequest {

    private int requestId;
    private String hospitalName;
    private String bloodGroup;
    private String component;
    private int unitsRequired;
    private String urgency;
    private String status;

    public HospitalRequest(
            int requestId,
            String hospitalName,
            String bloodGroup,
            String component,
            int unitsRequired,
            String urgency) {

        this.requestId = requestId;
        this.hospitalName = hospitalName;
        this.bloodGroup = bloodGroup;
        this.component = component;
        this.unitsRequired = unitsRequired;
        this.urgency = urgency;
        this.status = "PENDING";
    }

    public int getRequestId() {
        return requestId;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public String getComponent() {
        return component;
    }

    public int getUnitsRequired() {
        return unitsRequired;
    }

    public String getUrgency() {
        return urgency;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}