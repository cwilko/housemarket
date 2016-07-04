/*
 * Created on 15-Nov-05
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.market.gui;

import java.awt.BorderLayout;
import java.io.IOException;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.ibm.market.impl.PropertyDetails;

/**
 * @author cwilkin
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class BrowserPane extends JFrame {
  
  JEditorPane content;
  JTextField urlField;
  
  public BrowserPane() {
    
    JPanel topPanel = new JPanel();
    JLabel urlLabel = new JLabel("URL:");
    urlField = new JTextField(30);
    topPanel.add(urlLabel);
    topPanel.add(urlField);
    
    content = new JEditorPane();
    JScrollPane scrollPane = new JScrollPane(content);
    getContentPane().add(scrollPane, BorderLayout.CENTER);
    getContentPane().add(topPanel, BorderLayout.NORTH);

    setSize(800,800);
    setDefaultLookAndFeelDecorated(true);
  }

  /**
   * @param object
   */
  public void loadURL(PropertyDetails prop) {
    
    try {
      String url = prop.getURL();
      content.setPage(url);
      urlField.setText(url);
    } catch (IOException e) {
      e.printStackTrace();
    }
    
  }

}
