package services;
import javafx.collections.ObservableList;
import models.reclamation;

import java.sql.SQLException;
import java.util.List;

public interface recService <R>
{
    void ajoutereclamtion ( R r ) throws SQLException;

    void supprimereclamtion ( int idrec ) throws SQLException;

    void modifiereclamtion(int stater, int idrec) throws SQLException;

    List <reclamation> readAll() throws SQLException;
    ObservableList<reclamation> getReclamationList() throws SQLException ;

    }