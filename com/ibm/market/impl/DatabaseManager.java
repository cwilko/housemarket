/*
 * Created on 05-Nov-04
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.market.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import COM.ibm.db2.jdbc.DB2Exception;

/**
 * @author cwilkin
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DatabaseManager
{  
  private int added = 0;
  String url;
  String user;
  String password;
  Connection con;

  /**
   * 
   */
  public DatabaseManager(String url, String user, String password, String dbDriver)
  {
    this.url = url;
    this.user = user;
    this.password = password;
    
    try {
       //  register the driver with DriverManager 
       //  The newInstance() call is needed for the sample to work with 
       //  JDK 1.1.1 on OS/2, where the Class.forName() method does not 
       //  run the static initializer. For other JDKs, the newInstance 
       //  call can be omitted. 
       Class.forName(dbDriver).newInstance();
       
       con = DriverManager.getConnection(url, user, password);
    } catch (Exception e) {
       e.printStackTrace();
    }
  }
  
  /**
   * 
   */
  public void initialise(String tableName)
  {
    try
    {    
      System.out.println("Connected to database...");
      Statement stmt = con.createStatement();
      try
      {
        stmt.executeUpdate("CREATE TABLE " + tableName +                            "(ID VARCHAR(10) NOT NULL, " +
                           "DATE DATE NOT NULL, " +                           "LOCATION VARCHAR(128) NOT NULL, " +                           "REGION VARCHAR(64), " +                           "TYPE VARCHAR(32) NOT NULL, " +                           "PRICE VARCHAR(16) NOT NULL, " +                           "BEDROOMS VARCHAR(32) NOT NULL, " +                           "AGENT VARCHAR(64) NOT NULL, " +                           "DESCRIPTION VARCHAR(512) NOT NULL, " +
                           "SOLD CHAR(4), " +
                           "DATESOLD DATE NOT NULL, " +                           "PRIMARY KEY(ID))"); 
                         
        System.out.println("Created table "+tableName);
      }
      catch (DB2Exception e)
      {
        System.out.println("Table "+tableName+ " already exists");
      }        
                            
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  
  }

  /**
   * @param details
   */
  public void addEntry(String tableName, PropertyDetails details)
  {
    try
    {
                
      PreparedStatement stmt = 
        con.prepareStatement("INSERT INTO " + tableName +                             "(ID,DATE,LOCATION,REGION,TYPE,PRICE,BEDROOMS,AGENT,DESCRIPTION,SOLD,DATESOLD) " +                             "VALUES (?,?,?,?,?,?,?,?,?,?,?)"); 
                             
      String sold = "FREE";
      if (details.isSold()) sold = "SOLD";                       
                      
      stmt.setString(1, details.getId());
      stmt.setDate(2, new Date(System.currentTimeMillis()));   
      stmt.setString(3, details.getLocation());
      stmt.setString(4, details.getRegion());
      stmt.setString(5, details.getType());
      stmt.setString(6, details.getPrice());
      stmt.setString(7, details.getBedrooms());
      stmt.setString(8, details.getAgentDetails());
      stmt.setString(9, details.getDescription());
      stmt.setString(10, sold);
      stmt.setDate(11, new Date(System.currentTimeMillis())); 
      
      try
      {
        stmt.executeUpdate();
        System.out.println("Added new entry, ID : "+details.getId()+" into table "+tableName);
        details.setNewProperty();
        details.updateInGUI();
        added++;
      }
      catch(DB2Exception e)
      {
        // Entry already exists
        e.printStackTrace();
      }   
    }   
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }

  /**
   * @return
   */
  public int countAdded() {
    return added;
  }  
  
  public PropertyDetails[] runQuery(String tableName, String query)
  {
    PropertyDetails[] properties = null;
    try
    {

      Statement stmt = 
        con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                                      ResultSet.CONCUR_READ_ONLY);
     
      try
      {
        String stmtText = "SELECT ID,DATE,LOCATION,REGION,TYPE,PRICE,BEDROOMS,AGENT,DESCRIPTION,SOLD,DATESOLD FROM " + tableName;
        if (query != null) stmtText+= " WHERE "+query;
        ResultSet results = stmt.executeQuery(stmtText);
        results.last();
        properties = new PropertyDetails[results.getRow()];
        results.beforeFirst();
        int i = 0;
        while(results.next())
        {
          PropertyDetails details = new PropertyDetails();
          details.setId(results.getString("ID"));
          details.setDate(results.getString("DATE"));
          details.setLocation(results.getString("LOCATION"));
          details.setRegion(results.getString("REGION"));
          details.setType(results.getString("TYPE"));
          details.setPrice(results.getString("PRICE"));
          details.setBedrooms(results.getString("BEDROOMS"));
          details.setAgentDetails(results.getString("AGENT"));
          details.setDescription(results.getString("DESCRIPTION"));
          details.setIsSold(new Boolean((results.getString("SOLD").equals("SOLD")?true:false)));
          details.setDateSold(results.getString("DATESOLD"));
          if (details.getDate().equals(new Date(System.currentTimeMillis()).toString()))
            details.setNewProperty();
          properties[i++] = details;
        }
      }
      catch(DB2Exception e)
      {
        // Entry already exists
      }  
    }   
    catch(Exception e)
    {
      e.printStackTrace();
    }
    
    return properties;
    
  }

  /**
   * @param details
   */
  public void updateProperty(String tableName, PropertyDetails details) {
    try
    {   
      PreparedStatement stmt = 
        con.prepareStatement("UPDATE " + tableName +
                             " SET SOLD='SOLD',DATESOLD=? "+
                             "WHERE ID=?"); 

      stmt.setDate(1, new Date(System.currentTimeMillis())); 
      stmt.setString(2, details.getId());
      try
      {
        stmt.executeUpdate();
        System.out.println("Updated entry, ID : "+details.getId()+" is no longer available");
      }
      catch(DB2Exception e)
      {
        System.out.println("Could not update entry to SOLD");
        e.printStackTrace();
      } 
       
    }   
    catch(Exception e)
    {
      System.out.println("Error accessing database");
      e.printStackTrace();
    }
  }

  public void close() {
    System.out.println("Closing db connections");
    try {
      con.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

}
