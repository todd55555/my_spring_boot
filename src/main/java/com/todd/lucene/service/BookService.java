package com.todd.lucene.service;

import com.todd.lucene.entity.Book;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookService {


    /**
     * 根据ID获取book
     * @param bookId
     * @return
     */
    public Book getBookById(Long bookId);


    /**
     * 获取所有书本
     * @return
     */
    public List<Book> getAllBook();



}
