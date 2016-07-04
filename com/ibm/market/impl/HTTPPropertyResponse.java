/*
 * Created on 05-Nov-04
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.market.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author cwilkin
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class HTTPPropertyResponse extends HTTPResponse
{

  /**
   * @param tempFile
   */
  public HTTPPropertyResponse(String tempFile)
  {
    super(tempFile);
  }
  
  public PropertyDetails[] parse()
  {
    PropertyDetails[] details = null;
  

      String[] idList = getIDs();
      String[] locationList = getLocations();
      String[] agentList = getAgents();
      String[] typeList = getTypes();
      String[] descriptionList = getDescriptions();
      String[] priceList = getPrices();
      String[] bedroomList = getBedrooms();
      Boolean[] soldStatus = getStatus();
      
      
      details = new PropertyDetails[idList.length];
      int i = 0;
      
      for (i=0;i<idList.length;i++)
      {
        PropertyDetails property = new PropertyDetails();
        property.setId(idList[i]);
        property.setLocation(locationList[i]);
        property.setAgentDetails(agentList[i]);
        property.setType(typeList[i]);
        property.setDescription(descriptionList[i]);
        property.setPrice(priceList[i]);
        property.setBedrooms(bedroomList[i]);
        property.setIsSold(soldStatus[i]);
        details[i] = property;
      }

    return details;
  }
  
  /**
   * @return
   */
  private String[] getBedrooms()
  {
    List bedroomLines = getAllLines(MarketConstants.TOKEN_BEDROOMS);
    List bedrooms = new ArrayList();
    Iterator it = bedroomLines.iterator();
    
    while (it.hasNext())
    {
      String line = (String)it.next();
      int offset = line.indexOf(MarketConstants.TOKEN_BEDROOMS)+MarketConstants.TOKEN_BEDROOMS.length();
      line = line.substring(offset).trim(); 
      if (line.indexOf('<') > 0)
        bedrooms.add(line.substring(0,line.indexOf('<')).trim());
      else
        bedrooms.add(line);
    }
    
    return (String[])bedrooms.toArray(new String[bedrooms.size()]);
  }

  /**
   * @return
   */
  private String[] getPrices()
  {
    List priceLines = getAllNextLines(MarketConstants.TOKEN_PRICE);
    List prices = new ArrayList();
    Iterator it = priceLines.iterator();
    
    while (it.hasNext())
    {
      String line = (String)it.next();
      int offset = line.indexOf(MarketConstants.TOKEN_POUND);
      if (offset >= 0)
      {
        offset+=MarketConstants.TOKEN_POUND.length();
        line = line.substring(offset).trim();     
        line = line.substring(0,line.indexOf('<')).trim();
        if (line.length() > MarketConstants.PRICE_LENGTH)
          line = "N/A";
      }
      else
        line = "N/A";
      prices.add(line);  
    }
    
    return (String[])prices.toArray(new String[prices.size()]);
  }

  /**
   * @return
   */
  private String[] getDescriptions()
  {
    List descriptionLines = getAllLines(MarketConstants.TOKEN_DESCRIPTION);
    List descriptions = new ArrayList();
    Iterator it = descriptionLines.iterator();

    while (it.hasNext())
    {

        String line = (String)it.next();
        int offset = line.indexOf(MarketConstants.TOKEN_DESCRIPTION)+MarketConstants.TOKEN_DESCRIPTION.length();
        line = line.substring(offset).trim();
        if (line.indexOf('<') >= 0)
          descriptions.add(line.substring(0,line.indexOf('<')).trim());
        else 
          descriptions.add("See link for description");
    }

    
    return (String[])descriptions.toArray(new String[descriptions.size()]);
  }

  /**
   * @return
   */
  private String[] getTypes()
  {

    List typeLines = getAllLines(MarketConstants.TOKEN_TYPE);
    List types = new ArrayList();
    Iterator it = typeLines.iterator();
    
    while (it.hasNext())
    {
      String line = (String)it.next();
      int offset = line.indexOf(MarketConstants.TOKEN_TYPE)+MarketConstants.TOKEN_TYPE.length();
      line = line.substring(offset).trim();
      if (line.indexOf('<') > 0)
        types.add(line.substring(0,line.indexOf('<')).trim());
      else
        types.add(line);
          
    }
    
    return (String[])types.toArray(new String[types.size()]);
  }
  
  /**
   * @return
   */
  private Boolean[] getStatus()
  {

    List statusLines = getAllLines(MarketConstants.TOKEN_DESCRIPTION);
    List statuses = new ArrayList();

      Iterator it = statusLines.iterator();
      
      while (it.hasNext())
      {
        String line = (String)it.next();
        if (line.indexOf(MarketConstants.TOKEN_SOLD)>0)
          statuses.add(new Boolean(true));
        else
          statuses.add(new Boolean(false));
      }

    return (Boolean[])statuses.toArray(new Boolean[statuses.size()]);
  }

  /**
   * @return
   */
  private String[] getAgents()
  {
    List agentLines = getAllNextLines(MarketConstants.TOKEN_AGENT);
    List agents = new ArrayList();
    Iterator it = agentLines.iterator();
    
    while (it.hasNext())
    {
      String line = (String)it.next();
      agents.add(line.substring(0,line.indexOf('<')).trim());  
    }
    
    return (String[])agents.toArray(new String[agents.size()]);
  }

  /**
   * @return
   */
  private String[] getLocations()
  {
    List locationLines = getAllLines(MarketConstants.TOKEN_LOCATION);
    List locations = new ArrayList();
    Iterator it = locationLines.iterator();
    
    while (it.hasNext())
    {
      String line = (String)it.next();
      int offset = line.indexOf(MarketConstants.TOKEN_LOCATION)+MarketConstants.TOKEN_LOCATION.length();
      locations.add(line.substring(offset).trim());  
    }
    
    return (String[])locations.toArray(new String[locations.size()]);
  }

  private String[] getIDs()
  {
    //System.out.println(getFileName());
    List idLines = getAllLines("p_id");
    List ids = new ArrayList();
    Iterator it = idLines.iterator();
    
    String oldId = null;
    while(it.hasNext())
    {
      String id = new XMLString((String)it.next()).getProperty("p_id", '&');
      if(!id.equals(oldId))
        ids.add(id);
      oldId = id;
    }
    
    return (String[])ids.toArray(new String[ids.size()]);
  }

  /**
   * @return
   */
  public int totalPages()
  {
    XMLString pageLine = new XMLString(getLine(MarketConstants.TOKEN_PAGES));    
    return Integer.parseInt(pageLine.getProperty(MarketConstants.TOKEN_PAGES,' '));
  }

}
