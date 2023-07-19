/*
 * Created on 05-Nov-04
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.market.impl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.ibm.market.gui.ListRenderer;
import com.ibm.market.gui.MarketList;
import com.ibm.market.gui.PropertySelection;
import com.ibm.market.impl.ProcessedPropertyIndex.PropertyPopup;
import com.ibm.market.utils.FavouritesFile;
import com.ibm.market.utils.KeywordFile;
import com.ibm.market.utils.Utils;

/**
 * @author cwilkin
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class PropertyIndex extends JList implements MarketList, ActionListener
{
  List index;
  String name;
  
  /**
   * @author cwilkin
   *
   * To change the template for this generated type comment go to
   * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
   */
  public class PropertyPopup extends JPopupMenu {
    
    public PropertyPopup(final ActionListener parent)
    {
      final JMenuItem fave = new JMenuItem("Add to favourites");
      fave.addActionListener(parent);
      fave.setActionCommand("favourite");
      add(fave);
    }
  }
  
  /**
   * 
   */
  public PropertyIndex(final String name)
  {
    index = new ArrayList();
    this.name = name;
    setListData(index.toArray());  
    setCellRenderer(new ListRenderer());
  }

  /**
   * @param properties
   */
  public void add(final PropertyDetails[] properties)
  {
    for (int i=0;i<properties.length;i++)
    {
      add(properties[i]);
    } 
  }
  
  /**
   * @param properties
   */
  public void add(final PropertyDetails property)
  {
    index.add(property);
    property.setIndex(this);   
    updateGUI();
  }
  
  /**
   * Output a report
   *
   */
  public void generateReport()
  {
    Utils.report("Housing Report for City of "+name+" :\n");
    
    final Iterator it = index.iterator();
    while(it.hasNext())
    {
      final PropertyDetails property = (PropertyDetails)it.next();
      
      Utils.report("ID : \t\t\t"+property.getId());
      Utils.report("Location : \t\t"+property.getLocation());
      Utils.report("Type : \t\t\t"+property.getType());
      Utils.report("Bedrooms : \t\t"+property.getBedrooms());
      Utils.report("Value : \t\t"+property.getPrice());
      Utils.report("Description : \t"+property.getDescription());
      Utils.report("Marketed by : \t"+property.getAgentDetails());
      if (property.isSold()) System.out.println("SOLD");
      Utils.report(null);
    }
    Utils.closeReport();
  }

  /**
   * Output a report
   *
   */
  public void generateFullReport()
  {
    Utils.report("Housing Report for City of "+name+" :\n");
    
    final Iterator it = index.iterator();
    while(it.hasNext())
    {
      final PropertyDetails property = (PropertyDetails)it.next();
      
      Utils.report("ID : \t\t\t"+property.getId());
      Utils.report("Location : \t\t"+property.getLocation());
      Utils.report("Type : \t\t\t"+property.getType());
      Utils.report("Bedrooms : \t\t"+property.getBedrooms());
      Utils.report("Value : \t\t"+property.getPrice());
      Utils.report("Description : \t"+property.getDescription());
      Utils.report("Agent : \t"+property.getAgentDetails());
      Utils.report("Add Date : \t"+property.getDate());
      Utils.report("Sold Date : \t"+property.getDateSold());
      Utils.report("Days on Market : \t"+property.getDaysOnMarket());
      Utils.report("Pri by : \t"+property.isNew());
      if (property.isSold()) Utils.report("Status : \tSOLD"); else Utils.report("Status : \tFREE");
      Utils.report(null);
    }
    Utils.closeReport();
  }
  /**
   * @param db
   */

  /** REMOVED
  public void persist(final DatabaseManager db)
  {
    int insertAttempts = 0;
    final int indexSize = index.size();
    double percent = 0.1;
    final Iterator it = index.iterator();
    while (it.hasNext())
    {
      db.addEntry(name, (PropertyDetails) it.next());
      if (insertAttempts++==indexSize*percent)
      {
        System.out.println((percent*100) + "% Complete");
        percent+=0.1;
      }
    }
    System.out.println(db.countAdded() + " new properties added");
    
    // Update the available properties
    final PropertyDetails[] availableProps = db.runQuery(name, MarketConstants.AVAILABLE_QUERY);
    if (availableProps != null)
    {
      for (int x=0; x<availableProps.length; x++)
      {
        if (!index.contains(availableProps[x]))
          db.updateProperty(name, availableProps[x]);
      }
    }
  }
  **/

  public void updateGUI() {
    setListData(index.toArray());
  }
  
  /* (non-Javadoc)
   * @see com.ibm.market.gui.MarketList#order()
   */
  public void order(final boolean reverse) {
    Collections.sort(index);
    if (reverse) Collections.reverse(index);
    updateGUI();
  }

  /* (non-Javadoc)
   * @see com.ibm.market.gui.MarketList#getListSize()
   */
  public int getListSize() {
    return index.size();
  }
  
  public MarketList analyze()
  {
    final MarketList results = new ProcessedPropertyIndex(index);
    
    return results;
  }

  /**
   * 
   */
  public List getIndex() {
    return index;
  }

  /* (non-Javadoc)
   * @see com.ibm.market.gui.MarketList#getPopup(com.ibm.market.gui.PropertySelection)
   */
  public JPopupMenu getPopup(final PropertySelection selection) {
    final JPopupMenu popup = new PropertyPopup(this);
    return popup;
  }
  
  /* (non-Javadoc)
   * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
   */
  public void actionPerformed(final ActionEvent e) {
    if (e.getActionCommand().equals("favourite"))
    {
      final PropertyDetails details = (PropertyDetails)getSelectedValue();
      
      // Add the keyword to the config file
      final FavouritesFile file = Utils.getFavouritesFile();
      file.write(new Date(System.currentTimeMillis()).toString()+" : "+details.toString()+" - "+details.getURL());
      file.close();  
      
    }    
  }

}
