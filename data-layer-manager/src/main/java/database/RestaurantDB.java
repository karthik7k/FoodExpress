package database;

import entities.RestaurantEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RestaurantDB {
    private List<RestaurantEntity> restaurantEntities;

    public List<RestaurantEntity> getRestaurantEntities() {
        return restaurantEntities;
    }

    public void setRestaurantEntities(List<RestaurantEntity> restaurantEntities) {
        this.restaurantEntities = restaurantEntities;
    }

    public RestaurantDB() {
        this.restaurantEntities = new ArrayList<>();
    }
}
