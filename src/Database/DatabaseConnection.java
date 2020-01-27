/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import java.sql.ResultSet;

/**
 *
 * @author NIIT PC
 */
public class DatabaseConnection {
    
  String databaseUrl ="jdbc:mysql://localhost:3306/";
  String username ="root";
  String password="";
  String databaseName ="java05";
  String tableName="user";
  Connection con =null;
  String DriverName="com.mysql.jdbc.Driver";
  Statement statement = null;
  ResultSet rs = null;
  PreparedStatement preparedStatement ;
  
  
  
          
}
