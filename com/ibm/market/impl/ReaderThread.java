/*
 * Created on 14-Sep-05
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.market.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author cwilkin
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ReaderThread extends Thread {
  
  URLConnection conn;
  InputStream stream;
  Object lock;

  /**
   * 
   */
  public ReaderThread(URL url, Object lock, String refer) throws Exception{   
    
    this.conn = url.openConnection();
    if (refer!=null) conn.setRequestProperty("Referer",refer);
    
    this.lock = lock;
    start();  
  }
  
  /* (non-Javadoc)
   * @see java.lang.Runnable#run()
   */
  public void run() {      
    try {
      stream = conn.getInputStream();
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    synchronized(lock){
      lock.notify();
    }
    
    // System.out.println("Stream obtained");
    

  }
  
  public InputStream getStream()
  {
    return stream;
  }

}
