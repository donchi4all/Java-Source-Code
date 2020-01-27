/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import java.awt.Color;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author NIIT PC
 */
public class UserRegistration extends javax.swing.JFrame {

    /**
     * Creates new form UserRegistration
     */
    String databaseUrl = "jdbc:mysql://localhost:3306/";
    String username = "root";
    String password = "";
    String databaseName = "java05Database";
    String tableName = "users";
    Connection con = null;
    String DriverName = "com.mysql.jdbc.Driver";
    Statement statement = null;
    ResultSet rs = null;
    PreparedStatement preparedStatement;

    public UserRegistration() {
        initComponents();
        setLocationRelativeTo(null);
        panel_all.setVisible(false);
    }

    //creating the database
    void createDatabase() {
        try {
            statement = (Statement) con.createStatement();
            String query = " CREATE DATABASE IF NOT EXISTS  " + databaseName;
            int count = statement.executeUpdate(query);
            System.out.println(" Database Count " + count);

            if (count > 0) {
                lbl_connect.setText("Database Created (Connected )");
                btn_connect.setText("Connected");
                lbl_connect.setForeground(Color.GREEN);
            } else {
                lbl_connect.setText("Connected");
                btn_connect.setText("Connected");
                lbl_connect.setForeground(Color.GREEN);
            }
        } catch (SQLException se) {
            JOptionPane.showMessageDialog(this, "Message :  " + se.getMessage() + "\n Code: " + se.getErrorCode() + "\n StateCode " + se.getSQLState(), " Database Error ", JOptionPane.ERROR_MESSAGE);
        }
    }

//    connecting to database
    void connect() {
        try {
            Class.forName(DriverName);
            con = (Connection) DriverManager.getConnection(databaseUrl, username, password);

            if (con != null) {
                createDatabase();
                con = (Connection) DriverManager.getConnection(databaseUrl + databaseName, username, password);
            }

        } catch (ClassNotFoundException ce) {
            JOptionPane.showMessageDialog(this, "Database Not Found ", " Database Error ", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException se) {
            JOptionPane.showMessageDialog(this, "Message :  " + se.getMessage() + "\n Code: " + se.getErrorCode() + "\n StateCode " + se.getSQLState(), " Database Error ", JOptionPane.ERROR_MESSAGE);
            createDatabase();
        }

    }

    void createTable() {
        String query = " create table " + tableName + " (id int primary key auto_increment, "
                + " name varchar(50), username varchar(50) unique, password varchar(50), age int(11))";

        try {
            if (con == null) {
                JOptionPane.showMessageDialog(this, "Database Not Connected ", " Database Error ", JOptionPane.ERROR_MESSAGE);
            }

            DatabaseMetaData dbm = con.getMetaData();
            ResultSet tables = dbm.getTables(null, null, tableName, null);

            if (tables.next()) {
                btn_create_table.setText("Table Connected");
            } else {
                statement = (Statement) con.createStatement();
                statement.executeUpdate(query);

                JOptionPane.showMessageDialog(this, "Table Created Successfully", " Table Info ", JOptionPane.INFORMATION_MESSAGE);

            }
        } catch (SQLException se) {
            JOptionPane.showMessageDialog(this, "Message :  " + se.getMessage() + "\n Code: " + se.getErrorCode() + "\n StateCode " + se.getSQLState(), " Table Error ", JOptionPane.ERROR_MESSAGE);
            se.printStackTrace();
        }
    }

    void insertDb() {
        String inserSQL = " insert into " + tableName + " (name,username,password"
                + ", age) values (?,?,?,?)";
        try {
            if (con == null) {
                JOptionPane.showMessageDialog(this, "Database Not Connected ", " Database Error ", JOptionPane.ERROR_MESSAGE);
            }

            preparedStatement = (PreparedStatement) con.prepareStatement(inserSQL);
            preparedStatement.setString(1, txt_name.getText());
            preparedStatement.setString(2, txt_username.getText());
            preparedStatement.setString(3, txt_password.getText());
            preparedStatement.setInt(4, Integer.parseInt(txt_age.getText()));

            int count = preparedStatement.executeUpdate();
            if (count > 0) {
                JOptionPane.showMessageDialog(this, "User Inserted Successfully", " Table Info ", JOptionPane.INFORMATION_MESSAGE);
                clear(txt_name, txt_username, txt_password, txt_age);
            }
        } catch (SQLException se) {
            JOptionPane.showMessageDialog(this, "Message :  " + se.getMessage() + "\n Code: " + se.getErrorCode() + "\n StateCode " + se.getSQLState(), " Table Error ", JOptionPane.ERROR_MESSAGE);
            se.printStackTrace();
        }

    }

    //searchData
    void searchData(String user) {
        String searchQuery = "select * from " + tableName + " where"
                + " username = ?";
        try {
            if (con == null) {
                JOptionPane.showMessageDialog(this, "Database Not Connected ", " Database Error ", JOptionPane.ERROR_MESSAGE);
            }

            preparedStatement = (PreparedStatement) con.prepareStatement(searchQuery);

            preparedStatement.setString(1, user);

            rs = preparedStatement.executeQuery();
            
            if(rs.next()){
            while (rs.next()) {
                txt_name.setText(rs.getString(2));
                txt_username.setText(rs.getString(3));
                txt_password.setText(rs.getString(4));
                txt_age.setText(rs.getString(5));
            }
            txt_username.setEnabled(false);
            }else{
                 JOptionPane.showMessageDialog(this, "Data Not Found ", " Database Error ", JOptionPane.ERROR_MESSAGE);
        
            }
        } catch (SQLException se) {
            JOptionPane.showMessageDialog(this, "Message :  " + se.getMessage() + "\n Code: " + se.getErrorCode() + "\n StateCode " + se.getSQLState(), " Table Error ", JOptionPane.ERROR_MESSAGE);
            se.printStackTrace();

        }
    }

    //update
    void updateData() {
        String SQLupdate = "update " + tableName + " "
                + "set name=?, password=? ,age =? where username =?";
        try {
            if (con == null) {
                JOptionPane.showMessageDialog(this, "Database Not Connected ", " Database Error ", JOptionPane.ERROR_MESSAGE);
            }

            preparedStatement = (PreparedStatement) con.prepareStatement(SQLupdate);
            preparedStatement.setString(1, txt_name.getText());
            preparedStatement.setString(2, txt_password.getText());
            preparedStatement.setInt(3, Integer.parseInt(txt_age.getText()));
            preparedStatement.setString(4, txt_username.getText());
            
            int count = preparedStatement.executeUpdate();
            if(count>0)
               JOptionPane.showMessageDialog(this, "User Update Successfully", " Table Info ", JOptionPane.INFORMATION_MESSAGE);
                clear(txt_name, txt_username, txt_password, txt_age);
             
        } catch (SQLException se) {
            JOptionPane.showMessageDialog(this, "Message :  " + se.getMessage() + "\n Code: " + se.getErrorCode() + "\n StateCode " + se.getSQLState(), " Table Error ", JOptionPane.ERROR_MESSAGE);
            se.printStackTrace();

        }
    }

    void clear(JTextField txt12, JTextField txt1, JTextField txt2, JTextField txt) {
        txt12.setText(null);
        txt2.setText(null);
        txt1.setText(null);
        txt.setText(null);
    }
    
     private void deleteUser(String username) {
         String SQLdelete ="delete from "+tableName+ " where username=?";
       try {
            if (con == null) {
                JOptionPane.showMessageDialog(this, "Database Not Connected ", " Database Error ", JOptionPane.ERROR_MESSAGE);
            }
             preparedStatement = (PreparedStatement) con.prepareStatement(SQLdelete);
           preparedStatement.setString(1, username);
           
           int count = preparedStatement.executeUpdate();
           if(count > 0)
               JOptionPane.showMessageDialog(this, "User Deleted Successfully", " Table Info ", JOptionPane.INFORMATION_MESSAGE);
               clear(txt_name, txt_username, txt_password, txt_age);
             
               
       }
          catch (SQLException se) {
            JOptionPane.showMessageDialog(this, "Message :  " + se.getMessage() + "\n Code: " + se.getErrorCode() + "\n StateCode " + se.getSQLState(), " Table Error ", JOptionPane.ERROR_MESSAGE);
            se.printStackTrace();

        }
     }

     
     private void searchAll() {
        String searchQuery = "select "
                + "id as IDentity, name as FullName,"
                + "username as LoginName, age as Age from " + tableName ;
        try {
            if (con == null) {
                JOptionPane.showMessageDialog(this, "Database Not Connected ", " Database Error ", JOptionPane.ERROR_MESSAGE);
            }

            preparedStatement = (PreparedStatement) con.prepareStatement(searchQuery);
         rs = preparedStatement.executeQuery();
         
         tbl_all.setModel(DbUtils.resultSetToTableModel(rs));
            
            
        } catch (SQLException se) {
            JOptionPane.showMessageDialog(this, "Message :  " + se.getMessage() + "\n Code: " + se.getErrorCode() + "\n StateCode " + se.getSQLState(), " Table Error ", JOptionPane.ERROR_MESSAGE);
            se.printStackTrace();

        } 
     }
     
     /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        btn_connect = new javax.swing.JButton();
        btn_create_table = new javax.swing.JButton();
        btn_view_table = new javax.swing.JButton();
        lbl_connect = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txt_name = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txt_username = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txt_password = new javax.swing.JTextField();
        txt_age = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        btn_insert = new javax.swing.JButton();
        btn_update = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txt_username_search = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        btn_delete = new javax.swing.JButton();
        btn_query = new javax.swing.JButton();
        panel_all = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_all = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(7, 81, 58));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        btn_connect.setBackground(new java.awt.Color(7, 81, 58));
        btn_connect.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_connect.setText("Connect");
        btn_connect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_connectActionPerformed(evt);
            }
        });

        btn_create_table.setBackground(new java.awt.Color(7, 81, 58));
        btn_create_table.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_create_table.setForeground(new java.awt.Color(255, 255, 255));
        btn_create_table.setText("Create Table");
        btn_create_table.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_create_tableActionPerformed(evt);
            }
        });

        btn_view_table.setBackground(new java.awt.Color(7, 81, 58));
        btn_view_table.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_view_table.setForeground(new java.awt.Color(255, 255, 255));
        btn_view_table.setText("View Table");
        btn_view_table.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_view_tableActionPerformed(evt);
            }
        });

        lbl_connect.setBackground(new java.awt.Color(255, 255, 255));
        lbl_connect.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        lbl_connect.setForeground(new java.awt.Color(255, 0, 0));
        lbl_connect.setText("Not Connected");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_connect, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(lbl_connect, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_create_table, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addComponent(btn_view_table, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_connect, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_create_table, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_view_table, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_connect))
                .addGap(25, 25, 25))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel1.setText("Name :");

        txt_name.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel2.setText("Username");

        txt_username.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel3.setText("Password :");

        txt_password.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N

        txt_age.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel4.setText("Age :");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 78, Short.MAX_VALUE)
                        .addComponent(txt_name, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addGap(60, 60, 60)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_password)
                            .addComponent(txt_username)
                            .addComponent(txt_age))))
                .addGap(35, 35, 35))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txt_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(txt_username, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txt_password, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txt_age, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(53, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        btn_insert.setBackground(new java.awt.Color(0, 51, 51));
        btn_insert.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        btn_insert.setForeground(new java.awt.Color(255, 255, 255));
        btn_insert.setText("Insert");
        btn_insert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_insertActionPerformed(evt);
            }
        });

        btn_update.setBackground(new java.awt.Color(0, 51, 51));
        btn_update.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        btn_update.setForeground(new java.awt.Color(255, 255, 255));
        btn_update.setText("Update");
        btn_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_updateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(btn_insert)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 109, Short.MAX_VALUE)
                .addComponent(btn_update)
                .addGap(34, 34, 34))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_insert)
                    .addComponent(btn_update))
                .addContainerGap(40, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jLabel5.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 51, 51));
        jLabel5.setText("Search :");

        txt_username_search.setBackground(new java.awt.Color(238, 235, 235));
        txt_username_search.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        txt_username_search.setForeground(new java.awt.Color(0, 51, 51));

        jLabel6.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 51, 51));
        jLabel6.setText("Username :");

        btn_delete.setBackground(new java.awt.Color(7, 81, 58));
        btn_delete.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        btn_delete.setText("Delete");
        btn_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deleteActionPerformed(evt);
            }
        });

        btn_query.setBackground(new java.awt.Color(7, 81, 58));
        btn_query.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        btn_query.setText("Query");
        btn_query.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_queryActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txt_username_search, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(83, 83, 83)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_delete, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_query, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_username_search, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_delete)
                    .addComponent(btn_query))
                .addContainerGap())
        );

        panel_all.setBackground(new java.awt.Color(255, 255, 255));

        tbl_all.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tbl_all);

        jButton1.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 51, 51));
        jButton1.setText("Hide");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(0, 51, 51));
        jButton2.setText("Refresh");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_allLayout = new javax.swing.GroupLayout(panel_all);
        panel_all.setLayout(panel_allLayout);
        panel_allLayout.setHorizontalGroup(
            panel_allLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_allLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
            .addGroup(panel_allLayout.createSequentialGroup()
                .addGap(76, 76, 76)
                .addComponent(jButton1)
                .addGap(121, 121, 121)
                .addComponent(jButton2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_allLayout.setVerticalGroup(
            panel_allLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_allLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(panel_allLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panel_all, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addComponent(panel_all, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_connectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_connectActionPerformed
        connect();        // TODO add your handling code here:
    }//GEN-LAST:event_btn_connectActionPerformed

    private void btn_create_tableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_create_tableActionPerformed
        createTable();        // TODO add your handling code here:
    }//GEN-LAST:event_btn_create_tableActionPerformed

    private void btn_insertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_insertActionPerformed
        insertDb();        // TODO add your handling code here:
    }//GEN-LAST:event_btn_insertActionPerformed

    private void btn_queryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_queryActionPerformed
        searchData(txt_username_search.getText());        // TODO add your handling code here:
    }//GEN-LAST:event_btn_queryActionPerformed

    private void btn_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_updateActionPerformed
updateData();        // TODO add your handling code here:
    }//GEN-LAST:event_btn_updateActionPerformed

    private void btn_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteActionPerformed
deleteUser(txt_username_search.getText());        // TODO add your handling code here:
    }//GEN-LAST:event_btn_deleteActionPerformed

    private void btn_view_tableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_view_tableActionPerformed
 panel_all.setVisible(true);
        searchAll();   // TODO add your handling code here:
    }//GEN-LAST:event_btn_view_tableActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
panel_all.setVisible(false);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
searchAll();        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(UserRegistration.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UserRegistration.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UserRegistration.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UserRegistration.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UserRegistration().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_connect;
    private javax.swing.JButton btn_create_table;
    private javax.swing.JButton btn_delete;
    private javax.swing.JButton btn_insert;
    private javax.swing.JButton btn_query;
    private javax.swing.JButton btn_update;
    private javax.swing.JButton btn_view_table;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lbl_connect;
    private javax.swing.JPanel panel_all;
    private javax.swing.JTable tbl_all;
    private javax.swing.JTextField txt_age;
    private javax.swing.JTextField txt_name;
    private javax.swing.JTextField txt_password;
    private javax.swing.JTextField txt_username;
    private javax.swing.JTextField txt_username_search;
    // End of variables declaration//GEN-END:variables

    

   
}
