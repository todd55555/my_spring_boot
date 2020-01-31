package com.todd.lucene.service.impl;

import com.todd.lucene.dao.BookMapper;
import com.todd.lucene.entity.Book;
import com.todd.lucene.entity.BookExample;
import com.todd.lucene.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookMapper bookMapper;

    /**
     * 根据ID获取book
     * @param bookId
     * @return
     */
    @Override
    public Book getBookById(Long bookId){

        return bookMapper.selectByPrimaryKey(bookId);
    }

    @Override
    public List<Book> getAllBook() {
        List<Long> ids = new ArrayList<Long>();
        ids.add(1L);
        ids.add(2L);
        BookExample example = new BookExample();
        example.createCriteria().andBookIdIn(ids);
        return bookMapper.selectByExample(example);
    }


}
