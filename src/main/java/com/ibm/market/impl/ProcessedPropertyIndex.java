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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import com.ibm.market.gui.MarketList;
import com.ibm.market.gui.PropertySelection;
import com.ibm.market.utils.KeywordFile;
import com.ibm.market.utils.Utils;

/**
 * @author cwilkin
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ProcessedPropertyIndex extends JList implements MarketList, ActionListener {

  HashMap results;
  
  /**
   * @author cwilkin
   *
   * To change the template for this generated type comment go to
   * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
   */
  public class PropertyPopup extends JPopupMenu {
    
    public PropertyPopup(ActionListener parent)
    {
      JMenuItem remove = new JMenuItem("Remove");
      remove.addActionListener(parent);
      remove.setActionCommand("remove");
      add(remove);
    }
  }
  /**
   * @param index
   */
  public ProcessedPropertyIndex(List index) {
    
    // Load in any exempt words from the keyword file
    ArrayList exemptKeywords = loadExemptKeywords();
    // Build a list of keywords as the hash
    results = getKeywords(index, exemptKeywords);
    ArrayList values = new ArrayList(results.values());
    Collections.sort(values);
    setListData(values.toArray());    
  }

  /**
   * @return
   */
  private ArrayList loadExemptKeywords() {
    
    ArrayList exemptKeywords = new ArrayList();    
    KeywordFile file = Utils.getKeywordFile();
    String keyword = null;
    while((keyword = file.read()) != null)
      exemptKeywords.add(keyword); 
    file.close();       
    return exemptKeywords;
  }

  /**
   * @param index
   * @return
   */
  private HashMap getKeywords(List index, List exemptKeywords) {
    
    HashMap results = new HashMap();
    Iterator it = index.iterator();
    while(it.hasNext())
    {
      PropertyDetails prop = (PropertyDetails)it.next();
      String location = prop.getLocation();
      StringTokenizer st = new StringTokenizer(location, " ,1234567890-");
      while(st.hasMoreTokens())
      {
        String region = st.nextToken();
        if (!exemptKeywords.contains(region.toLowerCase()))
        {
          String key = region.toLowerCase() + prop.getBedrooms().toLowerCase();
          PropertyLinks links = (PropertyLinks) results.get(key);
          if (links == null)
          {
            links = new PropertyLinks(region);
            results.put(key, links);
          }
          links.addPropertyLink(prop);
        }
      }
    }
    return results;
  }

  /* (non-Javadoc)
   * @see com.ibm.market.gui.MarketList#order()
   */
  public void order(boolean reverse) {
   //f Collections.sort(results);
  }

  /* (non-Javadoc)
   * @see com.ibm.market.gui.MarketList#getListSize()
   */
  public int getListSize() {
    return results.keySet().size();
  }

  /* (non-Javadoc)
   * @see com.ibm.market.gui.MarketList#getPopup(com.ibm.market.gui.PropertySelection)
   */
  public JPopupMenu getPopup(PropertySelection selection) {
    JPopupMenu popup = new PropertyPopup(this);
    return popup;
  }

  /* (non-Javadoc)
   * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
   */
  public void actionPerformed(ActionEvent e) {
    if (e.getActionCommand().equals("remove"))
    {
      PropertyLinks links = (PropertyLinks)getSelectedValue();
      String region = links.getRegion().toLowerCase();
      
      Iterator it = results.keySet().iterator();
      while(it.hasNext())
      {
        String key = (String)it.next();
        if (key.startsWith(region))
          it.remove();        
      }
      
      // Add the keyword to the config file
      KeywordFile file = Utils.getKeywordFile();
      file.write(region);
      file.close();

      ArrayList values = new ArrayList(results.values());
      Collections.sort(values);
      setListData(values.toArray());  
      
    }    
  }
}
