package tests;

import entities.Projet;
import entities.Sujet;
import services.ProjetService;
import services.SujetService;
import utils.MyDataBase;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {

        // MyDataBase d = MyDataBase.getInstance();

        ProjetService ps = new ProjetService();
        try {
            ps.ajouter(new Projet("innovation", "projet innovation ", "ACTIA"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        try {
            ps.modifier(new Projet(1, "nouveauNomProjet", "nouvelleDescription", "nouvelleEntreprise"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        try {
            System.out.println(ps.recuperer());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        try {
            ps.supprimer(1);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        SujetService ss = new SujetService();
        try {
            ss.ajouter(new Sujet("robotique"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        try {
            ss.modifier(new Sujet(1, "intelligence artificielle"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        try {
            ss.supprimer(1);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        try {
            System.out.println(ss.recuperer());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
