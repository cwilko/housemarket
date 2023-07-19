package com.ibm.market.gui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

import com.ibm.market.impl.PropertyDetails;

/*
 * Created on 15-Nov-05
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

/**
 * @author cwilkin
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ListRenderer extends DefaultListCellRenderer {
      
    public Component getListCellRendererComponent(JList list, 
                                                  Object value,
                                                  int index, 
                                                  boolean isSelected,
                                                  boolean cellHasFocus) {

      super.getListCellRendererComponent(list, 
                                         value, 
                                         index, 
                                         isSelected, 
                                         cellHasFocus);
      if (value instanceof PropertyDetails) {
        PropertyDetails details = (PropertyDetails)value;
        if (!details.isSold())
        {
          setForeground(Color.BLUE);
        }
        if (details.isNew()) {
          setBackground(Color.RED);
        }
        
      }
      return this;
    }


}
