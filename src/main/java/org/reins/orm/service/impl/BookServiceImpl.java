package org.reins.orm.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reins.orm.dao.BookDao;
import org.reins.orm.dao.BookDescDao;
import org.reins.orm.entity.BookDescEntity;
import org.reins.orm.entity.BookEntity;
import org.reins.orm.entity.BookInfo;
import org.reins.orm.service.BookService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    @Resource
    private BookDao bookDao;
    public BookInfo getBookInfoByISBN(Long isbn){
        return bookDao.getBookInfo(isbn);
    }
    public List<BookEntity> getBooks(){
        return bookDao.getBooks();
    }
    public BookEntity getBookByISBN(String isbn){
        return bookDao.getBookByISBN(isbn);
    }
    @Transactional
    public void updateBook(BookEntity bookEntity){
        bookDao.updateBook(bookEntity);
    }

    @Override
    @Transactional
    public void addBook(BookEntity bookEntity) {
        bookDao.addBook(bookEntity);
    }
    @Override
    @Transactional
    public void deleteBookByISBN(String isbn) {
        bookDao.deleteBookByISBN(isbn);
    }
}
