package com.gorodeckaya;

import com.gorodeckaya.classes.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static Socket clientDialog;

    public Server(Socket client) {
        Server.clientDialog = client;
    }

    static ExecutorService executeIt = Executors.newFixedThreadPool(2);

    public static void main(String[] args) {
        System.out.println("Введите порт сервера:");
        Scanner in = new Scanner(System.in);
        int port = in.nextInt();

        try (ServerSocket server = new ServerSocket(port);
             BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            while (!server.isClosed()) {
                if (br.ready()) {
                    String serverCommand = br.readLine();
                    if (serverCommand.equalsIgnoreCase("quit")) {
                        System.out.println("Main Server initiate exiting...");
                        server.close();
                        break;
                    }
                }
                Socket client = server.accept();
                String text = "Server port: " + server.getLocalPort() + " IP: " + server.getLocalSocketAddress() + '\n';
                System.out.println(text);
                executeIt.execute(new MonoThreadClientHandler(client));
            }
            executeIt.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class MonoThreadClientHandler implements Runnable {

        private static Socket clientDialog;

        public MonoThreadClientHandler(Socket client) {
            MonoThreadClientHandler.clientDialog = client;
        }

        @Override
        public void run() {
            ObjectOutputStream out = null;
            ObjectInputStream in = null;

            try {

                out = new ObjectOutputStream(clientDialog.getOutputStream());
                in = new ObjectInputStream(clientDialog.getInputStream());
                String text = "Client port: " + clientDialog.getPort() + " IP: " + clientDialog.getInetAddress() + '\n';
                System.out.println(text);
                try {

                    while (true) {
                        Object clientMassegeRecieved = (Object) in.readObject();
                        List<Object> clientMessageAnswer = findClass(clientMassegeRecieved);
                        out.writeObject(clientMessageAnswer);
                    }
                }
                catch (EOFException e){
                    System.out.println("Клиент вышел");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    in.close();
                    out.close();
                    clientDialog.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }



    public static List<Object> findClass(Object obj) {
        String host = "jdbc:mysql://localhost:3306/payrollsystem?allowPublicKeyRetrieval=true&useSSL=false" +
                "?verifyServerCertificate=false" +
                "&useSSL=false" +
                "&requireSSL=false" +
                "&useLegacyDatetimeCode=false" +
                "&amp" +
                "&serverTimezone=UTC";
        if (obj instanceof Post) {
            Post s = new Post();
            Post post = (Post) obj;
            try {
                Connection con = DriverManager.getConnection(host, "root", "28092003");
                if (post.getType().equals("allPost")) {
                    return s.getAllPost(con);
                }
                else if(post.getType().equals("addPost")){
                    return s.addPost(con, post);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else if (obj instanceof Deducation) {
            Deducation s = new Deducation();
            Deducation deducation = (Deducation) obj;
            try {
                Connection con = DriverManager.getConnection(host, "root", "28092003");
                if (deducation.getType().equals("allDeducation")) {
                    return s.getAllDeducation(con);
                } else if (deducation.getType().equals("editDeducation")) {
                    return s.editDeducation(con, deducation);
                } else if (deducation.getType().equals("deleteDeducation")) {
                    return s.deleteDeducation(con, deducation);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else if (obj instanceof Employee) {
            Employee s = new Employee();
            Employee employee = (Employee) obj;
            List<Object> list = new ArrayList<>();
            try {
                Connection con = DriverManager.getConnection(host, "root", "28092003");
                if (employee.getType().equals("allEmployee")) {
                    return s.getAllEmployee(con);
                } else if (employee.getType().equals("editEmployee")) {
                    return s.editEmployee(con, employee);
                } else if (employee.getType().equals("deleteEmployee")) {
                    return s.deleteEmployee(con, employee);
                } else if (employee.getType().equals("addEmployee")) {
                    return s.addEmployee(con, employee);
                } else if (employee.getType().equals("addUser")) {
                    return s.addUser(con,employee);
                } else if (employee.getType().equals("checkEmployee")) {
                    list.add(s.checkEmployee(con, employee));
                    return list;
                }else if (employee.getType().equals("checkLogin")) {
                    list.add(s.checkLogin(con, employee));
                    return list;
                } else if (employee.getType().equals("allEmployeeForTotal")) {
                    return s.getAllEmployeeForTotal(con, employee);
                }else if (employee.getType().equals("EmployeeForPaySheet")) {
                    return s.getEmployeeForPaySheet(con, employee);
                }else if (employee.getType().equals("Фамилия")) {
                    return s.searchEmployee(con, employee);
                }
                else if (employee.getType().equals("Имя")) {
                    return s.searchEmployee(con, employee);
                }
                else if (employee.getType().equals("Отчество")) {
                    return s.searchEmployee(con, employee);
                }
                else if (employee.getType().equals("Лицевой счёт")) {
                    return s.searchEmployee(con, employee);
                }
                else if (employee.getType().equals("Должность")) {
                    return s.searchEmployee(con, employee);
                }
                else if (employee.getType().equals("Отдел")) {
                    return s.searchEmployee(con, employee);
                }
                else if (employee.getType().equals("E-mail")) {
                    return s.searchEmployee(con, employee);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else if (obj instanceof String) {
            Charges ch=new Charges();
            String str = (String) obj;
            try {
                Connection con = DriverManager.getConnection(host, "root", "28092003");
                if (str.equals("AllCharges")) {
                    return ch.getAllNameCharges(con);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else if (obj instanceof Total) {
            Total total=new Total();
            Total t = (Total) obj;
            try {
                Connection con = DriverManager.getConnection(host, "root", "28092003");
                if (t.getType().equals("total")) {
                    return t.CalculationTotal(con,t);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else if (obj instanceof Payroll) {
            Payroll payroll = new Payroll();
            Payroll payroll1 = (Payroll) obj;
            try {
                Connection con = DriverManager.getConnection(host, "root", "28092003");
                if (payroll1.getType().equals("addPayroll")) {
                    return payroll.addPayroll(con, (Payroll) obj);
                }else  if (payroll1.getType().equals("editPayroll")) {
                    return payroll.editPayroll(con, (Payroll) obj);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else if (obj instanceof User) {
            User user = new User();
            User user1 = (User) obj;
            try {
                Connection con = DriverManager.getConnection(host, "root", "28092003");
                if (user1.getType().equals("checkEmp")) {
                    return user1.checkUser(con,user1.getLogin(),user1.getPassword(),user1.getType());
                }
                if (user1.getType().equals("checkAdmin")) {
                    return user1.checkUser(con,user1.getLogin(),user1.getPassword(),user1.getType());
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        List<Object> list = new ArrayList();
        list.add("false");
        return list;

    }
}
