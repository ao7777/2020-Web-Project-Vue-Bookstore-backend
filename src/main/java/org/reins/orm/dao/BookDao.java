package org.reins.orm.dao;

import org.reins.orm.entity.BookEntity;

import java.util.List;

public interface BookDao {
    List<BookEntity> getBooks();
    void updateBook(BookEntity bookEntity);
    BookEntity getBookByISBN(String isbn);
}
