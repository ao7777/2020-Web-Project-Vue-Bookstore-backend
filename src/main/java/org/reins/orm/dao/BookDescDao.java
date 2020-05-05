package org.reins.orm.dao;

import org.reins.orm.entity.BookDescEntity;

public interface BookDescDao {
    BookDescEntity getBookInfo(String isbn);
}
