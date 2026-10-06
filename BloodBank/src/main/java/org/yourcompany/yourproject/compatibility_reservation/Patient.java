package org.yourcompany.yourproject.compatibility_reservation;

import org.yourcompany.yourproject.inventory.BloodGroup;

public class Patient {

    private int patientId;
    private String patientName;
    private BloodGroup bloodGroup;
    private String contactNumber;
    private String hospitalName;

    public Patient(int patientId, String patientName,
                   BloodGroup bloodGroup, String contactNumber,
                   String hospitalName) {

        this.patientId = patientId;
        this.patientName = patientName;
        this.bloodGroup = bloodGroup;
        this.contactNumber = contactNumber;
        this.hospitalName = hospitalName;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public BloodGroup getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(BloodGroup bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }
}