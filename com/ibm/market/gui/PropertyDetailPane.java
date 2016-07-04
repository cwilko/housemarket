/*
 * Created on 21-Nov-05
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.market.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.LayoutManager2;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import com.ibm.market.impl.PropertyDetails;

/**
 * @author cwilkin
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class PropertyDetailPane extends DetailPane {

  /**
   * @param lm
   * @param parent
   */
  public PropertyDetailPane(LayoutManager2 lm, JFrame parent) {
    super(lm, parent);
  }
  
  /**
   * @param object
   * @param detailPane
   */
  public void displayPropertyDetails(PropertySelection selection) {
    
    PropertyDetails prop = (PropertyDetails)selection;
    removeAll();
    setPreferredSize(new Dimension(400,800));
    
    if (prop == null) prop = new PropertyDetails();
 
    add(new JLabel("ID : "));
    add(new JLabel(prop.getId()));
    
    add(new JLabel("Date Added : "));
    add(new JLabel(prop.getDate()));
    
    add(new JLabel("Location : "));
    add(new JLabel(prop.getLocation()));
    
    add(new JLabel("Region : "));
    add(new JLabel(prop.getRegion()));
    
    add(new JLabel("Price : "));
    add(new JLabel(prop.getPrice()));
    
    add(new JLabel("No. of Bedrooms : "));
    add(new JLabel(prop.getBedrooms()));
    
    add(new JLabel("Property Type : "));
    add(new JLabel(prop.getType()));
    
    add(new JLabel("Agent : "));
    add(new JLabel(prop.getAgentDetails()));
    
    add(new JLabel("Status : "));
    if (prop.isSold()) 
    {
      String sold = "SOLD";
      if (prop.getDaysOnMarket() > 0) sold += " after "+prop.getDaysOnMarket()+" days";
      add(new JLabel(sold));
    }      
    else
      add(new JLabel("Available"));
    
    add(new JLabel(" Full Description : "));
    JTextArea desc = new JTextArea(prop.getDescription());
    desc.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
    desc.setLineWrap(true);
    desc.setWrapStyleWord(true);
    desc.setBackground(Color.LIGHT_GRAY);
    desc.setRows(7);
    add(desc);
    //add(new JLabel(prop.getDescription()));

    
    SpringUtilities.makeCompactGrid(this, //parent
                                    20, 1,
                                    10, 10,  //initX, initY
                                    10, 10); //xPad, yPad

    parent.getContentPane().validate();
    parent.repaint();                              
    
  }


}
