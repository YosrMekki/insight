package org.example;
import models.Formation;
import services.FormationService;

import utils.Mydatabase;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {

FormationService formationService=new FormationService();
        try {
            System.out.println(formationService.recuperer());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }}

