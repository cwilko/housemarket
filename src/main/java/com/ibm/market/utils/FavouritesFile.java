/*
 * Created on 25-Nov-05
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.market.utils;

import com.ibm.market.impl.MarketConstants;


/**
 * @author cwilkin
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class FavouritesFile extends ConfigFile{
  
  public FavouritesFile(String fileName) {
   super(fileName); 
  }
  
  public FavouritesFile(){
    super(MarketConstants.FAVE_FILE);
  }
}
