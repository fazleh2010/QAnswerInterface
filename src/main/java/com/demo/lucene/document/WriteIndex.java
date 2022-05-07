/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.demo.lucene.document;

import static com.demo.lucene.document.Constants.INDEX_DIR;
import com.howtodoinjava.uio.CsvFile;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

/**
 *
 * @author elahi
 */
public class WriteIndex implements Constants{

    public static void writeIndex(String CSV_DIR) throws Exception {
        File folder = new File(CSV_DIR);
        File[] listOfFiles = folder.listFiles();
        IndexWriter writer = createWriter();
        List<Document> documents = new ArrayList<Document>();

      
        long sum = 0;

        for (File file : listOfFiles) {
            if (file.isFile()) {
                CsvFile csvFile = new CsvFile();
                String fileName = CSV_DIR + file.getName();
                //List<String[]> rows = csvFile.getRows(new File(fileName));
                List<String[]> rows = csvFile.getManualRow(new File(fileName), 1000000000);
                //long lines = countLineOfFile(fileName);
                //System.out.println(file.getName() + lines);
                Integer index = 0;
                for (String[] row : rows) {
                    index = index + 1;
                    Boolean flag = false;
                    String id = null, question = null, sparql = null, answer = null;
                    try {
                        id = row[0].replace("\"", "");
                        question = row[1].replace("\"", "");
                        sparql = row[2].replace("\"", "");
                        answer = row[3].replace("\"", "");
                        flag = true;
                    } catch (Exception ex) {

                    }
                    if (flag) {
                        System.out.println(index + " " + question + " " + sparql + " " + answer + " " + file.getName());
                        Document document = createDocument(id, question, sparql, answer);
                        documents.add(document);
                    }
                    if (index > 1000) {
                        break;
                    }

                }

                if (index > 1000) {
                    break;
                }
            }

        }

        writer.deleteAll();

        writer.addDocuments(documents);
        writer.commit();
        writer.close();

    }
    
     public static Document createDocument(String id, String firstName, String lastName, String website) {
        Document document = new Document();
        document.add(new StringField("id", id, Field.Store.YES));
        document.add(new TextField("firstName", firstName, Field.Store.YES));
        document.add(new TextField("lastName", lastName, Field.Store.YES));
        document.add(new TextField("website", website, Field.Store.YES));
        return document;
    }

    public static IndexWriter createWriter() throws IOException {
        FSDirectory dir = FSDirectory.open(Paths.get(INDEX_DIR));
        IndexWriterConfig config = new IndexWriterConfig(new StandardAnalyzer());
        IndexWriter writer = new IndexWriter(dir, config);
        return writer;
    }
    



}
