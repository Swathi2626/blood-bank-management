package org.yourcompany.yourproject.compatibility_reservation;

import org.yourcompany.yourproject.inventory.BloodStatus;
import org.yourcompany.yourproject.inventory.BloodUnit;

import java.time.LocalDate;
import java.util.List;

import org.yourcompany.yourproject.inventory.BloodComponent;
import org.yourcompany.yourproject.inventory.BloodGroup;
import org.yourcompany.yourproject.inventory.InventoryManager;

public class Reservation {
    CompatibilityChecker checker = new CompatibilityChecker();
    public boolean reserve(BloodGroup recipient,BloodUnit unit){
        if (checker.isCompatible(recipient, unit)){
            unit.setStatus(BloodStatus.RESERVED);
            return true;
        }
        return false;
    }

    public BloodUnit reserveAvailableUnit(BloodGroup recipient, BloodComponent component){
        InventoryManager inventoryManager = new InventoryManager();
        List<BloodUnit> list = inventoryManager.getAllBloodUnits();

        for (int i=0;i<list.size();i++){
            if (list.get(i).getComponent()==component && reserve(recipient, list.get(i))){
                return list.get(i);
            }
        }

        return null;
    }

    public boolean cancelReservation(BloodUnit unit){
        if (unit.getStatus()==BloodStatus.RESERVED){
            unit.setStatus(BloodStatus.AVAILABLE);
            return true;
        }
        return false;
    }
}