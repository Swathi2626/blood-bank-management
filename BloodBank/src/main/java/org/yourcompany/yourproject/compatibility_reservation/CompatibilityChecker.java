package org.yourcompany.yourproject.compatibility_reservation;

import org.yourcompany.yourproject.inventory.BloodGroup;
import org.yourcompany.yourproject.inventory.BloodStatus;
import org.yourcompany.yourproject.inventory.BloodUnit;

public class CompatibilityChecker {
    public boolean isWholeBloodCompatible(BloodGroup recipient, BloodGroup donor){
        return recipient==donor;
    }

    public boolean isPRBCCompatible(BloodGroup recipient, BloodGroup donor){
        switch (recipient){
            case A_POSITIVE:
                return donor==BloodGroup.A_POSITIVE||donor==BloodGroup.A_NEGATIVE||donor==BloodGroup.O_POSITIVE||donor==BloodGroup.O_NEGATIVE;
            case A_NEGATIVE:
                return donor==BloodGroup.A_NEGATIVE||donor==BloodGroup.O_NEGATIVE;
            case O_POSITIVE:
                return donor==BloodGroup.O_POSITIVE||donor==BloodGroup.O_NEGATIVE;
            case O_NEGATIVE:
                return donor==BloodGroup.O_NEGATIVE;
            case B_POSITIVE:
                return donor==BloodGroup.B_POSITIVE||donor==BloodGroup.B_NEGATIVE||donor==BloodGroup.O_POSITIVE||donor==BloodGroup.O_NEGATIVE;
            case B_NEGATIVE:
                return donor==BloodGroup.B_NEGATIVE||donor==BloodGroup.O_NEGATIVE;
            case AB_POSITIVE:
                return donor==BloodGroup.A_POSITIVE||donor==BloodGroup.A_NEGATIVE||donor==BloodGroup.O_POSITIVE||donor==BloodGroup.O_NEGATIVE||donor==BloodGroup.AB_NEGATIVE||donor==BloodGroup.AB_POSITIVE||donor==BloodGroup.B_POSITIVE||donor==BloodGroup.B_NEGATIVE;
            case AB_NEGATIVE:
                return donor==BloodGroup.AB_NEGATIVE||donor==BloodGroup.A_NEGATIVE||donor==BloodGroup.B_NEGATIVE||donor==BloodGroup.O_NEGATIVE;
            default:
                return false;
        }
    }

    public boolean isFPPCompatible(BloodGroup recipient, BloodGroup donor){
        switch(recipient){
            case A_POSITIVE:
            case A_NEGATIVE:
                return donor== BloodGroup.A_POSITIVE || donor== BloodGroup.A_NEGATIVE ||donor== BloodGroup.AB_POSITIVE || donor== BloodGroup.AB_NEGATIVE;
            case B_POSITIVE:
            case B_NEGATIVE:
                return donor== BloodGroup.B_POSITIVE || donor== BloodGroup.B_NEGATIVE ||donor== BloodGroup.AB_POSITIVE || donor== BloodGroup.AB_NEGATIVE;
            case AB_POSITIVE:
            case AB_NEGATIVE:
                return donor== BloodGroup.AB_POSITIVE || donor== BloodGroup.AB_NEGATIVE;
            case O_POSITIVE:
            case O_NEGATIVE:
                return true;
            default:
                return false;
        }
    }

    public boolean isPlateletsCompatible(BloodGroup recipient, BloodGroup donor){
        return recipient == donor;
    }

    public boolean isCompatible(BloodGroup recipient, BloodUnit unit){
        if (unit.getStatus()!=BloodStatus.AVAILABLE){
            return false;
        }

        switch(unit.getComponent()){
            case WHOLE_BLOOD:
                return isWholeBloodCompatible(recipient, unit.getBloodGroup());
            case PRBC:
                return isPRBCCompatible(recipient, unit.getBloodGroup());
            case FPP:
                return isFPPCompatible(recipient, unit.getBloodGroup());
            case PLATELETS:
                return isPlateletsCompatible(recipient, unit.getBloodGroup());
            default:
                return false;
        }
    }
}