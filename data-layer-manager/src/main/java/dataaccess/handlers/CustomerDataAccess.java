package dataaccess.handlers;

import dataaccess.IDataAccess;
import database.CustomerDB;
import entities.CustomerEntity;
import entities.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomerDataAccess implements IDataAccess {

    @Autowired
    private CustomerDB customerDB;

    @Override
    public Optional<Data> getData(int id) {
        for(CustomerEntity customerEntity : customerDB.getCustomerEntities()){
            if(customerEntity.getId() ==id ){
                return Optional.of(customerEntity);
            }
        }
        return Optional.empty();
    }
}
