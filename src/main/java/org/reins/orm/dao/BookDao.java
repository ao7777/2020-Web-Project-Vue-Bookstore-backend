package org.reins.orm.dao;

import org.reins.orm.entity.BookDescEntity;
import org.reins.orm.entity.BookEntity;
import org.reins.orm.entity.BookInfo;

import java.util.List;

public interface BookDao {
    List<BookEntity> getBooks();
    void updateBook(BookEntity bookEntity);
    BookEntity getBookByISBN(String isbn);
    BookInfo getBookInfo(Long isbn);
    void addBook(BookEntity bookEntity);
    void deleteBookByISBN(String isbn);
}
