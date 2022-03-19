package dataaccess;

import entities.Data;

import java.util.Optional;

public interface IDataAccess {

    public Optional<Data> getData(int id);
}
