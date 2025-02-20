module cids.grouptwo {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;

    opens cids.grouptwo.controller to javafx.fxml;
    opens cids.grouptwo to javafx.fxml;
    exports cids.grouptwo;
}
