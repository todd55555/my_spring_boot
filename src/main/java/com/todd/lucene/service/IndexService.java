package com.todd.lucene.service;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.todd.lucene.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;


@Service
@Slf4j
public class IndexService {


    @Autowired
    private UserService userService;

    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    /**
     * 获取用户文档
     * @return
     */
    public List<Document> getUserDocuments(){
        List<User> userList = userService.getAllUsers();
        log.info("获取用户信息：{}", JSON.toJSONString(userList));
        return buildUserDocument(userList);
    }

    /**
     * 构建用户文档
     * @param userList
     * @return
     */
    private List<Document> buildUserDocument(List<User> userList) {
        if(CollectionUtils.isEmpty(userList)){
            return Lists.newArrayList();
        }
        List<Document> results = Lists.newArrayListWithCapacity(userList.size());
        userList.stream().forEach(user -> {
            Document document = new Document();
            Field idField = new LongField("id",user.getId(), Field.Store.YES);
            Field nameField = new TextField("name", user.getName(), Field.Store.YES);
            Field sexField = new IntField("sex", user.getSex(), Field.Store.YES);
            Field mobileField = new TextField("mobile", user.getMobile(), Field.Store.NO);

            document.add(idField);
            document.add(nameField);
            document.add(sexField);
            document.add(mobileField);
            log.info("document field info: {}", JSON.toJSONString(document.getFields()));
            results.add(document);
        });
        log.info("构建的文档list: {}", JSON.toJSONString(results));
        return results;
    }


    /**
     * 创建文档索引
     * @param documentList
     */
    public void creatIndex(List<Document> documentList){

//        创建分析对象
        Analyzer analyzer = new StandardAnalyzer();

//        创建目录（索引存放位置）
        File file = new File("src/main/resources/static/indexDir/");
        log.info("索引存放位置：{}", file.getPath());
        try {
            Directory directory = FSDirectory.open(file);
//            获取创建IndexWriteConfig对象
            IndexWriterConfig config = new IndexWriterConfig(Version.LATEST,analyzer);
            IndexWriter indexWriter = new IndexWriter(directory,config);
            indexWriter.addDocuments(documentList);
            indexWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("创建索引完成");
    }

//    根据索引，搜索对象

    public List<User> searchUser(String name) {

//        创建分析器
        Analyzer analyzer = new StandardAnalyzer();

//        创建查询解释器
        QueryParser queryParser = new QueryParser("name",analyzer);
//        创建查询对象
        Query query = null;
        List<User> results = Lists.newArrayList();
        try {
            query = queryParser.parse("name:"+name);

    //        读取索引库
            File path = new File("src/main/resources/static/indexDir/");
            Directory directory = FSDirectory.open(path);

    //        读取索引
            IndexReader indexReader = DirectoryReader.open(directory);

    //        索引查询
            IndexSearcher searcher = new IndexSearcher(indexReader);

    //        查询
            TopDocs docs = searcher.search(query,10);

    //        获取返回记录
            ScoreDoc[] scoreDocs = docs.scoreDocs;
            if(scoreDocs == null || scoreDocs.length == 0){
                return results;
            }
            Arrays.stream(scoreDocs).forEach(scoreDoc -> {
                int docId = scoreDoc.doc;
                try {
                    Document document = searcher.doc(docId);
                    User user = new User();
                    user.setId(Long.valueOf(document.get("id")));
                    user.setName(document.get("name"));
                    user.setSex(Integer.valueOf(document.get("sex")));
                    user.setMobile(document.get("mobile"));
                    results.add(user);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

}
