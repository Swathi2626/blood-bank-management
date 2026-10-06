package org.yourcompany.yourproject.compatibility_reservation;

import java.time.LocalDate;

public class ReservationRecord {

    private int reservationId;
    private int patientId;
    private int unitId;
    private LocalDate reservationDate;
    private String status;

    public ReservationRecord(int reservationId, int patientId,
                             int unitId, LocalDate reservationDate,
                             String status) {

        this.reservationId = reservationId;
        this.patientId = patientId;
        this.unitId = unitId;
        this.reservationDate = reservationDate;
        this.status = status;
    }

    public int getReservationId() {
        return reservationId;
    }

    public int getPatientId() {
        return patientId;
    }

    public int getUnitId() {
        return unitId;
    }

    public LocalDate getReservationDate() {
        return reservationDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}