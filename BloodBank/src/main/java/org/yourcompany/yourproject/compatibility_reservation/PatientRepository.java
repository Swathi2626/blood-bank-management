package org.yourcompany.yourproject.compatibility_reservation;

import java.util.List;

public interface PatientRepository {

    boolean save(Patient patient);

    Patient findById(int patientId);

    List<Patient> findAll();

    boolean update(Patient patient);

    boolean delete(int patientId);
}