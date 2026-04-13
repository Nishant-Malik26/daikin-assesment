package daikin.building_climate_control.entity;

import daikin.building_climate_control.util.RoomType;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "common_rooms")
public class CommonRoom extends Room {
    @Enumerated(EnumType.STRING)
    private RoomType roomType;

    @ManyToOne
    @JoinColumn(name= "building_id")
    private Building building;

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }
    public Building getBuilding() {
        return building;
    }
    public void setBuilding(Building building) {
        this.building = building;
    }

}
