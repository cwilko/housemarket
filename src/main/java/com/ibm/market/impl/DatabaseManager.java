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

// import COM.ibm.db2.jdbc.DB2Exception;

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

       Class.forName(dbDriver);//.newInstance();
       
       con = DriverManager.getConnection(url, user, password);
    } catch (ClassNotFoundException e) {
      System.out.println("Could not find: " + dbDriver);
    } catch (Exception e) {
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
     
      String stmtText = "";
      try
      {
        stmtText = "SELECT ID,CREATED_ON,LOCATION,REGION,TYPE,PRICE,BEDROOMS,AGENT,DESCRIPTION,SOLD,SOLD_ON FROM " + tableName;
        if (query != null && !query.trim().isEmpty()) stmtText+= " WHERE "+query;
        ResultSet results = stmt.executeQuery(stmtText);
        results.last();
        properties = new PropertyDetails[results.getRow()];
        results.beforeFirst();
        int i = 0;
        while(results.next())
        {
          PropertyDetails details = new PropertyDetails();
          details.setId(results.getString("ID"));
          details.setDate(results.getString("CREATED_ON"));
          details.setLocation(results.getString("LOCATION"));
          details.setRegion(results.getString("REGION"));
          details.setType(results.getString("TYPE"));
          details.setPrice(results.getString("PRICE"));
          details.setBedrooms(results.getString("BEDROOMS"));
          details.setAgentDetails(results.getString("AGENT"));
          details.setDescription(results.getString("DESCRIPTION"));
          details.setIsSold(new Boolean((results.getString("SOLD").equals("SOLD")?true:false)));
          details.setDateSold(results.getString("SOLD_ON"));
          if (details.getDate().equals(new Date(System.currentTimeMillis()).toString()))
            details.setNewProperty();
          properties[i++] = details;
        }
      }
      //catch(DB2Exception e)
      catch(Exception e)
      {
        System.out.println("Error running SQL query: " + stmtText);
        e.printStackTrace();
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
                             " SET SOLD='SOLD',SOLD_ON=? "+
                             "WHERE ID=?"); 

      stmt.setDate(1, new Date(System.currentTimeMillis())); 
      stmt.setString(2, details.getId());
      try
      {
        stmt.executeUpdate();
        System.out.println("Updated entry, ID : "+details.getId()+" is no longer available");
      }
      //catch(DB2Exception e)
      catch (Exception e)
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
