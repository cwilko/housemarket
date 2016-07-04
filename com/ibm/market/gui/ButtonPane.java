/*
 * Created on 14-Nov-05
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.market.gui;

import java.awt.Container;
import java.awt.LayoutManager2;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * @author cwilkin
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ButtonPane extends JPanel implements ActionListener{

  private MarketFrame parent;
  
  public ButtonPane(LayoutManager2 lm, MarketFrame parent)
  {
    super(lm);
    this.parent = parent;
    
    JButton b1,b2,b3,b4;
    
    b1 = new JButton("Run Query");
    b1.setMnemonic(KeyEvent.VK_R);
    b1.setActionCommand("run");
    b1.setToolTipText("Query database for property results");
    b1.addActionListener(this);
                      
    b2 = new JButton("Filter");
    b2.setMnemonic(KeyEvent.VK_F);
    b2.setActionCommand("filter");
    b2.setToolTipText("Change the criteria to query on");
    b2.addActionListener(this);

    b3 = new JButton("Update");
    b3.setMnemonic(KeyEvent.VK_U);
    b3.setActionCommand("update");
    b3.setToolTipText("Retrieve latest data from internet and update database");
    b3.addActionListener(this);
    
    b4 = new JButton("Analyze!");
    b4.setMnemonic(KeyEvent.VK_A);
    b4.setActionCommand("analyze");
    b4.setToolTipText("Run the analyzer on the current data");
    b4.addActionListener(this);
    
    add(b1);
    add(b2);
    add(b3);
    add(b4);

    SpringUtilities.makeCompactGrid(this, //parent
                                    2, 2,
                                    10, 10,  //initX, initY
                                    10, 10); //xPad, yPad

  }

  /* (non-Javadoc)
   * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
   */
  public void actionPerformed(ActionEvent e) {
    
    if(e.getActionCommand().equals("run"))
    {
      parent.runQuery();
    }
    else if (e.getActionCommand().equals("update"))
    {
      parent.runUpdate();
    }
    else if (e.getActionCommand().equals("filter"))
    {
      parent.runFilter();
    }  
    else if (e.getActionCommand().equals("analyze"))
    {
      parent.runAnalyzer();
    }  
  }
}
