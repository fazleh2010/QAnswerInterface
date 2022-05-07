/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import com.demo.lucene.document.Constants;
import com.demo.lucene.document.ReadIndex;
import com.demo.lucene.document.WriteIndex;
import static com.demo.lucene.document.Constants.CSV_DIR;

/**
 *
 * @author elahi
 */
public class Main implements  Constants{
    
    public static void main(String[] args) throws Exception {
        WriteIndex.writeIndex(CSV_DIR);
        ReadIndex.readIndex("firstName", "Cent");
    }
    
}
