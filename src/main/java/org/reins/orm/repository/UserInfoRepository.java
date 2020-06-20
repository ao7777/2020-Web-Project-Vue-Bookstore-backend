package org.reins.orm.repository;

import org.reins.orm.entity.UserInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface UserInfoRepository extends MongoRepository<UserInfo,String> {
    @Query(value = "{'user_id':?0}")
    Optional<UserInfo> findByUser_id(int user_id);
}
