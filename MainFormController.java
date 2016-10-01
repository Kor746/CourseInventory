/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dan;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Daniel
 */
public class MainFormController implements Initializable {
    
    
  
    @FXML
    private MenuItem editItem;
    @FXML
    private MenuItem addItem;
    @FXML
    private MenuItem deleteItem;
    @FXML
    private MenuItem searchItem;
    @FXML
    private MenuItem helpItem;
    @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Button saveButton;
    @FXML
    private Label changeLabel;
    @FXML
    private TextField titleField;
    @FXML
    private TextField creditField;
    @FXML
    private ComboBox<String> categoryMenu;
    @FXML
    private ListView<String> listView;
    @FXML
    private ComboBox<String> comboCat;
    @FXML
    private Button searchButton;
    @FXML
    private Button addButton;
    
    private Course course;
    private InventoryModel model;
    private Stage stage;
    private File file; //currently working file
    private boolean flag;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        flag = false;
        course = new Course();
        //create model component
        model = new InventoryModel();
        
        
        listView.getSelectionModel()
                .selectedItemProperty()
                .addListener((ov,oldValue,newValue) ->
        {
            if(newValue != null)
            {
                System.out.println(newValue);
                showCourseInfo(newValue);
            }
            else
            {
                
                clearCourseInfo();
            }
        });
        
       
        
        //detects changes in title,credit, and cat combo box
        titleField.textProperty().addListener( (ov,oldValue,newValue) -> flag = true);
        creditField.textProperty().addListener( (ov,oldValue,newValue) ->  flag = true);
        categoryMenu.getSelectionModel().selectedItemProperty().addListener( (ov,oldValue,newValue) ->
            flag = true);
       
        
        comboCat.getSelectionModel().selectedItemProperty().addListener((ov, oldValue,newValue) ->
        {
            
            if(newValue == null || newValue.equals("All Categories"))
            {
                populateCourseIds(null);
                comboCat.setValue(newValue);
                changeLabel.setText(newValue + " category is selected");
                editButton.setDisable(true);
                deleteButton.setDisable(true);
            }
            else
            {
                populateCourseIds(newValue);
                if(newValue.equals("DATABASE"))
                {
                    comboCat.setValue(newValue);
                    changeLabel.setText(newValue + " category is selected");
                }
                else if(newValue.equals("INFORMATION"))
                {
                    comboCat.setValue(newValue);
                    changeLabel.setText(newValue + " category is selected"); 
                }
                else if(newValue.equals("MATH"))
                {
                    comboCat.setValue(newValue);
                    changeLabel.setText(newValue + " category is selected"); 
                }
                else if(newValue.equals("PROGRAMMING"))
                {
                    comboCat.setValue(newValue);
                    changeLabel.setText(newValue + " category is selected"); 
                }
                else if(newValue.equals("SYSTEM"))
                {
                    comboCat.setValue(newValue);
                    changeLabel.setText(newValue + " category is selected"); 
                }
                editButton.setDisable(true);
                deleteButton.setDisable(true);
                //check in console
                System.out.println(newValue);
            }
              
        });
        
       
    }
    
    public void setStage(Stage stage)
    {
        this.stage = stage; //remember the owner
        
        //register close event
        stage.setOnCloseRequest(e -> 
        {
            Alert alert = new Alert(AlertType.CONFIRMATION, "Do you want to exit?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK)
            {
                //quit
                Platform.exit();
            }
            else
            {
                //do nothing
                e.consume();
            }
        });
    }
    
    //populate listview of course ids
    private void populateCourseIds(String category)
    {
        
        if(category == null || category.equals("All Categories"))
        {
            //converting arraylist to observablelist
            ArrayList<String> ids = model.getCourseIds();
            listView.setItems(FXCollections.observableArrayList(ids));
        }
        else {
           ArrayList<String> cat = model.getCourseWithCat(category);
           ObservableList<String> ol = FXCollections.observableList(cat);
           listView.setItems(ol);
        }
      
    }
    
    private void populateCategories()
    {
        ArrayList<String> cat = model.getCategories();
        comboCat.getItems().addAll(FXCollections.observableArrayList(cat));
    }
    
    private void showCourseInfo(String id)
    {
        Course c = model.getCourse(id);
        ArrayList d = model.getCategories();
        if(c == null) { return; }
        
        //Menu item controls
        editItem.setDisable(false);
        addItem.setDisable(false);
        deleteItem.setDisable(false);
        searchItem.setDisable(false);
        
        //change the label to selected id
        changeLabel.setText("You have selected course " + id);
        
        editButton.setDisable(false);
        deleteButton.setDisable(false);
        titleField.setText(c.getTitle());
        creditField.setText(String.valueOf(c.getCredit()));
        categoryMenu.setValue(c.getCategory());
        for(int i = 0; i < d.size(); ++i)
        {
            categoryMenu.setItems(FXCollections.observableArrayList(d));
        }
       
        

                    
            
        
    }
    
    private void clearCourseInfo()
    {
        editItem.setDisable(true);
        deleteItem.setDisable(true);
        titleField.clear();
        creditField.clear();
        categoryMenu.valueProperty().set(null);
        
    }
    
    @FXML
    private void handleOpenAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                                           new FileChooser.ExtensionFilter("All Files","*.*"),
                                           new FileChooser.ExtensionFilter("DAT","*.dat"),
                                           new FileChooser.ExtensionFilter("TXT","*.txt"));
        File file = fileChooser.showOpenDialog(stage);
        if(file == null)
            return;
        
        comboCat.getItems().removeAll(comboCat.getItems());
        model.setFile(file);
        model.readCourseFile(file);
        // if the file contains more than 1 course object than it will populate cat
        if(model.getCourseCount() > 0)
        {
            //this will set the initial combobox item and value to All Categories 
            comboCat.getItems().add("All Categories");
            comboCat.setValue("All Categories");
            
            populateCourseIds(null); // all categories   
            populateCategories(); // single course view
            
            
            changeLabel.setText("Loaded " + model.getCourseCount() + " courses from " + file.getName());
            //menuitem controls
            addItem.setDisable(false);
            searchItem.setDisable(false);
            //enable controls
            listView.setDisable(false);
            comboCat.setDisable(false);
            addButton.setDisable(false);
            searchButton.setDisable(false);
            //disable controls
            editButton.setDisable(true);
            deleteButton.setDisable(true);
            titleField.setDisable(true);
            creditField.setDisable(true);
            categoryMenu.setDisable(true);
            titleField.clear();
            creditField.clear();
            categoryMenu.valueProperty().set(null);
            //this will clear the selection from the combobox
            listView.getSelectionModel().clearSelection();
            
        }
        
    }

    @FXML
    private void handleSaveAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                                            new FileChooser.ExtensionFilter("All Files", "*.*"),
                                            new FileChooser.ExtensionFilter("DAT","*.dat"),
                                            new FileChooser.ExtensionFilter("TXT","*.txt"));
        File file = fileChooser.showSaveDialog(stage);
        
        if(file == null)
            return;
        
        model.saveCourseFile(file);
        changeLabel.setText("Saved " + model.getCourseCount() + " courses to " + file.getName());
                                               
    }

    @FXML
    private void handleExitAction(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    private void handleEditAction(ActionEvent event) {
        //menuItemcontrols
        editItem.setDisable(false);
        addItem.setDisable(true);
        deleteItem.setDisable(true);
        searchItem.setDisable(true);
        
        //button and pane controls
        titleField.setDisable(false);
        creditField.setDisable(false);
        categoryMenu.setDisable(false);
        saveButton.setDisable(false);
        cancelButton.setDisable(false);
        comboCat.setDisable(true);
        listView.setDisable(true);
        deleteButton.setDisable(true);
        addButton.setDisable(true);
        searchButton.setDisable(true);
        changeLabel.setWrapText(true);
        changeLabel.setText("Modify title,credit and category then click "
                              + "\"Save\" or \"Cancel\" button.");
           
    }

    @FXML
    private void handleAddAction(ActionEvent event)  {
        file = model.getFile();
        
        try 
        {
            //load scene graph from fxml
           FXMLLoader loader = new FXMLLoader(getClass().getResource("AddForm.fxml"));
           Parent root = (Parent) loader.load();

           //create and show window as modal
           Scene scene = new Scene(root);
           Stage stage = new Stage();
           stage.initModality(Modality.APPLICATION_MODAL);
           stage.setTitle("Add New Course");
           stage.setScene(scene);

           //pass stage to controller
           AddFormController afc = loader.getController();
           afc.setStage(stage);
           afc.setModel(model);
           
           stage.showAndWait();
        }
        catch(Exception e)
        {
            System.out.print("]ERROR] " + e.getMessage());
        }
        
        //process user response here
        
        Course newid = model.getNewCourseId();
        if(newid.getId() == null)
            return;
        int index = model.getCourseIndex(newid);
        
       
        //save file
        model.saveCourseFile(file);
        
        //reload ids
        populateCourseIds(null);
        listView.getSelectionModel().select(index);
        // focus on new item
        listView.getFocusModel().focus(index);
        listView.scrollTo(index);
        changeLabel.setText("Added a new course " + newid.getId());
        
    }

    @FXML
    private void handleDeleteAction(ActionEvent event) {
        file = model.getFile();
        //get selected ID from listview
        
        String id = listView.getSelectionModel().getSelectedItem();
        if(id==null)
            return;
        
        //confirm deletion using alert
        Alert alert = new Alert(AlertType.CONFIRMATION, "Do you want to delete "
                                 + id + "?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK)
        {
            //Disable the editing btn for efficiency
            editItem.setDisable(true);
            editButton.setDisable(true);
            //delete the course and save to the file
            model.removeCourse(id);
            model.saveCourseFile(file);
            changeLabel.setText("Deleted " + id + " from the list");
            //reload course list
            populateCourseIds(null);
            //deselect on listview
            listView.getSelectionModel().clearSelection();
        }
    }

    @FXML
    private void handleSearchAction(ActionEvent event) {
          
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SearchForm.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Search Courses");
            stage.setScene(scene);

            SearchFormController sfc = loader.getController();
            sfc.setStage(stage);
            sfc.setModel(model);

            //wait for user response
            stage.showAndWait();
        }
        catch(Exception e)
        {
            System.out.print("]ERROR] " + e.getMessage());
        }
        //process user response
        //update listview with selection
        
        Course selectedId = model.getSelectedCourseId();
        if(selectedId.getId() == null)
        {
            editButton.setDisable(true);
            deleteButton.setDisable(true);
            changeLabel.setText("Search was cancelled");
            listView.getSelectionModel().clearSelection();
        }
        int selectedIndex = model.getCourseIndex(selectedId);
        
        if(selectedIndex >= 0)
        {
            //select an item and scroll the list to the selected item
            listView.getSelectionModel().select(selectedIndex);
            listView.getFocusModel().focus(selectedIndex);
            listView.scrollTo(selectedIndex);
        }
        else
        {
            //deselect item
            editButton.setDisable(true);
            deleteButton.setDisable(true);
            changeLabel.setText("Search was cancelled");
            listView.getSelectionModel().clearSelection();
        }
       
    }

    @FXML
    private void handleCancelAction(ActionEvent event) {
        //re-select the course
        String id = listView.getSelectionModel().getSelectedItem();
        listView.getSelectionModel().select(id);
        //enable/disable main controls
        
        listView.setDisable(false);
        comboCat.setDisable(false);
        searchButton.setDisable(false);
        deleteButton.setDisable(false);
        addButton.setDisable(false);
        titleField.setDisable(true);
        creditField.setDisable(true);
        categoryMenu.setDisable(true);
        saveButton.setDisable(true);
        cancelButton.setDisable(true);
        
        //Menu item controls
        editItem.setDisable(false);
        addItem.setDisable(false);
        deleteItem.setDisable(false);
        searchItem.setDisable(false);
       
        changeLabel.setText("Cancelled editing " + id);
        
    }

    @FXML
    private void handleMenuAbout(ActionEvent event) throws IOException {
        
        //load scene graph
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AboutForm.fxml"));
        Parent root = loader.load();
        
        //create scene and attach the root of my scene graph
        Scene scene = new Scene(root);
        //create a stage and attach the scene
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("About");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
        
        //tell controller who is your owner
        AboutFormController afc = loader.getController();
        afc.setStage(stage);
    }

    @FXML
    private void handleSaveCourse(ActionEvent event) {
        
        //save to file loaded in inventory
        file = model.getFile();
        //get selected ID from listview
        String id = listView.getSelectionModel().getSelectedItem();
        if(id == null)
            return;
        //confirm saving
        Alert alert1 = new Alert(AlertType.CONFIRMATION,"Do you want to save"
               + " the changes?" );
        Optional<ButtonType> result = alert1.showAndWait();
        
        if(result.get() != ButtonType.OK)
        {
            editItem.setDisable(false);
            addItem.setDisable(false);
            deleteItem.setDisable(false);
            searchItem.setDisable(false);
            listView.setDisable(false);
            comboCat.setDisable(false);
            searchButton.setDisable(false);
            deleteButton.setDisable(false);
            addButton.setDisable(false);
            titleField.setDisable(true);
            creditField.setDisable(true);
            categoryMenu.setDisable(true);
            saveButton.setDisable(true);
            cancelButton.setDisable(true);
            return;
        }
        
        //alert for validation
        Alert alert2 = new Alert(AlertType.ERROR);
        
        
        
        if(flag)
        {
            //validate title
            String title = titleField.getText();
            if(title == null || title.length() == 0)
            {
                alert2.setContentText("Title cannot be empty.");
                alert2.show(); return;
            }

            //validate credit
            int credit;
            try
            {
                credit = Integer.parseInt(creditField.getText()); 
            }
            catch(NumberFormatException e)
            {
                alert2.setContentText("Credit must be an integer.");
                alert2.show(); return;
            }
            if(credit < 0)
            {
                alert2.setContentText("Credit must be greater than 0.");
                alert2.show();
                return;
            }
            //validate category
            String cat = categoryMenu.getValue();
            if(cat == null)
            {
                alert2.setContentText("Category must be selected.");
                alert2.show();
                return;
            }

            //finally, update the course
            model.updateCourse(id,title,credit,cat);
            model.saveCourseFile(file);
            changeLabel.setText("Updated the course " + id);
        }
        
        editItem.setDisable(false);
        addItem.setDisable(false);
        deleteItem.setDisable(false);
        searchItem.setDisable(false);
        listView.setDisable(false);
        comboCat.setDisable(false);
        searchButton.setDisable(false);
        deleteButton.setDisable(false);
        addButton.setDisable(false);
        titleField.setDisable(true);
        creditField.setDisable(true);
        categoryMenu.setDisable(true);
        saveButton.setDisable(true);
        cancelButton.setDisable(true);
        
    }
    
         
}
