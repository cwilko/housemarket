/*
 * Created on 03-Nov-04
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.market.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author cwilkin
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class SourceURL
{
  String address;
  
  /**
   * 
   */
  public SourceURL(String address)
  {
    this.address = address;
  }

  /**
   * 
   */
  public HTTPResponse getResponse(String refer)
  {
    // Request a page from the given URL
    
    String tempFile = "response"+address.hashCode();
    
    System.out.println(tempFile + " received from "+ address);
    try 
    {
      // Create a URLConnection object for a URL
      URL url = new URL(address);

      
      InputStream stream = null;
      while( stream == null)
      {
        System.out.println("Requesting page from server...");
        Object lock = new Object();
        synchronized(lock)
        {
          ReaderThread reader = new ReaderThread(url, lock, refer);
          lock.wait(5000);
          stream = reader.getStream();
        }
      }

      BufferedReader br = new BufferedReader(new InputStreamReader(stream)); 
      BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tempFile)));
           
      String line = null;
      while ((line = br.readLine())!=null)
      {
        out.write(line+"\n");
      }
      out.close();
    }
    catch(Exception e)
    {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
    return new HTTPPropertyResponse(tempFile);
  }

}
