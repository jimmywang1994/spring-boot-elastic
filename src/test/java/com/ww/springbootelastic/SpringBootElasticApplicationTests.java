package com.ww.springbootelastic;

import com.ww.springbootelastic.bean.Article;
import com.ww.springbootelastic.bean.Book;
import com.ww.springbootelastic.repository.BookRepository;
import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootElasticApplicationTests {

    @Autowired
    JestClient jestClient;

    @Autowired
    BookRepository bookRepository;

    @Test
    public void test2() {
//        Book book=new Book();
//        book.setAuthor("刘德华");
//        book.setId(1);
//        book.setName("我要飞");
//        bookRepository.index(book);
        for (Book book : bookRepository.findBookByNameLike("飞")) {
            System.out.println(book);
        }
    }

    @Test
    public void contextLoads() {
        Article article = new Article();
        article.setId(1);
        article.setTitle("好笑");
        article.setAuthor("赵刚");
        article.setContent("这是一本书");
        //构建一个索引功能
        Index index = new Index.Builder(article).index("atguigu").type("news").build();
        try {
            jestClient.execute(index);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //测试搜索
    @Test
    public void search() {
        String json = "{\n" +
                "     \"query\" : {\n" +
                "        \"match\" : {\n" +
                "            \"content\" : \"书\"\n" +
                "        }\n" +
                "    }\n" +
                "}";
        Search build = new Search.Builder(json).addIndex("atguigu").addType("news").build();
        try {
            SearchResult result = jestClient.execute(build);
            System.out.println(result.getJsonString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
