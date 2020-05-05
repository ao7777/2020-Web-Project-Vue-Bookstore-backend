package org.reins.orm.dao.impl;

import org.reins.orm.dao.BookDescDao;
import org.reins.orm.entity.BookDescEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class BookDescDaoImpl implements BookDescDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public BookDescEntity getBookInfo(String isbn) {
        return entityManager.find(BookDescEntity.class,isbn);
    }
}
