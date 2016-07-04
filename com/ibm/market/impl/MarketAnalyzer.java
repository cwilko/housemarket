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
  String url = "jdbc:db2:HOUSES";
  String dbDriver = "COM.ibm.db2.jdbc.app.DB2Driver";

  public MarketAnalyzer(String[] args) {
    
    Config config = Utils.readProperties(args);
    
    // Initialise DBManager
    String dbname = config.getOptionalProperty("-dbName");
    if (dbname != null) 
      url = "jdbc:db2:"+dbname;
    
    String dbDriver = config.getOptionalProperty("-dbDriver");
    if (dbDriver != null) 
      this.dbDriver = dbDriver;
    
    
    db = new DatabaseManager(url, "", "", dbDriver);
    
    
    gui = new MarketFrame(this,config); 
    gui.setVisible(true);
  }

  String area = "-1";
  String city = null;
  String maxPrice = null;
  String county = null;
  
  public void init(String city, Config config, ListPane gui)
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
    
    SourceURL url1 = new SourceURL(MarketConstants.URL_MAIN);    
    HTTPResponse response = url1.getResponse(null);    
    String address2 = response.getProperty("edit_search.rsp", "action");
    
    SourceURL url2 = new SourceURL(address2 = (address2+"&s_lo="+city+"&b=buy&psa=new&search=Start+Search"));    
    HTTPResponse response2 = url2.getResponse(MarketConstants.URL_MAIN);    
    String address3 = response2.getProperty("modify_search_criteria_form", "action");
    String regionCode = response2.getProperty(county,"value", '^');
    
     
    PropertyIndex index = new PropertyIndex(city);
    gui.displayPropertyList(index);
    
    HTTPPropertyResponse response3;
    int i = 1;
    long initialTime = System.currentTimeMillis();
    do
    {
      SourceURL url3 = new SourceURL(address3+
        "?tr_t=buy&lo_n=&lo_u="+regionCode+"%5E"+city+"%2C+"+county+"+&se_t="+area+"&ma_p="+maxPrice+"&nh_st=1&stc_s=true&eventSubmit_doSearch=1&initial_search_time="+initialTime+"&pa_n="+i);
       
      response3 = (HTTPPropertyResponse) url3.getResponse(address2);
      PropertyDetails[] properties = response3.parse();
      index.add(properties);
      System.out.println(i);      
      
//      try {
//        Thread.sleep(5000);
//      } catch (InterruptedException e1) {
//        // TODO Auto-generated catch block
//        e1.printStackTrace();
//      }
    
    } while (i < (i = response3.getNextPage()));
   
    index.generateReport();
        
    db.initialise(city);
    index.persist(db); 
  
  }
  
  public void retrieveAllRecords(String city, String sql, ListPane gui)
  {
    PropertyIndex index = new PropertyIndex(city);
    
    index.add(db.runQuery(city, sql));
    
    index.generateFullReport();
    
    gui.displayPropertyList(index);    
  }

  /**
   * 
   */
  private static void usage() {
  	System.out.println( "Usage : java MarketAnalyzer [options]");
  	System.out.println( 
  		"	(options)	-city 			City Name\n" +  		"				-maxPrice		Maximum Search Price\n" +  		"				-county			Search County\n" +  		"				-miles			(1.0/3.0/5.0 mile radius or default = immediate area)\n" +  		"				-dbName			Database name (default = HOUSES)");
  		
  	System.out.println("N.B. Ensure db2java.zip is added to classpath");			
  	System.exit(0);
  }

  public void close() {
    db.close();
  }
}
