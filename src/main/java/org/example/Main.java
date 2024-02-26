package org.example;

import models.Ecole;
import models.Personne;
import models.Professeur;
import services.EcoleService;
import services.PersonneService;
import services.ProfService;
import utils.Mydatabase;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {

        //Mydatabase d=new Mydatabase();
        PersonneService ps=new PersonneService();
        EcoleService es=new EcoleService();
        ProfService profS=new ProfService();
       /*try {
            ps.ajouter(new Personne(24,"khalil","ayari"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try {
            ps.modifier(new Personne(1,22,"ayari","khalil"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try {
            es.ajouter(new Ecole(24,"
            ","el alia"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try {
            es.modifier(new Ecole(1,"espritt","bizerte"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try {
            Ecole e=new Ecole(1,"espritt","bizerte",2);
            profS.ajouter(new Professeur("hamza","ayarii",51300900,"koln",e));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try {

            System.out.println(profS.recuperer());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }*/
        try {
            System.out.println(profS.recupererProfParEcoleId(2));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}