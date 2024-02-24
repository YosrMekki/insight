package entities;

import java.util.Date;

public class Admin extends User{

    public Admin( int id,String email, String passwd, String firstName, String lastName, Date birthDate, int phoneNumber, int cin) {
        super( id,email, passwd, firstName, lastName, birthDate, phoneNumber, cin);
        setRole("admin");
    }
    public Admin( String email, String passwd, String firstName, String lastName, Date birthDate, int phoneNumber, int cin) {
        super( email, passwd, firstName, lastName, birthDate, phoneNumber, cin);
        setRole("admin");
    }

    @Override
    public String toString() {
        return "Admin{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", passwd='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDate=" + birthDate +
                ", phoneNumber=" + phoneNumber +
                ", cin=" + cin +
                ", role='" + role + '\'' +
                "} " + super.toString();
    }
}
