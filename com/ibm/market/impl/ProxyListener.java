/*
 * Created on 16-Jun-2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.market.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author cwilkin
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ProxyListener {
  
  public static void main(String[] args) throws Exception 
  {
      byte [] byteArray = new byte[500];
    
      System.out.println("Wating for incoming connection");
      ServerSocket sock = new ServerSocket(8081);
      
      Socket in = sock.accept();
      in.getInputStream().read(byteArray);
      System.out.println(new String(byteArray));
      
      BufferedReader br = new BufferedReader(
          new InputStreamReader(
              in.getInputStream()));
      String inputLine;
      while ((inputLine = br.readLine()) != null) 
        System.out.println(inputLine);
    

  }
  

}
