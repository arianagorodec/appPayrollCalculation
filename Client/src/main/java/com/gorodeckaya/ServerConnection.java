package com.gorodeckaya;

import javafx.scene.control.Alert;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class ServerConnection {
    public static Socket clientSocket;
    public static ObjectOutputStream coos;
    public static ObjectInputStream cois;


    public void startConnection(int port){

        System.out.println("Подключение к серверу port:"+port+"....");
        try {
            clientSocket = new Socket("localhost",port);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Ошибка подключения");
            alert.setContentText("Проверьте работу сервера");
            alert.showAndWait();
            e.printStackTrace();
        }
        System.out.println("Соединение установлено....");
        try {
            coos = new ObjectOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            cois = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static List<Object> send(Object obj) throws Exception {
        coos.writeObject(obj);
        List<Object> list = (List<Object>)cois.readObject();
        return list;
    }

    public static List<Object> recv(Object obj) throws Exception {
        List<Object> list = (List<Object>)cois.readObject();
        return list;
    }

    public void closeConnection() throws Exception{
        cois.close();
        coos.close();
        clientSocket.close();
    }
}
