package entities;

import entities.Data;

public class CustomerEntity extends Data {

    private String name;
    private Location location;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public CustomerEntity(int id, String name, Location location) {
        super(id);
        this.name = name;
        this.location = location;

    }

    public CustomerEntity() {
    }
}
