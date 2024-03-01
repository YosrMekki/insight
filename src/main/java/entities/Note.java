package entities;

public class Note {
    private int id ;
    private String noteContent;
    private int studentId;

    public Note(int id, String noteContent, int studentId) {
        this.id = id;
        this.noteContent = noteContent;
        this.studentId = studentId;
    }
    public Note(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", noteContent='" + noteContent + '\'' +
                ", studentId=" + studentId +
                '}';
    }
}
