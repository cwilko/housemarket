/*
 * Created on 04-Nov-04
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.market.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;

import com.ibm.market.gui.PropertySelection;

/**
 * @author cwilkin
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class PropertyDetails implements Comparable, PropertySelection
{
  
  int priceValue;
  boolean sold;
  String id;
  String location;
  String region;
  String type;
  String description;
  String price;
  String bedrooms;
  String agentDetails;
  String date;
  
  PropertyIndex index;
  private boolean isNew = false;
  private String dateSold;
  private int daysOnMarket;
  
  public PropertyDetails()
  {
    date = new Date(System.currentTimeMillis()).toString();
  }
  
  /**
   * @return
   */
  public String getAgentDetails()
  {
    return agentDetails;
  }

  /**
   * @return
   */
  public String getBedrooms()
  {
    return bedrooms;
  }

  /**
   * @return
   */
  public String getDescription()
  {
    return description;
  }

  /**
   * @return
   */
  public String getId()
  {
    return id;
  }

  /**
   * @return
   */
  public String getLocation()
  {
    return location;
  }

  /**
   * @return
   */
  public String getPrice()
  {
    return price;
  }
  
  /**
   * @return
   */
  public int getValue()
  {
    return priceValue;
  }

  /**
   * @return
   */
  public String getRegion()
  {
    return region;
  }

  /**
   * @return
   */
  public String getType()
  {
    return type;
  }

  /**
   * @param string
   */
  public void setAgentDetails(String string)
  {
    agentDetails = string;
  }

  /**
   * @param string
   */
  public void setBedrooms(String string)
  {
    bedrooms = string;
  }

  /**
   * @param string
   */
  public void setDescription(String string)
  {
    description = string;
  }

  /**
   * @param string
   */
  public void setId(String string)
  {
    id = string;
  }

  /**
   * @param string
   */
  public void setLocation(String string)
  {
    location = string;
  }

  /**
   * @param string
   */
  public void setPrice(String price)
  {
    this.price = price;  
    priceValue = (int) new Float(price.replaceAll(",","")).floatValue();
  }

  /**
   * @param string
   */
  public void setRegion(String string)
  {
    region = string;
  }

  /**
   * @param string
   */
  public void setType(String string)
  {
    type = string;
  }

  /**
   * @param boolean1
   */
  public void setIsSold(Boolean sold)
  {
    this.sold = sold.booleanValue();
  }
  
  public boolean isSold()
  {
    return sold;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  public String toString() {
    String displayString = getLocation()+" :  £"+getPrice();
    if (isNew) displayString+=" ** NEW **";
    return displayString;
  }

  /**
   * 
   */
  public void setNewProperty() {
    isNew = true;   
  }

  /**
   * @param index
   */
  public void setIndex(PropertyIndex index) {
    this.index = index;    
  }

  /**
   * @param string
   */
  public void setDate(String date) {
    this.date = date.substring(0, date.indexOf(' '));    
  }
  
  public String getDate()
  {
    return date;
  }

  /**
   * @return
   */
  public boolean isNew() {
    return isNew;
  }

  /* (non-Javadoc)
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  public int compareTo(Object thatProp) {
    final int BEFORE = 1;
    final int EQUAL = 0;
    final int AFTER = -1;

    if (getValue() > ((PropertyDetails)thatProp).getValue()) return BEFORE;
    if (getValue() < ((PropertyDetails)thatProp).getValue()) return AFTER;
    return EQUAL;
  }

  /* (non-Javadoc)
   * @see com.ibm.market.gui.PropertySelection#getDetails()
   */
  public PropertyDetails getDetails() {
    return this;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  public boolean equals(Object details) {    
    return ((PropertyDetails)details).getId().equals(id);
  }

  public void setDateSold(String dateSold) {

    dateSold = dateSold.substring(0, dateSold.indexOf(' '));
    this.dateSold = dateSold.replaceAll("-","/");    
    StringTokenizer strTok = new StringTokenizer(dateSold,"-",false);
    
    int year = Integer.parseInt(strTok.nextToken());
    int month = Integer.parseInt(strTok.nextToken());
    int day = Integer.parseInt(strTok.nextToken());

    GregorianCalendar date1 = new GregorianCalendar(year, month, day);
    
    strTok = new StringTokenizer(getDate(),"-",false);
    
    year = Integer.parseInt(strTok.nextToken());
    month = Integer.parseInt(strTok.nextToken());
    day = Integer.parseInt(strTok.nextToken());

    GregorianCalendar date2 = new GregorianCalendar(year, month, day);

    long millisBetween = date1.getTimeInMillis() - date2.getTimeInMillis();
    
    daysOnMarket = (int) Math.round (millisBetween / (1000 * 60 * 60 * 24));

  }
  
  public String getDateSold()
  {
    return dateSold;
  }

  public int getDaysOnMarket() {
    return daysOnMarket;
  }

  public void updateInGUI() {
    index.updateGUI();    
  }

  public String getURL() {
    return "http://www.rightmove.co.uk/property/"+getId();
  }

}
