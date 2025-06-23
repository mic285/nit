package org.example.notepad;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;

public class NotepadController {
    private int fontSize = 12; // Default font size
    @FXML
    private TabPane tabPane;


    private Stage primaryStage;

    @FXML
    private void handleNewTab() {
        TextArea textArea = new TextArea();
        Tab newTab = new Tab("Untitled");
        newTab.setContent(textArea);
        tabPane.getTabs().add(newTab);
        tabPane.getSelectionModel().select(newTab);
    }
    @FXML
    private void handleZoomOut() {
        if (fontSize > 8) fontSize -= 2;
        updateFontSize();
    }

    @FXML
    private void handleOpen() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File file = fileChooser.showOpenDialog(primaryStage);
        if (file != null) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                TextArea textArea = new TextArea();
                String line;
                while ((line = reader.readLine()) != null) {
                    textArea.appendText(line + "\n");
                }

                Tab tab = new Tab(file.getName());
                tab.setContent(textArea);
                tabPane.getTabs().add(tab);
                tabPane.getSelectionModel().select(tab);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handleSave() {
        Tab currentTab = tabPane.getSelectionModel().getSelectedItem();
        if (currentTab == null) return;

        TextArea textArea = (TextArea) currentTab.getContent();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File file = fileChooser.showSaveDialog(primaryStage);
        if (file != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(textArea.getText());
                currentTab.setText(file.getName());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void handleExit(){
        System.exit(0);
    }
    private void updateFontSize() {
        Tab CurrentTab =
                tabPane.getSelectionModel().getSelectedItem();
        if (CurrentTab != null) {
            TextArea textArea =
                    (TextArea) CurrentTab.getContent();
            textArea.setStyle("-fx-font-size: " + fontSize + "px;");
        }
    }
    @FXML
    private void handleZoomIn() {
        if (fontSize < 50) {
            fontSize += 2;
            updateFontSize();
        }
    }

}


