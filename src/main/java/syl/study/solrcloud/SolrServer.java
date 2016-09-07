package syl.study.solrcloud;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.common.SolrInputDocument;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

/**
 * Created by Think on 2016/9/7.
 */
public class SolrServer {

    static String url = "";
    static String zkhost = "node1:2181,node2:2181,node3:2181";


    public static void main(String[] args) {
        CloudSolrClient cloud = new CloudSolrClient(zkhost);
        cloud.setDefaultCollection("SMovie");
        System.out.println("======================add doc ===================");
        Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
        for (int i = 1; i <= 20; i++) {
            SolrInputDocument doc1 = new SolrInputDocument();
            doc1.addField("id", UUID.randomUUID().toString(), 1.0f);
            doc1.addField("name", "bean");
//            doc1.addField("birthday", "2016-09-01");
//            doc1.addField("birthday", LocalDate.now());
            doc1.addField("age", 4);
            doc1.addField("isMan", true);
            docs.add(doc1);
        }
        try {
            cloud.add(docs);
            cloud.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
