/*
 * Created on 21-Nov-05
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.market.gui;

import com.ibm.market.impl.PropertyDetails;

/**
 * @author cwilkin
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface RootFrame {

  public DetailPane getDetailPane();

  /**
   * 
   */
  public BasicFilterPane getFilterPane();

  /**
   * 
   */
  public BrowserPane getBrowserPane();

  /**
   * @param details
   */
  public void displayPropertyDetails(PropertySelection selection);
}
