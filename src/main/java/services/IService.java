package services;

import javafx.collections.ObservableList;
import models.Formation;

import java.sql.SQLException;
import java.util.List;

public interface IService<T> {
    void ajouter (T t) throws SQLException;
    void modifier (T t) throws SQLException;
    void supprimer (int id ) throws SQLException;
    List recuperer () throws SQLException;
    ObservableList<Formation> getFormationList() throws SQLException ;
}
