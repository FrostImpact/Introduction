package com.example.tech_assignment;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.control.ListCell;

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
    public void initialize() {

        taskList.setCellFactory(list -> new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);
                    if (item.startsWith("[✓] ")) {
                        setStyle("-fx-text-fill: gray;");
                    } else {
                        setStyle("-fx-text-fill: black;");
                    }
                }
            }
        });

        tasks = FXCollections.observableArrayList();
        taskList.setItems(tasks);

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
        });

        editButton.setOnAction(event -> {
            String newText = taskInput.getText().trim();
            int index = taskList.getSelectionModel().getSelectedIndex();

            if (index != -1 && !newText.equals("")){
                tasks.set(index, newText);
                taskInput.clear();
                updateCount();
            }
        });

        addButton.setOnAction(event -> {
            String task = taskInput.getText().trim();
            if (!task.isEmpty()) {
                tasks.add(task);
                taskInput.clear();
                updateCount();
            }
        });

        deleteButton.setOnAction(event ->{
            String selected = taskList.getSelectionModel().getSelectedItem();

            if (selected != null){
                tasks.remove(selected);
                updateCount();
            }

        });

        updateCount();
    }

    private void updateCount() {
        int count = tasks.size();
        taskCountLabel.setText(count + (count == 1 ? " task remaining" : " tasks remaining"));
    }
}
