/*
 * Created on 27-Nov-05
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
public class ReportFile extends ConfigFile {

  /**
   * @param fileName
   */
  public ReportFile(String fileName) {
    super(fileName);
  }
  
  public ReportFile()
  {
    super(MarketConstants.REPORT_FILE);
  }

}
