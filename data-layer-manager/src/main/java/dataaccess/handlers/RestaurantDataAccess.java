package dataaccess.handlers;

import dataaccess.IDataAccess;
import database.RestaurantDB;
import entities.Data;
import entities.RestaurantEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RestaurantDataAccess implements IDataAccess {

    @Autowired
    private RestaurantDB restaurantDB;

    @Override
    public Optional<Data> getData(int id) {
        for(RestaurantEntity restaurantEntity : restaurantDB.getRestaurantEntities()){
            if(restaurantEntity.getId()==id){
                return Optional.of(restaurantEntity);
            }
        }
        return Optional.empty();
    }
}
