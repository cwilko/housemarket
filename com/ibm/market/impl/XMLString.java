/*
 * Created on 04-Nov-04
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.market.impl;

/**
 * @author cwilkin
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class XMLString
{
  String propertyString;
  /**
   * @param string
   */
  public XMLString(String propertyString)
  {
    this.propertyString = propertyString;
  }

  /**
   * Extract property from a property string of the format : property=value
   * @param property
   * @return
   */
  public String getProperty(String property, char ch)
  {
    String value = null;
    property+="=";
    
    int index = propertyString.lastIndexOf(property);
    if (index >= 0)
    {
      value = propertyString.substring(index+property.length());
      if (value.indexOf(ch)>=0)
        value = value.substring(0, value.indexOf(ch));
    }
    
    try{
    if (value != null)
    	value = value.replaceAll("\"","");
    }catch (NullPointerException e)
    {
      System.out.println(propertyString);
      e.printStackTrace();
    }
    
    return value;
  }
}
