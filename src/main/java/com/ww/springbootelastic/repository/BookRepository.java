package com.ww.springbootelastic.repository;

import com.ww.springbootelastic.bean.Book;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface BookRepository extends ElasticsearchRepository<Book,Integer> {
    List<Book> findBookByNameLike(String bookName);
}
