/*
 * Created on 21-Nov-05
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.market.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager2;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import com.ibm.market.impl.PropertyDetails;
import com.ibm.market.impl.PropertyIndex;
import com.ibm.market.impl.PropertyLinks;

/**
 * @author cwilkin
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class AnalyzerPane extends JFrame implements RootFrame {
  
  ListPane middleListPane;
  ListPane bottomListPane;
  DetailPane topDetailPane;
  DetailPane middleDetailPane;
  RootFrame parent;
  
  public AnalyzerPane(RootFrame parent, PropertyIndex index)
  {
    super("Market Analyzer - Analyzed Data");
    this.parent = parent;
    setSize(1000,900);
    setDefaultLookAndFeelDecorated(true);
    
    Container content = getContentPane();
    content.setLayout(new BorderLayout());
    
    JPanel middlePanel = new JPanel(new BorderLayout());
    JPanel bottomPanel = new JPanel(new BorderLayout());
    JPanel topPanel = new JPanel(new BorderLayout());
    
    // Top Pane
    topDetailPane = new PropertyLinksDetailPane(new SpringLayout(), this);
    topDetailPane.displayPropertyDetails(null);
    
    topPanel.add(new JLabel("Deal Summary"), BorderLayout.NORTH);
    topPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
    topPanel.add(topDetailPane, BorderLayout.CENTER);
    
    // Middle Pane
    middleListPane = new ListPane(null, this);
    middleListPane.displayPropertyList(index.analyze());
    middleListPane.setPreferredSize(new Dimension(100,600));
    
    middleDetailPane = new PropertyDetailPane(new SpringLayout(), this);
    middleDetailPane.displayPropertyDetails(null);
    
    middlePanel.add(middleListPane, BorderLayout.CENTER);
    middlePanel.add(middleDetailPane, BorderLayout.EAST);
    
    // Bottom Pane
    bottomListPane = new ListPane(null, this);
    bottomListPane.displayPropertyList(new PropertyIndex(null));
    
    bottomPanel.add(bottomListPane, BorderLayout.CENTER);
    
    // Content
    JPanel centrePanel = new JPanel(new SpringLayout());
    centrePanel.add(topPanel);
    centrePanel.add(middlePanel);
    //centrePanel.add(bottomPanel);
    
//    SpringUtilities.makeCompactGrid(centrePanel, //parent
//                                    2, 1,
//                                    10, 10,  //initX, initY
//                                    10, 10); //xPad, yPad
    
    content.add(topPanel, BorderLayout.NORTH);
    content.add(middlePanel, BorderLayout.CENTER);
    content.add(bottomPanel, BorderLayout.SOUTH);
                         
  }

  /* (non-Javadoc)
   * @see com.ibm.market.gui.RootFrame#getDetailPane()
   */
  public DetailPane getDetailPane() {
    return topDetailPane;
  }

  /* (non-Javadoc)
   * @see com.ibm.market.gui.RootFrame#getFilterPane()
   */
  public BasicFilterPane getFilterPane() {
    // TODO Auto-generated method stub
    return parent.getFilterPane();
  }

  /* (non-Javadoc)
   * @see com.ibm.market.gui.RootFrame#getBrowserPane()
   */
  public BrowserPane getBrowserPane() {
    // TODO Auto-generated method stub
    return parent.getBrowserPane();
  }

  /* (non-Javadoc)
   * @see com.ibm.market.gui.RootFrame#displayPropertyDetails(com.ibm.market.impl.PropertyDetails)
   */
  public void displayPropertyDetails(PropertySelection selection) {
    if (selection instanceof PropertyLinks)
    {
      PropertyLinks links = (PropertyLinks)selection;
      middleDetailPane.displayPropertyDetails(links.getDetails());
      topDetailPane.displayPropertyDetails(links);
      PropertyIndex index = links.getIndex();
      bottomListPane.displayPropertyList(index);
    }
    else
    if (selection instanceof PropertyDetails)
    {
      PropertyDetails details = (PropertyDetails) selection;
      middleDetailPane.displayPropertyDetails(details);
    }
    
  }


}
