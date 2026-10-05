package org.yourcompany.yourproject.compatibility_reservation;

import java.util.List;

public interface ReservationRepository {

    boolean save(int patientId, int unitId);

    boolean cancel(int reservationId);

    ReservationRecord findById(int reservationId);

    List<ReservationRecord> findAll();
}