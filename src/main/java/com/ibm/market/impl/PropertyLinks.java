/*
 * Created on 21-Nov-05
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.market.impl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import com.ibm.market.gui.PropertySelection;

/**
 * @author cwilkin
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class PropertyLinks implements Comparable, PropertySelection {

  JPopupMenu popup;
  PropertyIndex links;
  String region = null;
  int cumulativePrice = 0;
  int averagePrice = 0;
  int bargainValue = 0;
  
  public PropertyLinks(String region)
  {
    links = new PropertyIndex(region);
    this.region = region;
  }
        
  public void addPropertyLink(PropertyDetails prop)
  {
    links.add(prop);
    links.order(true);
   
    // Calculate new figures
    cumulativePrice+=prop.getValue();
    averagePrice=cumulativePrice/links.getListSize();
    bargainValue = averagePrice - ((PropertyDetails)links.getIndex().get(0)).getValue();
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  public String toString() {
    return ((PropertyDetails)links.getIndex().get(0)).toString() +" - "+(int)((bargainValue/(float)(averagePrice - bargainValue))*100)+"% profit";
  }
  
  /* (non-Javadoc)
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  public int compareTo(Object thatPropLinks) {
    final int BEFORE = 1;
    final int EQUAL = 0;
    final int AFTER = -1;
    
    if (isHeadAvailable() && !((PropertyLinks)thatPropLinks).isHeadAvailable()) return AFTER;
    if (!isHeadAvailable() && ((PropertyLinks)thatPropLinks).isHeadAvailable()) return BEFORE;

    if (bargainValue < ((PropertyLinks)thatPropLinks).getBargainValue()) return BEFORE;
    if (bargainValue > ((PropertyLinks)thatPropLinks).getBargainValue()) return AFTER;
    return EQUAL;
  }

  private boolean isHeadAvailable() {
    return !((PropertyDetails)((PropertyDetails)links.getIndex().get(0))).isSold();
  }

  /**
   * @return
   */
  public int getBargainValue() {
    return bargainValue;
  }

  /* (non-Javadoc)
   * @see com.ibm.market.gui.PropertySelection#getDetails()
   */
  public PropertyDetails getDetails() {
    return ((PropertyDetails)links.getIndex().get(0));
  }

  /**
   * @return
   */
  public PropertyIndex getIndex() {
    return links;
  }
  /**
   * @return
   */
  public int getAveragePrice() {
    return averagePrice;
  }

  /**
   * @return
   */
  public String getRegion() {
    return region;
  }

}
