/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dan;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Daniel
 */
public class SearchFormController implements Initializable {

    
    @FXML
    private ListView<String> listview;
    @FXML
    private TextField textfield;
    @FXML
    private RadioButton idButton;
    
    private Stage stage;
    private InventoryModel model;
    private boolean option;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        model = new InventoryModel();
        stage = null;
        option = false;
        idButton.setSelected(true);
        
    }    
    
    public void setStage(Stage stage)
    {
        this.stage = stage;
    }
    
    public void setModel(InventoryModel model)
    {
        this.model = model;
    }

    @FXML
    private void handleButtonSearch(ActionEvent event)  {
        
        ObservableList<String> items = listview.getItems();
        items.clear(); //clear prev
        
        String key = textfield.getText();
        if(key == null || key.length()  == 0)
        {
            ArrayList<Course> courses = model.findCoursesById(key);
            
            for(int i = 0; i < courses.size(); ++i)
            {
                Course c = courses.get(i);
                
                String item = c.getId() + ": " + c.getTitle();
                //System.out.print(item);
                items.add(item);
            }
        }
            
        //search by keyword and add courses to the listview
        if(option == false)
        {
           
            ArrayList<Course> courses = model.findCoursesById(key);
            
            for(int i = 0; i < courses.size(); ++i)
            {
                Course c = courses.get(i);
                
                String item = c.getId() + ": " + c.getTitle();
                //System.out.print(item);
                items.add(item);
            }
        }
        else
        {
            ArrayList<Course> courses = model.findCoursesByTitle(key);
            for(int i = 0; i < courses.size(); ++i)
            {
                Course course = courses.get(i);
                String item = course.getId() + ": " + course.getTitle();
                items.add(item);
            }
        }
       
    }

    @FXML
    private void handleButtonSelect(ActionEvent event) {
        //extract ID only from listview
        
        String s = listview.getSelectionModel().getSelectedItem();
        s = s.substring(0, s.indexOf(":")); // select the ID on main listview
        
        model.setSelectedCourseId(s);
        if(stage != null)
            stage.close();
        
    }

    @FXML
    private void handleButtonCancel(ActionEvent event) {
        
        if(stage != null)
            stage.close();
          
    }

    @FXML
    private void handleIdAction(ActionEvent event) {
        
        option = false;
    }

    @FXML
    private void handleTitleAction(ActionEvent event) {
        option = true;
    }
    
    
    
}
