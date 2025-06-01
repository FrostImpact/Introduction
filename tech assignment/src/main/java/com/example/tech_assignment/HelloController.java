package com.example.tech_assignment;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.control.ListCell;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;

public class HelloController {

    @FXML
    private TextField taskInput;

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button editButton;

    @FXML
    private Button doneButton;

    @FXML
    private ListView<String> taskList;

    @FXML
    private Label taskCountLabel;

    @FXML
    private ObservableList<String> tasks;

    @FXML

    //where tasks are saved/pulled from
    private final File taskFile = new File("tasks.txt");

    public void saveTasks(){

        if (!taskFile.exists()){ //if file dosent exist, logs in console and returns
            System.out.println("save file does not exist");
            return;
        }

        //writes tasks from the list to the file line by line
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(taskFile))){
            for (String task : tasks){
                writer.write(task);
                writer.newLine();
            }

        //avoids infinite runtime
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void loadTasks(){ 
        if (!taskFile.exists()){ //same as above
            System.out.println("save file does not exist");
            return;
        }

        //loads tasks from txt file into list
        try (BufferedReader reader = new BufferedReader(new FileReader(taskFile))){
            String line;
            while ((line = reader.readLine()) != null){
                tasks.add(line);
            }

        //save as above
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }



    public void initialize() { 

        tasks = FXCollections.observableArrayList(); 
        loadTasks(); 
        taskList.setItems(tasks);

        //customize how each task is displayed
        taskList.setCellFactory(list -> new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) { //checking if task actually exists
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);
                    if (item.startsWith("[✓] ")) {
                        setStyle("-fx-text-fill: gray;"); //changing colors
                    } else {
                        setStyle("-fx-text-fill: black;");
                    }
                }
            }
        });

        doneButton.setOnAction(event -> {
            String selected = taskList.getSelectionModel().getSelectedItem();
            int index = taskList.getSelectionModel().getSelectedIndex();

            if (selected != null){
                if (selected.startsWith("[✓] ")){
                    selected = selected.substring(4);
                }
                else {
                    selected = "[✓] " + selected;


                }

                tasks.set(index, selected);
            }

            saveTasks();
        });

        editButton.setOnAction(event -> {
            String newText = taskInput.getText().trim();
            int index = taskList.getSelectionModel().getSelectedIndex();

            if (index != -1 && !newText.equals("")){
                tasks.set(index, newText);
                taskInput.clear();
                updateCount();
            }

            saveTasks();
        });

        addButton.setOnAction(event -> {
            String task = taskInput.getText().trim();
            if (!task.isEmpty()) {
                tasks.add(task);
                taskInput.clear();
                updateCount();
            }
            saveTasks();
        });

        deleteButton.setOnAction(event ->{
            String selected = taskList.getSelectionModel().getSelectedItem();

            if (selected != null){
                tasks.remove(selected);
                updateCount();
            }
            saveTasks();

        });

        updateCount();
        saveTasks();
    }

    private void updateCount() { //updates the current amount of tasks, called after every button action
        int count = tasks.size();
        taskCountLabel.setText(count + (count == 1 ? " task remaining" : " tasks remaining"));
    }
}
