/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import static com.demo.lucene.document.Calculation.FolderFileCount;
import static com.demo.lucene.document.Constants.CSV_DIR;
import core.constants.Constants;
import static core.constants.Constants.CSV_DIR;
import static core.constants.Constants.INDEX_DIR;
import static core.constants.Constants.READ;
import static core.constants.Constants.WRITE;
import core.read.ReadIndex;
import core.write.WriteIndex;
import java.util.*;

/**
 *
 * @author elahi
 */
public class Main implements  Constants{
    private static Boolean testFlag=false;
    private static Set<String> menu=new HashSet<String>();
    
    public static void main(String[] args) throws Exception {
        //FolderFileCount(CSV_DIR);
        
        menu.add(WRITE);
        menu.add(READ);
        
        if(menu.contains(WRITE))
           WriteIndex.writeIndex(CSV_DIR,INDEX_DIR,testFlag);
        if(menu.contains(READ))
          ReadIndex.readIndex("firstName", "Where");
    }
    
}