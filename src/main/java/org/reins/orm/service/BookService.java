package org.reins.orm.service;

import org.reins.orm.dao.BookDao;
import org.reins.orm.dao.BookDescDao;
import org.reins.orm.entity.BookDescEntity;
import org.reins.orm.entity.BookEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class BookService {
    @Resource
    private BookDescDao bookDescDao;
    @Resource
    private BookDao bookDao;
    public BookDescEntity getBookInfoByISBN(String isbn){
        BookDescEntity descEntity=bookDescDao.getBookInfo(isbn);
        return descEntity;
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
}
