/*
 * Created on 30-Oct-04
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.market.impl;
import javax.swing.JFrame;

import com.ibm.market.gui.ListPane;
import com.ibm.market.gui.MarketFrame;
import com.ibm.market.utils.*;
import com.ibm.market.utils.Config;
import com.ibm.market.utils.Utils;


/**
 * @author cwilkin
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class MarketAnalyzer
{
  public static void main(String[] args)
  {
    System.out.println("MarketAnalyzer 1.0 - Copyright@2005 C.Wilkinson");
    new MarketAnalyzer(args);
  }
  
  MarketFrame gui;
  DatabaseManager db;
  String url = "jdbc:postgresql://192.168.1.206:5432/squirrel";
  String dbDriver = "org.postgresql.Driver";

  public MarketAnalyzer(String[] args) {
    
    Config config = Utils.readProperties(args);
    
    // Initialise DBManager
    String dbname = config.getOptionalProperty("-dbName");
    if (dbname != null) 
      url = "jdbc:postgresql://192.168.1.206:5432/"+dbname;
    
    String dbDriver = config.getOptionalProperty("-dbDriver");
    if (dbDriver != null) 
      this.dbDriver = dbDriver;

    String dbUser = config.getProperty("-dbUser");
    String dbPassword = config.getProperty("-dbPassword");
    tableName = config.getOptionalProperty("-tableName");
    
    db = new DatabaseManager(url, dbUser, dbPassword, this.dbDriver);
    
    
    gui = new MarketFrame(this,config); 
    gui.setVisible(true);
  }

  String area = "-1";
  String city = null;
  String maxPrice = null;
  String county = null;
  String tableName = "houses";
  
  public void init(String city, Config config)
  {	
  	try
  	{
	    //city = config.getProperty("-city");
	    maxPrice = config.getProperty("-maxPrice");
	    county = config.getProperty("-county");
	    String radius = config.getOptionalProperty("-miles");
	    if (radius != null) area = radius;
  	}
  	catch (UsageException e)
  	{
  		usage();
  	}
    
    this.gui.runQuery();
  
  }
  
  public void retrieveAllRecords(String city, String sql, ListPane gui)
  {
    PropertyIndex index = new PropertyIndex(city);
    
    //index.add(db.runQuery(city, sql));

    sql += (!sql.equals("") ? "AND " : "") + "REGION LIKE '%" + city + "%' ";
    index.add(db.runQuery(tableName, sql));
    
    index.generateFullReport();
    
    gui.displayPropertyList(index);    
  }

  /**
   * 
   */
  private static void usage() {
  	System.out.println( "Usage : java MarketAnalyzer [options]");
  	System.out.println( 
  		"	(options)	-city 			City Name\n" +
  		"				-maxPrice		Maximum Search Price\n" +
  		"				-county			Search County\n" +
  		"				-miles			(1.0/3.0/5.0 mile radius or default = immediate area)\n" +
  		"				-dbName			Database name (default = HOUSES)");
  		
  	System.out.println("N.B. Ensure db2java.zip is added to classpath");			
  	System.exit(0);
  }

  public void close() {
    db.close();
  }
}
