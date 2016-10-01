/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dan;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Daniel
 */
public class AddFormController implements Initializable {

    
    @FXML
    private TextField idfield;
    @FXML
    private TextField titlefield;
    @FXML
    private TextField creditfield;
    @FXML
    private ComboBox<String> comboCat;
    
    private File file;
    private InventoryModel model;
    private Stage stage;
    private Course course;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        model = new InventoryModel();
        setModel(model);
        
    }    
    
    private void populateCategories()
    {
        ArrayList<String> list = model.getCategories();
        comboCat.setItems(FXCollections.observableArrayList(list));
        
    }
    //invokes populateCategories
    public void setModel(InventoryModel model)
    {
        this.model = model;
        populateCategories();
    }
    
    public void setStage(Stage stage)
    {
        this.stage = stage;
    }

    @FXML
    private void handleButtonAdd(ActionEvent event) {
        Alert alert = new Alert(AlertType.ERROR);
        //validate ID
        
        String id = idfield.getText().toUpperCase();
        if(model.validateId(id) == false)
        {
            alert.setContentText("ID must be 4 alphabets followed by 5 digit");
            alert.show(); return;
        }
        //validate title
        String title = titlefield.getText();
        if(title == null || title.length() == 0)
        {
            alert.setContentText("Title cannot be empty");
            alert.show(); return;
        }
        //validate credit
        int credit;
        try {
          credit = Integer.parseInt(creditfield.getText());
            
        } 
        catch(NumberFormatException e) {
            alert.setContentText("Credit must be numeric.");
            alert.show(); return;
        }
        if(credit <= 0)
        {
            alert.setContentText("Credit must be greater than 0.");
            alert.show(); return;
        }
        //validate category
        String cat = comboCat.getValue();
        if(cat == null) // null
        {
            alert.setContentText("Choose a category.");
            alert.show(); return;
        }
        /*  Testing values
        System.out.println(id);
        System.out.println(title);
        System.out.println(credit);
        System.out.println(cat);
        */
        
        // add course and close this window
        course = new Course(id,title,credit,cat);
        model.addCourse(course);
        
        //stage.close();
        if(stage!=null)
            stage.close();
           
    }

    @FXML
    private void handleButtonCancel(ActionEvent event) {
        
        
        stage.close();
        
     
    }
    
}
