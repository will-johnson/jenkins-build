package com.seen.library.service;

import com.seen.library.dao.BookDao;
import com.seen.library.entity.Book;
import com.seen.library.entity.enums.BookStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookDao bookDao;

    public List<Book> getAllBooks(){
        return bookDao.selectAll();
    }

    public int addBook(Book book){
        return bookDao.addBook(book);
    }

    public void deleteBook(int id){
        bookDao.updateBookStatus(id, BookStatusEnum.DELETE.getValue());
    }

    public void recoverBook(int id){
        bookDao.updateBookStatus(id, BookStatusEnum.NORMAL.getValue());
    }

}
