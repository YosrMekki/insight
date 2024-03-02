package services;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import models.Contrat;
import models.Ecole;
import models.Professeur;
import utils.Mydatabase;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContratService implements IService<Contrat> {

    private Connection connexion;
    private Contrat contrat;
    private EcoleService ecoleService=new EcoleService();

    public ContratService() {
        this.connexion = Mydatabase.getInstance().getConnexion();
    }

    @Override
    public void ajouter(Contrat contrat) throws SQLException {
        String req = "insert into contrat (ecole_id,nb_days) "
                + "values(" + contrat.getEcole().getId() + ","
                + contrat.getNb_days() + ")";

        Statement statement = connexion.createStatement();
        statement.executeUpdate(req);
    }

    @Override
    public void modifier(Contrat contrat) throws SQLException {

        String sql = "update contrat set nb_days = ?";
        PreparedStatement preparedStatement = connexion.prepareStatement(sql);
        preparedStatement.setInt(1, contrat.getNb_days());
        preparedStatement.executeUpdate();
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String req = "DELETE FROM contrat WHERE id=?";
        PreparedStatement preparedStatement = connexion.prepareStatement(req);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }
    @Override
    public List<Contrat> recuperer() throws SQLException {
        EcoleService es=new EcoleService();
        String sql="select * from contrat";
        List<Contrat> contrats = new ArrayList<>();
        try (PreparedStatement statement = connexion.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Contrat contrat = new Contrat();
                contrat.setId(rs.getInt("id"));
                contrat.setEcole_id(rs.getInt("ecole_id"));
                contrat.setDate_contrat(rs.getDate("date_contrat"));
                contrat.setNb_days(rs.getInt("nb_days"));



                contrats.add(contrat);
            }
        }
        return contrats;
    }

    public void printContrat(Contrat contrat){
        File folder = new File("documents");
        if (!folder.exists()) {
            if (!folder.mkdirs()) {
                // Gérer l'erreur si la création du répertoire échoue
                System.err.println("Impossible de créer le répertoire 'documents'");
            }
        }
        String nom_fichier = "documents/Informations_de" +contrat.getId()+".pdf";
        System.out.println(nom_fichier);
        Font TitleFont = new Font(Font.FontFamily.TIMES_ROMAN,16,Font.BOLD, BaseColor.BLUE);
        Font RedFont = new Font(Font.FontFamily.TIMES_ROMAN,14,Font.BOLD, BaseColor.RED);
        File imageEcole=new File("C:\\Users\\GIGABYTE I5\\Desktop\\partenaire\\images\\img2.jpg");
        String chemin = imageEcole.getAbsolutePath();
        LineSeparator ls=new LineSeparator();
        ls.setLineColor(BaseColor.BLUE);
        Image image,image2 = null;
        Document document= new Document();
        try {
            Statement stm=connexion.createStatement();

            int id=contrat.getId(); int duree=contrat.getNb_days(); int ecole_id= contrat.getEcole_id();
            Ecole ecole = ecoleService.getEcoleById(ecole_id);
            String nomEcole=ecole.getNomEcole();
            nomEcole=nomEcole.toUpperCase();
            PdfWriter.getInstance(document,new FileOutputStream(nom_fichier));
            document.open();
            document.addTitle("information sur contrat de l'ecole "+ecole.getNomEcole());
            document.addAuthor("AU-INFO");

            Paragraph preface = new Paragraph();
            Paragraph titre = new Paragraph("INFORMATION SUR LE CONTRAT DE L ECOLE "+nomEcole , TitleFont);

            titre.setAlignment(Element.ALIGN_CENTER);
            preface.add(titre);
            image = Image.getInstance("C:\\Users\\GIGABYTE I5\\Desktop\\partenaire\\src\\main\\java\\images/maxresdefault.jpg");

            image.scaleAbsolute(120,120);
            document.add(image);
            preface.add(ls);

            preface.add(new Paragraph(" "));
            preface.add(new Paragraph(" "));
            Chunk c_nom= new Chunk(nomEcole,RedFont);
            Chunk c_ecole= new Chunk(String.valueOf(ecole_id),RedFont);

            Chunk c_adresse= new Chunk(ecole.getAdresse(),RedFont);
            Chunk c_duree= new Chunk(String.valueOf(duree),RedFont);

            preface.add(new Paragraph(" "));
            preface.add(new Paragraph(" "));

            preface.add("MATRICULE DE L'ECOLE EST : ");preface.add(c_ecole);preface.add(new Paragraph(" "));
            preface.add("NOM DE L'ECOLE EST : ");preface.add(c_nom);preface.add(new Paragraph(" "));
            preface.add("SITUE A  : ");preface.add(c_adresse);preface.add(new Paragraph(" "));
            preface.add("LA DUREE DU CONTRAT EST  ");preface.add(c_duree);preface.add(new Paragraph(" "));
            preface.add(new Paragraph(" "));
            preface.add(new Paragraph(" "));


            document.add(preface);
            document.close();
            int valid = JOptionPane.showOptionDialog(
                    null,
                    new Object[]{
                            "Voulez vous directement ouvrir le fichier?",
                            "__________________________________________",
                            "Cliquer sur \"oui\" pour ouvrir our sur \"non\" pour annuler",

                    },
                    "Ouverture du fichier "+nom_fichier,
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE,
                    null,
                    null,
                    "OK"
            );
            if (valid == JOptionPane.OK_OPTION){
                File ouvrir = new File(nom_fichier);
                Desktop desk= Desktop.getDesktop();
                desk.open(ouvrir);
            }







        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    public void printContratt(Contrat contrat) {
        Document document = new Document();

        try {
            // Spécifier le chemin complet du fichier PDF sur le bureau
            String desktopPath = "C:\\Users\\GIGABYTE I5\\Desktop\\";
            String pdfFilePath = desktopPath + "contrat"+contrat.getId()+".pdf";

            // Créer le fichier de sortie
            FileOutputStream outputStream = new FileOutputStream(pdfFilePath);

            PdfWriter.getInstance(document, outputStream);
            document.open();

            // Ajout de l'image

           // Image image = Image.getInstance("images/img2.jpg");
            //document.add(image);

            // Ajout des informations du contrat
            document.add(new Paragraph("Durée du contrat: " + contrat.getNb_days() + " jours"));

            // Récupération des informations de l'école
            Ecole ecole = ecoleService.getEcoleById(contrat.getEcole_id());
            if (ecole != null) {
                System.out.println("hello");
                document.add(new Paragraph("Nom de l'école: " + ecole.getNom()));
                document.add(new Paragraph("Adresse de l'école: " + ecole.getAdresse()));
            }

        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
    }

}