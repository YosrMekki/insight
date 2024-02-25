package models;

import javafx.collections.ObservableList;

public class reclamation {

    private int idrec;
    private String fullname;
    private String emailrec;
    private int pnrec;
    private String subject;
    private String message;

    private int stater = 0;

    public reclamation() {

    }

    public reclamation(int idrec, String fullname, String emailrec,  int pnrec,String subject, String message, int stater) {
        this.idrec = idrec;
        this.fullname = fullname;
        this.emailrec = emailrec;
        this.pnrec = pnrec;
        this.subject = subject;
        this.message = message;
        this.stater = stater;
    }

    public reclamation(String fullname,String emailrec, int pnrec,  String subject, String message, int stater) {
        this.fullname = fullname;
        this.emailrec = emailrec;
        this.pnrec = pnrec;
        this.subject = subject;
        this.message = message;
        this.stater = stater;
    }

    public reclamation(String fullname, String emailrec,int pnrec, String subject, String message) {
        this.fullname = fullname;
        this.emailrec = emailrec;
        this.pnrec = pnrec;
        this.subject = subject;
        this.message = message;
    }

    public reclamation(int idrec, int stater) {
    }

    public static void add(ObservableList<reclamation> reclas) {
    }

    public int getIdrec() {
        return idrec;
    }

    public void setIdrec(int idrec) {
        this.idrec = idrec;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public int getPnrec() {
        return pnrec;
    }

    public void setPnrec(int pnrec) {
        this.pnrec = pnrec;
    }

    public String getEmailrec() {
        return emailrec;
    }

    public void setEmailrec(String emailrec) {
        this.emailrec = emailrec;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStater() {
        return stater;
    }

    public void setStater(int stater) {
        this.stater = stater;
    }

    @Override
    public String toString() {
        return "reclamation : {" +
                "id=" + idrec +
                ", fullname='" + fullname + '\'' +
                ", phone number=" + pnrec +
                ", email='" + emailrec + '\'' +
                ", subject='" + subject + '\'' +
                ", message='" + message + '\'' +
                ", state=" + stater +
                '}';
    }
}
