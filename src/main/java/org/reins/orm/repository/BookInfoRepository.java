package org.reins.orm.repository;

import org.reins.orm.entity.BookInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface BookInfoRepository extends MongoRepository<BookInfo,String> {
    @Query(value = "{'isbn':?0}")
    Optional<BookInfo> findByIsbn(Long isbn);
    void deleteBookInfoByIsbn(Long isbn);
}
