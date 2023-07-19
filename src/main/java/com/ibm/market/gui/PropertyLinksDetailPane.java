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
import com.ibm.market.impl.PropertyLinks;

/**
 * @author cwilkin
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class PropertyLinksDetailPane extends DetailPane{

  /**
   * @param lm
   * @param parent
   */
  public PropertyLinksDetailPane(LayoutManager2 lm, JFrame parent) {
    super(lm, parent);
  }

  /**
   * @param object
   * @param detailPane
   */
  public void displayPropertyDetails(PropertySelection selection) {
    
     PropertyLinks prop = (PropertyLinks)selection;
     removeAll();
     setPreferredSize(new Dimension(800,100));
     PropertyDetails details;
     if (prop == null) 
     {
       details = new PropertyDetails();
       prop = new PropertyLinks(null);
     } 
     else
       details = prop.getDetails();
    
     add(new JLabel("Region Match: "));
     add(new JLabel(prop.getRegion()));
     
     add(new JLabel("Location : "));
     add(new JLabel(details.getLocation()));
     
     add(new JLabel("Price : "));
     add(new JLabel(details.getPrice()));
    
     add(new JLabel("Potential Profit : "));
     add(new JLabel("£"+prop.getBargainValue()));
    
     add(new JLabel("Average Price : "));
     add(new JLabel("£"+prop.getAveragePrice()));
    
     add(new JLabel("No. of Bedrooms : "));
     add(new JLabel(details.getBedrooms()));   

    
     SpringUtilities.makeCompactGrid(this, //parent
                                     2, 6,
                                     10, 10,  //initX, initY
                                     20, 10); //xPad, yPad

     parent.getContentPane().validate();
     parent.repaint();                              
                   
    
  }

}
