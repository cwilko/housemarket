/*
 * Created on 14-Nov-05
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.market.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.LayoutManager2;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * @author cwilkin
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class BasicFilterPane extends JPanel implements ItemListener{

  private boolean isOrdered;
  private MarketFrame parent;
  JComboBox city;
  JTextField sqlField;
  JCheckBox order;
  
  public BasicFilterPane(LayoutManager2 lm, MarketFrame parent)
  {
    super(lm);
    this.parent = parent;
    
    setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
    
    city = new JComboBox();
    city.setBackground(Color.WHITE);
    city.setEditable(true);
    city.addItem("NewForest");
    city.addItem("Southampton");
    
    JPanel cityPane = new JPanel();
    cityPane.add(new JLabel("City : "));
    cityPane.add(city);

    sqlField = new JTextField(20);
    JPanel sqlPane = new JPanel();
    sqlPane.add(new JLabel("SQL Query : "));
    sqlPane.add(sqlField);
    
    JPanel orderPane = new JPanel();
    order = new JCheckBox();
    order.addItemListener(this);
    orderPane.add(order);
    orderPane.add(new JLabel("Order by price"));
    
    add(orderPane, BorderLayout.CENTER);
    add(cityPane, BorderLayout.EAST);
    add(sqlPane, BorderLayout.SOUTH);
  }
  
  public String getCity()
  {
    return (String)city.getSelectedItem();
  }

  /**
   * @return
   */
  public String getSQL() {
    return (String)sqlField.getText();
  }

  /* (non-Javadoc)
   * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
   */
  public void itemStateChanged(ItemEvent e) {
    Object source = e.getItemSelectable();
    if (source == order)
    {
      parent.getListPane().orderProperties();
      if (e.getStateChange() == ItemEvent.SELECTED)
        isOrdered = true;
      if (e.getStateChange() == ItemEvent.DESELECTED)
        isOrdered = false;
    }    
  }

  /**
   * @return
   */
  public boolean isOrdered() {
    return isOrdered;
  }

  /**
   * @param sql
   */
  public void setSQL(String sql) {
    sqlField.setText(sql);    
  }
}
