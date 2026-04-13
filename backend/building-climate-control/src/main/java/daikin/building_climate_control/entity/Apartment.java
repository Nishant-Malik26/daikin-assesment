package daikin.building_climate_control.entity;

import jakarta.persistence.*;
@Entity
@Table(name = "apartments")
public class Apartment extends Room {
    private String ownersName;
    private String unitNumber;

    @ManyToOne
    @JoinColumn(name= "building_id")
    private Building building;

    public String getOwnersName() {
        return ownersName;
    }
    public void setOwnersName(String ownersName){
        this.ownersName = ownersName;
    }
    public String getUnitNumber() {
        return unitNumber;
    }
    public void setUnitNumber(String unitNumber) {
        this.unitNumber = unitNumber;
    }
    public Building getBuilding() {
        return building;
    }
    public void setBuilding(Building building) {
        this.building = building;
    }
}
