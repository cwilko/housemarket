/*
 * Created on 14-Nov-05
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.market.gui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager2;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.ibm.market.impl.PropertyDetails;
import com.ibm.market.impl.PropertyIndex;

/**
 * @author cwilkin
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ListPane extends JPanel implements ListSelectionListener {
  
  private JScrollPane listScroller;
  private JList index;
  private RootFrame parent;
  private JLabel label;
  private String labelText = "Property Search Results";
  
  public ListPane(LayoutManager2 lm, RootFrame parent)
  {
    super(lm);
    this.parent = parent;
    
    listScroller = new JScrollPane();    
    listScroller.setAlignmentX(LEFT_ALIGNMENT);
    listScroller.setPreferredSize(new Dimension(100,100));
    
    setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
    label = new JLabel(labelText);     

    add(label);
    add(Box.createRigidArea(new Dimension(0,5)));
    add(listScroller);
    setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
  }

  public void displayPropertyList(MarketList list) {  
    index = (JList)list;  
    listScroller.getViewport().removeAll();
    listScroller.getViewport().add((JList)index);  
    index.addListSelectionListener(this);
    index.addMouseListener(new ListMouseListener());
    label.setText(labelText+" : "+list.getListSize()+" properties found"); 
    if(parent.getFilterPane().isOrdered()) list.order(false);
  }
  

  /* (non-Javadoc)
   * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
   */
  public void valueChanged(ListSelectionEvent e) {
    
      PropertySelection prop = (PropertySelection) index.getSelectedValue();
      if (prop!=null)
      {
        parent.displayPropertyDetails(prop);
      }     
    }
    
  class ListMouseListener extends MouseAdapter {
    
    /* (non-Javadoc)
     * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
     */
    public void mouseClicked(MouseEvent e) {
      if (e.getClickCount() == 2) {          // Double-click
        // Get item index
        parent.getBrowserPane().loadURL(((PropertySelection)index.getSelectedValue()).getDetails());     
      }
    }

    public void mousePressed(MouseEvent e) {
        index.setSelectedIndex(index.locationToIndex(e.getPoint()));
        maybeShowPopup(e);
    }

    private void maybeShowPopup(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
            PropertySelection selection = (PropertySelection)index.getSelectedValue();
            ((MarketList)index).
              getPopup(selection).show(
                  e.getComponent(), 
                  e.getX(), 
                  e.getY());
        }
    }
  }
  /**
   * 
   */
  public void orderProperties() {
    displayPropertyList((MarketList)index);
  }

  public MarketList getIndex() {
    return (MarketList)index;
  }



}
