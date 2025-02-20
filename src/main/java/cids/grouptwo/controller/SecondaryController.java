package cids.grouptwo.controller;

import java.io.IOException;

import cids.grouptwo.FXAppBoot;
import javafx.fxml.FXML;

public class SecondaryController {

    @FXML
    private void switchToPrimary() throws IOException {
        FXAppBoot.setRoot("primary");
    }
}