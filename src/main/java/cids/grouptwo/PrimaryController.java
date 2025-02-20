package cids.grouptwo;

import java.io.IOException;
import javafx.fxml.FXML;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        FXAppBoot.setRoot("secondary");
    }
}
