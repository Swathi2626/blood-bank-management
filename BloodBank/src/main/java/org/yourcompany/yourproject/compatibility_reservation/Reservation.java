package org.yourcompany.yourproject.compatibility_reservation;

import org.yourcompany.yourproject.inventory.BloodComponent;
import org.yourcompany.yourproject.inventory.BloodGroup;
import org.yourcompany.yourproject.inventory.BloodStatus;
import org.yourcompany.yourproject.inventory.BloodUnit;
import org.yourcompany.yourproject.inventory.InventoryManager;

import java.util.List;

public class Reservation {

    private CompatibilityChecker checker;
    private ReservationRepository reservationRepository;

    public Reservation() {
        checker = new CompatibilityChecker();
        reservationRepository = new MySQLReservationRepository();
    }

    // Reserve a blood unit in memory
    public boolean reserve(BloodGroup recipient, BloodUnit unit) {

        if (checker.isCompatible(recipient, unit)) {
            unit.setStatus(BloodStatus.RESERVED);
            return true;
        }

        return false;
    }

    // Find a compatible unit
    public BloodUnit reserveAvailableUnit(
            BloodGroup recipient,
            BloodComponent component) {

        InventoryManager inventoryManager = new InventoryManager();
        List<BloodUnit> list = inventoryManager.getAllBloodUnits();

        for (BloodUnit unit : list) {

            if (unit.getComponent() == component &&
                reserve(recipient, unit)) {

                return unit;
            }
        }

        return null;
    }

    // Save reservation in database
    public boolean saveReservation(int patientId, BloodUnit unit) {

        if (unit == null) {
            return false;
        }

        return reservationRepository.save(
                patientId,
                unit.getUnitId()
        );
    }

    // Cancel reservation
    public boolean cancelReservation(int reservationId) {

        return reservationRepository.cancel(reservationId);
    }

    // Get reservation
    public ReservationRecord getReservation(int reservationId) {

        return reservationRepository.findById(reservationId);
    }
}