/*
 * Created on 27-Nov-05
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.market.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * @author cwilkin
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ConfigFile {
  
  private BufferedWriter out;
  private BufferedReader in;
  private boolean closed = true;
  String filename = null;
  
  public ConfigFile(String fileName) {
   this.filename = fileName; 
  }

  /* (non-Javadoc)
   * @see java.io.Writer#write(java.lang.String)
   */
  public void write(String output) {
    
    try {
      if(closed)
      {
        out = new BufferedWriter(
          new OutputStreamWriter(
            new FileOutputStream(filename, true)));
        closed = false;
      }
                
      out.write(output+"\n");
    } catch (IOException e) {
      System.out.println("Could not write to keyword file");
      e.printStackTrace();
    }
  }

  /* (non-Javadoc)
   * @see java.io.Writer#close()
   */
  public String read() {
    String input = null;
    try {
      if (closed)
      {
        in = new BufferedReader(
          new InputStreamReader(
            new FileInputStream(filename)));
        closed = false;
      }
          
      input = in.readLine();
    } catch (IOException e) {
      System.out.println("Could not read from keyword file");
      e.printStackTrace();
    }
    return input;
  }

  public void close()
  {
    try
    {
      if (in != null) in.close();
      if (out!= null) out.close();
      closed = true;
    }
    catch (IOException e)
    {
      System.out.println("Could not close keyword file");
      e.printStackTrace();
    }
  }
}
