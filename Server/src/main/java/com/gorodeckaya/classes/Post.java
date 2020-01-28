package com.gorodeckaya.classes;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Post implements Serializable {

    private static final long serialVersionUID = 2912885090749157690L;

    int code;
    String name;
    String department;
    Double salary;
    String type;

    public Post(int code, String name, String department, Double salary, String type) {
        this.code = code;
        this.name = name;
        this.department = department;
        this.salary = salary;
        this.type = type;
    }

    public Post(int code, String name, String department, Double salary) {
        this.code = code;
        this.name = name;
        this.department = department;
        this.salary = salary;
        this.type = " ";
    }

    public Post() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Object> getAllPost(Connection con) {
        List<Object> list = new ArrayList();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from `payrollsystem`.`organization_structure`;");
            //boolean check=false;
            while (rs.next()) {
                /*if(list!=null){
                    for (Object object: list){
                        Post post = (Post) object;
                        if(post.getDepartment().equals(rs.getString("department"))){
                            check=true;
                        }
                    }
                }
                if(!check)*/
                    list.add(new Post(rs.getInt("code_post"), rs.getString("post"),
                        rs.getString("department"), rs.getDouble("salary")));
            }
            rs.close();
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /*public List<Object> editPost(Connection con, Post post) {
        List<Object> list = new ArrayList();
        List<Object> newList = new ArrayList<>();
        try {
            PreparedStatement st = con.prepareStatement("DELETE FROM organizatio_structure WHERE code_post = " + post.getCode() + ";");
            st.executeUpdate();
            st.close();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from organizatio_structure;");
            String str_update = "insert into organizatio_structure(code_post, department, post, salary) values (" +
                    post.getCode() + ",'" + post.getDepartment() + "', '" + post.getName() + "', '" + post.getSalary() +
                    "'" + ")";
            stmt.executeUpdate(str_update);
            newList = getAllPost(con);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return newList;
    }

    public List<Object> removePost(Connection con, Post post) {
        //String list = "";
        List<Object> list = new ArrayList();
        List<Object> serv = new ArrayList();
        try {
            Statement stmt = con.createStatement();
            PreparedStatement st = con.prepareStatement("DELETE FROM organizatio_structure WHERE code_post = " + post.getCode() + ";");
            st.executeUpdate();
            list = getAllPost(con);
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }*/

    public List<Object> addPost(Connection con, Post post) throws SQLException {
        List<Object> list = new ArrayList();
        try {
            List<Integer> id = new ArrayList();
            Statement statement = con.createStatement();

            String str_update = "INSERT INTO `payrollsystem`.`organiz" +
                    "ation_structure` (`department`, `post`, `salary`) VALUES ('"+post.getDepartment()+"', '"+post.getName()+"' ,'"+post.getSalary()+"')";
            statement.executeUpdate(str_update);
            //list = getAllPost(con);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            con.close();
        }
        list.add("true");
        return list;
    }
}
