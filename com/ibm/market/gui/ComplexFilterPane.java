/*
 * Created on 15-Nov-05
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.market.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

/**
 * @author cwilkin
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ComplexFilterPane extends JFrame implements ActionListener {
  
  JTextField location = new JTextField(20);
  JTextField date = new JTextField();
  JTextField bedrooms = new JTextField(20);
  JTextField type = new JTextField(20);
  
  MarketFrame parent;
  
  public ComplexFilterPane(MarketFrame parent) {    
   
    this.parent = parent;
    
    getContentPane().setLayout(new BorderLayout());
    setSize(400,300);
    setDefaultLookAndFeelDecorated(true);
    
    JPanel queryPane = new JPanel(new SpringLayout());
    queryPane.setPreferredSize(new Dimension(200,150));
    
    queryPane.add(new JLabel("Location : "));
    queryPane.add(location);

    queryPane.add(new JLabel("Date : "));
    queryPane.add(date);
    
    queryPane.add(new JLabel("Bedrooms : "));
    queryPane.add(bedrooms);
    
    queryPane.add(new JLabel("Type : "));
    queryPane.add(type);    
    
    SpringUtilities.makeCompactGrid(queryPane, //parent
                                    4, 2,
                                    10, 10,  //initX, initY
                                    10, 10); //xPad, yPad
                                    
    JPanel bottomPanel = new JPanel(new SpringLayout());
    
    JButton b1,b2,b3;
    
    b1 = new JButton("OK");
    b1.setActionCommand("ok");
    b1.addActionListener(this);
                      
    b2 = new JButton("Reset");
    b2.setActionCommand("reset");
    b2.addActionListener(this);

    b3 = new JButton("Cancel");
    b3.setActionCommand("cancel");
    b3.addActionListener(this);
    
    bottomPanel.add(b1);
    bottomPanel.add(b2);
    bottomPanel.add(b3);

    SpringUtilities.makeCompactGrid(bottomPanel, //parent
                                    1, 3,
                                    10, 10,  //initX, initY
                                    10, 10); //xPad, yPad                                    

    getContentPane().add(BorderLayout.SOUTH, bottomPanel);                                      
    getContentPane().add(BorderLayout.NORTH, queryPane);                                    
    
  }
  /* (non-Javadoc)
   * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
   */
  public void actionPerformed(ActionEvent e) {
    
    if(e.getActionCommand().equals("ok"))
    {
      String sql = "";
      if (!location.getText().equals(""))  sql+="LOCATION LIKE '%"+location.getText()+"%' ";
      if (!date.getText().equals(""))      sql+=(!sql.equals("")?"AND ":"")+"DATE > '"+date.getText()+"' ";
      if (!bedrooms.getText().equals(""))  sql+=(!sql.equals("")?"AND ":"")+"BEDROOMS LIKE '%"+bedrooms.getText()+"%' ";
      if (!type.getText().equals(""))      sql+=(!sql.equals("")?"AND ":"")+"TYPE LIKE '%"+type.getText()+"%' ";
      parent.setQuery(sql);
      setVisible(false);
    }
    else if (e.getActionCommand().equals("reset"))
    {
      location.setText(null);
      date.setText(null);
      bedrooms.setText(null);
      type.setText(null);
      repaint();
    }
    else if (e.getActionCommand().equals("cancel"))
    {
      setVisible(false);
    }   
  }

}
