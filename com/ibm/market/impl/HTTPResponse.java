/*
 * Created on 03-Nov-04
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.market.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author cwilkin
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class HTTPResponse
{
  File file;
  String fileName;
  /**
   * @param tempFile
   */
  public HTTPResponse(String tempFile)
  {
    file = new File(tempFile);
    fileName = tempFile;
    
    System.out.println("Processing "+fileName);
  }
  
  public String getLine(String searchString)
  {
    String line = null;
    try
    {
      BufferedReader br = 
        new BufferedReader(new InputStreamReader(new FileInputStream(file)));
      
      line = findLine(br, searchString);
      
      br.close();       
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }  
      
    return line;
  }
  
  public List getAllLines(String searchString)
  {
    ArrayList lines = null;
    String line = null;
    
    try
    {
      BufferedReader br = 
        new BufferedReader(new InputStreamReader(new FileInputStream(file)));
   
      do
      {
        line = findLine(br, searchString);
        if (line != null)
        {
          if(lines == null) lines = new ArrayList();
          lines.add(line);
        }
      } while (line != null);
      
      br.close();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return lines;
  }
  
  public List getAllNextLines(String searchString)
  {
    ArrayList lines = null;
    String line = null;
    
    try
    {
      BufferedReader br = 
        new BufferedReader(new InputStreamReader(new FileInputStream(file)));
   
      do
      {
        line = findLine(br, searchString);
        if (line != null && (line = br.readLine()) != null)
        {
          if(lines == null) lines = new ArrayList();
          lines.add(line);
        }
      } while (line != null);
      
      br.close();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return lines;
  }
  
  private String findLine(BufferedReader br, String searchString) throws Exception
  {
    String line = null;
    while ((line = br.readLine())!=null)
    {
      if (line.indexOf(searchString)>=0)
        break;
    }
    return line;
  }
  
  public String getProperty(String lineIdentifier, String property)
  {
    return getProperty(lineIdentifier, property, ' ');
  }
  
  public String getProperty(String lineIdentifier, String property, char ch)
  {
	return new XMLString(getLine(lineIdentifier)).getProperty(property, ch);
  }

  /**
   * 
   */
  public int getNextPage()
  {
    int nextPage = 0;
    List lines = getAllLines(">next<");
    if(lines != null)
    {
      String page = new XMLString((String)lines.get(0)).getProperty("pa_n", '&');
      if (page != null)
        nextPage = Integer.parseInt(page);  
    }

    return nextPage;
  }

  public String getFileName() {
    return fileName;
  }
}
