package com.gorodeckaya.fxml.accMenu.deducations;

import com.gorodeckaya.SceneChanger;
import com.gorodeckaya.ServerConnection;
import com.gorodeckaya.classes.Deducation;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DoubleStringConverter;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class Controller implements Initializable {

    @FXML
    private TableView<Deducation> table;
    @FXML
    private TableColumn<Deducation, String> name;
    @FXML
    private TableColumn<Deducation, Double> percent;
    @FXML
    private Button deleteButton;

    public void prevButtonPushed(ActionEvent event) throws IOException {
        SceneChanger sc = new SceneChanger();
        sc.changeScene(event, "/view/accPage.fxml", "Меню");
    }

    public void deleteButtonPushed() throws Exception {
        ObservableList<Deducation> allDeducation;
        Deducation selected;
        allDeducation = table.getItems();

        Deducation deducation = table.getSelectionModel().getSelectedItem();

        List<Object> list = new ArrayList();
        selected = table.getSelectionModel().getSelectedItem();
        deducation.setType("deleteDeducation");
        deducation.setName(selected.getName());
        Object obj = (Object) deducation;
        list = (ServerConnection.send(obj));
        if (Boolean.parseBoolean(list.get(0).toString())) {
            JOptionPane.showMessageDialog(null, "Удалено!");
            allDeducation.remove(deducation);
        }
    }


    public void onEditPercent(TableColumn.CellEditEvent<Deducation, Double> clientDoubleCellEditEvent) throws Exception {

        Deducation newdeducation = table.getSelectionModel().getSelectedItem();
        newdeducation.setPercent(clientDoubleCellEditEvent.getNewValue());

        if (newdeducation.getPercent() > 0 && newdeducation.getPercent() < 100) {
            List<Object> list = new ArrayList();
            newdeducation.setType("editDeducation");
            newdeducation.setName(clientDoubleCellEditEvent.getRowValue().getName());
            Object obj = (Object) newdeducation;
            list = (ServerConnection.send(obj));
            if (Boolean.parseBoolean(list.get(0).toString())) {
                JOptionPane.showMessageDialog(null, "Процент издержки изменен!");
            }
        } else {
            table.getItems().clear();
            Deducation.loadDeducation();
            JOptionPane.showMessageDialog(null, "Только положительные числа до 100%!");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        percent.setCellValueFactory(new PropertyValueFactory<>("percent"));

        List<Deducation> list = new ArrayList();
        list = Deducation.loadDeducation();

        table.getItems().addAll(list);
        table.setEditable(true);
        percent.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));

//        deleteButton.setOnAction(e -> {
//            try {
//                deleteButtonPushed();
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//        });
    }
}
