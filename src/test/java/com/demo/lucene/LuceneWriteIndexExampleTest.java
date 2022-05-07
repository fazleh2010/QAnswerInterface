package com.demo.lucene;

import com.demo.lucene.document.Constants;
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

public class LuceneWriteIndexExampleTest implements Constants {


    public static void main(String[] args) throws Exception {
        IndexWriter writer = createWriter();
        List<Document> documents = new ArrayList<Document>();

        Document document1 = createDocument("1", "Lokesh", "Gupta", "howtodoinjava.com");
        documents.add(document1);

        Document document2 = createDocument("2", "Brian", "Schultz", "example.com");
        documents.add(document2);

        //Let's clean everything first
        writer.deleteAll();

        writer.addDocuments(documents);
        writer.commit();
        writer.close();
        
         readIndex();
    }
    
      public static void writeIndex() throws Exception {
        LuceneWriteIndexExampleTest luceneWriteIndexExample = new LuceneWriteIndexExampleTest();

        IndexWriter writer = luceneWriteIndexExample.createWriter();
        List<Document> documents = new ArrayList<Document>();

        Document document1 = luceneWriteIndexExample.createDocument("1", "Lokesh", "Gupta", "howtodoinjava.com");
        documents.add(document1);

        Document document2 = luceneWriteIndexExample.createDocument("2", "Lokesh Brian", "Schultz", "example.com");
        documents.add(document2);
         
        Document document3 = luceneWriteIndexExample.createDocument("3", "Gupta Brian", "Schultz", "example.com");
        documents.add(document3);

        //Let's clean everything first
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
    
    
    private static TopDocs searchByFirstName(String firstName, IndexSearcher searcher) throws Exception {
        QueryParser qp = new QueryParser("firstName", new StandardAnalyzer());
        Query firstNameQuery = qp.parse(firstName);
        TopDocs hits = searcher.search(firstNameQuery, 10);
        return hits;
    }

    private static TopDocs searchById(Integer id, IndexSearcher searcher) throws Exception {
        QueryParser qp = new QueryParser("id", new StandardAnalyzer());
        Query idQuery = qp.parse(id.toString());
        TopDocs hits = searcher.search(idQuery, 10);
        return hits;
    }

    private static IndexSearcher createSearcher() throws IOException {
        Directory dir = FSDirectory.open(Paths.get(INDEX_DIR));
        IndexReader reader = DirectoryReader.open(dir);
        IndexSearcher searcher = new IndexSearcher(reader);
        return searcher;
    }
    
     private static void readIndex() throws Exception {
        IndexSearcher searcher = createSearcher();

        //Search by ID
        TopDocs foundDocs = searchById(1, searcher);

        System.out.println("Toral Results :: " + foundDocs.totalHits);

        for (ScoreDoc sd : foundDocs.scoreDocs) {
            Document d = searcher.doc(sd.doc);
            System.out.println(String.format(d.get("id")));
        }

        //Search by firstName
        TopDocs foundDocs2 = searchByFirstName("Brian", searcher);

        System.out.println("Toral Results :: " + foundDocs2.totalHits);

        for (ScoreDoc sd : foundDocs2.scoreDocs) {
            Document d = searcher.doc(sd.doc);
            System.out.println(String.format(d.get("firstName")));
        }
    }
}
