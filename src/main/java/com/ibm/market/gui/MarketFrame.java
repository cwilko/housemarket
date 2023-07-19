/*
 * Created on 08-Dec-04
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.market.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.ScrollPane;
import java.awt.event.KeyEvent;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.Spring;
import javax.swing.SpringLayout;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.ibm.market.impl.MarketAnalyzer;
import com.ibm.market.impl.PropertyDetails;
import com.ibm.market.impl.PropertyIndex;
import com.ibm.market.utils.Config;
import com.sun.media.sound.AlawCodec;

/**
 * @author cwilkin
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class MarketFrame extends JFrame implements RootFrame
{
 
  DetailPane detailPane;
  AnalyzerPane analyzerPane;
  ComplexFilterPane complexFilterPane;
  PropertyIndex index;
  ListPane listPane;
  BrowserPane browserPane;
  ButtonPane buttonPane;
  BasicFilterPane filterPane;
  MarketAnalyzer app;
  Config props;
  /**
   * @param arg0
   * @param arg1
   */
  public MarketFrame(MarketAnalyzer app, Config props)
  {
    super("Market Analyzer");
    this.app = app;
    this.props = props;
    
    setSize(1000,800);
    setDefaultLookAndFeelDecorated(true);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    
    Container content = getContentPane();
    content.setLayout(new BorderLayout());
    
    // Button Panel
    
    buttonPane = new ButtonPane(new SpringLayout(), this);  

    // Details Pane
    detailPane = new PropertyDetailPane(new SpringLayout(), this);
    detailPane.displayPropertyDetails(null);
    
    // List Pane
    listPane = new ListPane(null, this);

    // City Pane
    
    filterPane = new BasicFilterPane(new BorderLayout(), this);
    
    // North Panel
    JPanel northPane = new JPanel(new BorderLayout());
    northPane.add(buttonPane, BorderLayout.WEST);
    northPane.add(filterPane, BorderLayout.EAST);
    
   
    // Content Pane
    content.add(northPane, BorderLayout.NORTH);
    content.add(listPane, BorderLayout.CENTER);
    content.add(detailPane, BorderLayout.EAST);
    
  }
  /**
   * 
   */
  public void runQuery() {
    app.retrieveAllRecords(filterPane.getCity(), filterPane.getSQL(), listPane);    
  }
  /**
   * 
   */
  public void runUpdate() {
    app.init(filterPane.getCity(),props);    
  }
  /**
   * 
   */
  public void runFilter() {
    if (complexFilterPane == null) 
      complexFilterPane = new ComplexFilterPane(this);
    
    if (!complexFilterPane.isVisible())
      complexFilterPane.setVisible(true);
    
  }
  /**
   * 
   */
  public BrowserPane getBrowserPane() {
    if (browserPane == null || !browserPane.isShowing()) 
    {
      if (browserPane != null) browserPane.dispose();
      browserPane = new BrowserPane();
      browserPane.setVisible(true);
    } 
    return browserPane;    
  }
  /**
   * 
   */
  public ListPane getListPane() {
    return listPane;  
  }
  /**
   * 
   */
  public ButtonPane getButtonPane() {
    return buttonPane;    
  }
  /**
   * 
   */
  public BasicFilterPane getFilterPane() {
    return filterPane;    
  }
  public DetailPane getDetailPane() {
    return detailPane;    
  }
  /**
   * @param sql
   */
  public void setQuery(String sql) {
    filterPane.setSQL(sql);   
  }
  /**
   * 
   */
  public AnalyzerPane runAnalyzer() {
    if (analyzerPane == null || !analyzerPane.isShowing()) 
    {
      if (analyzerPane != null) analyzerPane.dispose();
      analyzerPane = new AnalyzerPane(this, (PropertyIndex)getListPane().getIndex());
      analyzerPane.setVisible(true);
    } 
    return analyzerPane;
  }
  /* (non-Javadoc)
   * @see com.ibm.market.gui.RootFrame#displayPropertyDetails(com.ibm.market.impl.PropertyDetails)
   */
  public void displayPropertyDetails(PropertySelection details) {
    detailPane.displayPropertyDetails((PropertyDetails)details);
  }
  
  public void dispose() {
    // TODO Auto-generated method stub
    super.dispose();
    app.close();
    
    System.exit(0);
  }

   
}
