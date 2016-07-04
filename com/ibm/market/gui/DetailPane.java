/*
 * Created on 14-Nov-05
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.market.gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager2;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.ibm.market.impl.PropertyDetails;

/**
 * @author cwilkin
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class DetailPane extends JPanel {

  protected JFrame parent;

  public DetailPane(LayoutManager2 lm, JFrame parent) {
    super(lm);
    this.parent = parent;
    
    setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
  }
  
  public abstract void displayPropertyDetails(PropertySelection prop);
  
}
