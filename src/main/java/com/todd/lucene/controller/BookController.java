package com.todd.lucene.controller;


import com.todd.lucene.service.BookService;
import com.todd.lucene.util.ReturnResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/com/book/")
public class BookController {

    @Autowired
    private BookService bookService;


    @RequestMapping(value = "getAllBook")
    public ReturnResult getAllBook(){
        ReturnResult result = new ReturnResult();
        result.setSuccess(Boolean.TRUE);
        result.setMsg("请求成功");
        result.setData(bookService.getAllBook());
        return result;
    }


    @RequestMapping(value = "getBookById/{bookId}")
    public ReturnResult getAllBook(@PathVariable Long bookId){
        ReturnResult result = new ReturnResult();
        result.setSuccess(Boolean.TRUE);
        result.setMsg("请求成功");
        result.setData(bookService.getBookById(bookId));
        return result;
    }
}
