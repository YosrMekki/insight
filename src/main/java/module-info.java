module easyquiz {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens easyquiz to javafx.fxml;
    exports easyquiz;
}