package daikin.building_climate_control.model;

public class CreateApartmentRequest {
    private String unitNumber;
    private String ownerName;

    public String getUnitNumber() {
        return unitNumber;
    }

    public void setUnitNumber(String unitNumber) {
        this.unitNumber = unitNumber;
    }

    public String getOwnersName() {
        return ownerName;
    }

    public void setOwnersName(String ownerName) {
        this.ownerName = ownerName;
    }
}
