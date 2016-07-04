/*
 * Created on 12-Oct-04
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.market.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.Iterator;

import com.ibm.market.impl.MarketConstants;

/**
 * @author cwilkin
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Utils
{
  private static ReportFile reportFile;
  private static KeywordFile keywordFile;
  private static FavouritesFile favouritesFile;

  /**
   * @param string
   * @param string2
   * @param input
   * @return
   */
  public static String insert(String searchString, String newString, String input)
  {
    // Search for specific strings
    int index = 0;
    if((index = input.lastIndexOf(searchString)) > 0)
    {
      String replacement = 
        input.substring(0, index + searchString.length());
      replacement += "\"" + newString + "\"";
      return replacement;        
    }
    else 
      return input;
  }

  /**
   * @param command
   * @param inserts
   * @return
   */
  public static String substitute(String command, String[] inserts)
  {
    for(int i=1; i<=inserts.length; i++)
    {
      command = command.replaceAll("\\$"+i, inserts[i-1]);
    }
    return command;
  }

  /**
   * @param iterator
   * @return
   */
  public static String paramaterize(Iterator iterator)
  {
    String paramaters = "";
    while(iterator.hasNext())
    {
      paramaters+=(String)iterator.next()+" ";
    }
    return paramaters;
  }

  /**
   * @param string
   * @param controlFile
   */
  public static String replace(String input, String oldString, String newString)
  {
    input = input.replaceAll(oldString, newString);
    return input;
    
  }
  
  public static void log(String output)
  {
    output = "["+new Date()+"] "+ output;
    System.out.println(output);
  }
  
  public static boolean deleteDir(File dir) {
      if (dir.isDirectory()) {
          String[] children = dir.list();
          for (int i=0; i<children.length; i++) {
              boolean success = deleteDir(new File(dir, children[i]));
              if (!success) {
                  return false;
              }
          }
      }
    
      // The directory is now empty so delete it
      return dir.delete();
  }
  
  public static Config readProperties(String[] args)
  {
    Config props = new Config();
    String filename = null;    

    // Search for -f parameter
    for(int i=0; i<args.length; i++)
    {
      if(args[i].equals("-f"))
      {
        filename = args[i+1];
        break;
      }
    }
    
    if (filename == null)
    {
      int i = 0;
      while (i + 1 < args.length)
      {
        if (!args[i].startsWith("-"))
        {
          Utils.log("Argument does not begin with a '-' character");
        }
        
        props.put(args[i++], args[i++]);
      }
    }
    else
    {
      //Load properties file
      try
      {
        BufferedReader in = 
          new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
        String input = null;
        
        while ((input = in.readLine()) != null)
        {
          input = input.trim();
          String[] prop = input.split("=");
          if(prop != null && prop.length == 2)
            props.put("-"+prop[0], prop[1]);
          
        }
      }
      catch(Exception e)
      {
        throw new RuntimeException(e);
      }
    }    
    return props;    
  }

  /**
   * 
   */
  public static KeywordFile getKeywordFile() {
    
    if (keywordFile == null)
      keywordFile = new KeywordFile();
      
    return keywordFile;
  }
  
  /**
   * 
   */
  public static FavouritesFile getFavouritesFile() {
    
    if (favouritesFile == null)
      favouritesFile = new FavouritesFile();
      
    return favouritesFile;
  }
  
  /**
   * @param string
   */
  public static void setKeywordFile(String string) {
    keywordFile = new KeywordFile(string);
  }

  /**
   * @param string
   */
  public static void report(String output) {
    System.out.println(output);
    
    if (reportFile == null)
      reportFile = new ReportFile();
      
    reportFile.write(output);   
  }

  /**
   * 
   */
  public static void closeReport() {
    if (reportFile != null) reportFile.close();
  }

}
