package org.reins.orm.repository;

import org.reins.orm.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<BookEntity,String> {
}
