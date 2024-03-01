package entities;

import java.util.Date;

public class Student  extends User{
    public Student(int id,String email, String passwd, String firstName, String lastName, Date birthDate, int phoneNumber, int cin) {
        super( id,email, passwd, firstName, lastName, birthDate, phoneNumber, cin);
        setRole("student");
    }
    public Student(String email, String passwd, String firstName, String lastName, Date birthDate, int phoneNumber, int cin) {
        super( email, passwd, firstName, lastName, birthDate, phoneNumber, cin);
        setRole("student");
    }
public Student(){

}
}
