package daikin.building_climate_control.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "buildings")
public class Building {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToMany(mappedBy = "building", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Apartment> apartments = new ArrayList<>();

    @OneToMany(mappedBy = "building", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommonRoom> rooms = new ArrayList<>();

    private float requestedTemperature;

    public float getRequestedTemperature() {
        return requestedTemperature;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Apartment> getApartments() {
        return this.apartments;
    }

    public void setApartments(List<Apartment> apartments) {
        this.apartments = apartments;
    }

    public List<CommonRoom> getRooms() {
        return rooms;
    }

    public void setRooms(List<CommonRoom> rooms) {
        this.rooms = rooms;
    }

    public void setRequestedTemperature(float requestedTemperature) {
        this.requestedTemperature = requestedTemperature;
    }
    public void addApartment(Apartment apartment) {
        apartments.add(apartment);
        apartment.setBuilding(this);
    }

    public void removeApartment(Apartment apartment) {
        apartments.remove(apartment);
        apartment.setBuilding(null);
    }
    public void addRoom(CommonRoom room) {
        rooms.add(room);
        room.setBuilding(this);
    }
    public void removeRoom(CommonRoom room) {
        rooms.remove(room);
        room.setBuilding(null);
    }
}
