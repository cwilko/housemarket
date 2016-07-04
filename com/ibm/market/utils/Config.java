/*
 * Created on 11-Oct-04
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.market.utils;

import java.util.Properties;

/**
 * @author cwilkin
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Config extends Properties
{
  
  /* (non-Javadoc)
   * @see java.util.Properties#getProperty(java.lang.String)
   */
  public String getProperty(String arg0) throws UsageException
  {
    String property = super.getProperty(arg0);
    if (property == null)
    {
      Utils.log("Failed to locate value for property " + arg0);
      throw new UsageException();
    }
    return property;
  }
  
  public String getOptionalProperty(String option)
  {
    return super.getProperty(option);
  }

}
