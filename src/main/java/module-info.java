module spase.harbour.friends {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jsr310;


    opens spase.harbour.friends to javafx.fxml, javafx.controls;
    opens spase.harbour.friends.model to com.fasterxml.jackson.databind, javafx.base, com.fasterxml.jackson.datatype.jsr310;
    exports spase.harbour.friends;
}