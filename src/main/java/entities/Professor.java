package entities;

import java.util.Date;

public class Professor extends User{

    public Professor(int id, String email, String passwd, String firstName, String lastName, Date birthDate, int phoneNumber, int cin) {
        super(id, email, passwd, firstName, lastName, birthDate, phoneNumber, cin);
        setRole("professor");
    }

    public Professor(String email, String passwd, String firstName, String lastName, Date birthDate, int phoneNumber, int cin) {
        super(email, passwd, firstName, lastName, birthDate, phoneNumber, cin);
        setRole("professor");
    }
    public Professor(){}
}
