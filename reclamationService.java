package services;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.reclamation;
import utils.nadDB;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class reclamationService implements recService<reclamation> {

    private Connection connection;

    public reclamationService() {
        connection = nadDB.getInstance().getConnection();
    }
    ;

    @Override
    public void ajoutereclamtion(reclamation reclamation) throws SQLException {
        String req = "insert into reclamations (fullname, emailrec, pnrec,subject,message) values  ('" + reclamation.getFullname() + "','" + reclamation.getEmailrec() + "','" + reclamation.getPnrec() + "','" + reclamation.getSubject() + "','" + reclamation.getMessage() + "')";
        Statement ste = connection.createStatement();
        ste.executeUpdate(req);
    }

    @Override
    public void supprimereclamtion(int idrec) throws SQLException {
        Statement ste = connection.createStatement();
        String query = "delete from reclamations where idrec = '" + idrec + "'";

        ste.executeUpdate(query);
    }

    @Override
    public void modifiereclamtion(int stater, int idrec) throws SQLException {
        String req = "UPDATE reclamations SET stater='" + stater + "' where idrec = '" + idrec + "'";

        Statement ste = connection.createStatement();
        ste.executeUpdate(req);
        System.out.println("updated");
    }

   @Override
    public List<reclamation> readAll() throws SQLException {
        String req = "select * from reclamations";

        List<reclamation> list1 = new ArrayList<>();
        Statement ste = connection.createStatement();
        ResultSet rs = ste.executeQuery(req);

        while (rs.next()) {
            reclamation r = new reclamation();
            r.setIdrec(rs.getInt("idrec"));
            r.setFullname(rs.getString("fullname"));
            r.setEmailrec(rs.getString("emailrec"));
            r.setIdrec(rs.getInt("pnrec"));
            r.setSubject(rs.getString("subject"));
            r.setMessage(rs.getString("message"));
            r.setStater(rs.getInt("stater"));

            list1.add(r);

        }
        return list1;
    }

    public ObservableList<reclamation> getReclamationList() throws SQLException {

        ObservableList<reclamation> reclamationlist = FXCollections.observableArrayList();

        List <reclamation> rec = new ArrayList<>();
        Statement stm = connection.createStatement();
        String query = "select * from reclamations";
        ResultSet rs;
        rs = stm.executeQuery(query);
        while (rs.next()) {
            reclamation r = new reclamation();
            r.setIdrec(rs.getInt("idrec"));
            r.setFullname(rs.getString("fullname"));
            r.setEmailrec(rs.getString("emailrec"));
            r.setPnrec(rs.getInt("pnrec"));
            r.setSubject(rs.getString("subject"));
            r.setMessage(rs.getString("message"));
            r.setStater(rs.getInt("stater"));

            reclamationlist.add(r);
        }
        return reclamationlist;

    }
}