/*
 * Created on 21-Nov-05
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.market.gui;

import javax.swing.JPopupMenu;

/**
 * @author cwilkin
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface MarketList {
  
  public void order(boolean reverse);
  
  public int getListSize();

  /**
   * @param selection
   */
  public JPopupMenu getPopup(PropertySelection selection);

}
