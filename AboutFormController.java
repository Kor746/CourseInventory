/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dan;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Daniel
 */
public class AboutFormController implements Initializable {

    //instance variable
    private Stage stage;
    @FXML
    private Button buttonClose;

    /**
     * Initializes the controller class.
     */
    public void setStage(Stage stage)
    {
        this.stage = stage;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        buttonClose.setOnAction( e -> stage.close());
        buttonClose.setOnMouseEntered( e -> 
                buttonClose.setStyle("-fx-background-color:#000"));
        buttonClose.setOnMouseExited( e -> 
                buttonClose.setStyle("-fx-background-color:#1e90ff"));
                
    }    
    
    
    
}
