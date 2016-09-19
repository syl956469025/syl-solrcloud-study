package syl.study.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Created by Think on 2016/9/19.
 */
public class IndexFileDemo {



    public static void main(String[] args) throws IOException{
        String docpath = "d://docpath";
        String indexpath = "d://indexpath";
        createIndex(docpath,indexpath);



    }


    public static void createIndex(String docpath,String indexpath) throws IOException{
        createIndex(docpath,indexpath,false);
    }

    /**
     * 创建索引
     * @param docpath
     * @param indexpath
     * @param createOrAppend
     * @throws IOException
     */
    public static void createIndex(String docpath,String indexpath,boolean createOrAppend)throws IOException{
        long start = System.currentTimeMillis();
        Directory indexDir = FSDirectory.open(Paths.get(indexpath, new String[0]));
        Path docDirPath = Paths.get(docpath, new String[0]);
        Analyzer analyzer = new StandardAnalyzer();
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        if (createOrAppend){
            config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        }else{
            config.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
        }
        IndexWriter writer = new IndexWriter(indexDir,config);
        indexDocs(writer,docDirPath);
        writer.close();
        long end = System.currentTimeMillis();
        System.out.println("time consumed " + (end - start) + "ms");

    }


    /**
     * 写入索引
     * @param writer
     * @param docpath
     */
    public static void indexDocs(IndexWriter writer,Path docpath) throws IOException{
        if (Files.isDirectory(docpath)){
            System.out.println("directory");
            Files.walkFileTree(docpath,new SimpleFileVisitor<Path>(){
            @Override
            public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {
                System.out.println(path.getFileName());
                indexDoc(writer, path, attrs.lastModifiedTime().toMillis());
                return FileVisitResult.CONTINUE;
            }
            });
        }else{
            indexDoc(writer,docpath,Files.getLastModifiedTime(docpath,new LinkOption[0]).toMillis());
        }
    }

    /**
     * 读取文件创建索引
     * @param writer
     * @param file
     * @param lastModified
     * @throws IOException
     */
    public static void indexDoc(IndexWriter writer , Path file , long lastModified) throws IOException{
        InputStream stream = Files.newInputStream(file,new OpenOption[0]);
        Document doc = new Document();
        StringField path = new StringField("path", file.toString(), Field.Store.YES);
//        new LongRangeField("modified", lastModified, Field.Store.NO);
        doc.add(path);
        doc.add(new StoredField("lastModified",lastModified));
        doc.add(new TextField("contents",new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))));
        if (writer.getConfig().getOpenMode() == IndexWriterConfig.OpenMode.CREATE){
            System.out.println("adding" + file);
            writer.addDocument(doc);
        }else{
            System.out.println("update" + file);
            writer.updateDocument(new Term("path",file.toString()),doc);
        }
        writer.commit();
    }
}
