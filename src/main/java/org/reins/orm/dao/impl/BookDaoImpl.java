package org.reins.orm.dao.impl;

import org.reins.orm.dao.BookDao;
import org.reins.orm.entity.BookEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class BookDaoImpl implements BookDao {
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List getBooks() {
        return entityManager.createQuery("select b from BookEntity b").getResultList();
    }

    @Override
    public void updateBook(BookEntity bookEntity) {
        BookEntity book=getBookByISBN(bookEntity.getIsbn());
        book.setAuthor(bookEntity.getAuthor());
        book.setName(bookEntity.getName());
        book.setPress(bookEntity.getPress());
        book.setPrice(bookEntity.getPrice());
        book.setPublishDate(bookEntity.getPublishDate());
        entityManager.flush();
    }

    @Override
    public BookEntity getBookByISBN(String isbn) {
        return entityManager.find(BookEntity.class,isbn);
    }
}
