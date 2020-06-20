package org.reins.orm.service;

import org.reins.orm.entity.BookDescEntity;
import org.reins.orm.entity.BookEntity;
import org.reins.orm.entity.BookInfo;

import java.util.List;

public interface BookService {
    BookInfo getBookInfoByISBN(Long isbn);
    List<BookEntity> getBooks();
    BookEntity getBookByISBN(String isbn);
    void updateBook(BookEntity bookEntity);
    void addBook(BookEntity bookEntity);
    void deleteBookByISBN(String isbn);
}
