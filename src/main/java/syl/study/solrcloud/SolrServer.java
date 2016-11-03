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
    static String zkhost = "solr1:2181,solr2:2181,solr3:2181";


    public static void main(String[] args) {
        CloudSolrClient cloud = new CloudSolrClient.Builder().withZkHost(zkhost).build();
        cloud.setDefaultCollection("SBand");
        System.out.println("======================add doc ===================");
//        Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
//        for (int i = 1; i <= 20; i++) {
            SolrInputDocument doc1 = new SolrInputDocument();
            doc1.addField("id", UUID.randomUUID().toString(), 1.0f);
            doc1.addField("name", "bean");
            doc1.addField("age", 4);
            doc1.addField("isMan", true);

//            docs.add(doc1);
//        }
        Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
        for (int i = 1; i <= 2; i++) {
            SolrInputDocument doc2 = new SolrInputDocument();
            doc2.addField("id",1+i);
            doc2.addField("code", "1234");
            doc2.addField("codename", "qwer");
            docs.add(doc2);
        }
        doc1.addField("filmType",docs);

//        Member member = new Member();
//        member.setAge(21);
//        member.setId(1);
//        member.setUsername("testMember");
//        List<FilmType> likeFilmTypes = new ArrayList<FilmType>();
//        for (int i = 0; i < 3; i++) {
//            FilmType ft = new FilmType();
//            ft.setFilmTypeCode("dz");
//            ft.setFilmTypeName("动作片");
//            likeFilmTypes.add(ft);
//        }
        try {
            cloud.add(doc1);
            cloud.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
