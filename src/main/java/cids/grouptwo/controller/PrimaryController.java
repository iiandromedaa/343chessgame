package cids.grouptwo.controller;

import java.io.IOException;

import cids.grouptwo.FXAppBoot;
import javafx.fxml.FXML;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        FXAppBoot.setRoot("secondary");
    }
}
