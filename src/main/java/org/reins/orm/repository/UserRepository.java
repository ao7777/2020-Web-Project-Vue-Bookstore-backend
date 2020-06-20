package org.reins.orm.repository;

import org.reins.orm.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<UserEntity,Integer> {
    @Query("select u from UserEntity u where u.name=:username and u.pwd=:password")
    UserEntity checkLogin(@Param("username") String username,@Param("password") String password);
    @Query("select u from UserEntity u where u.name=:username")
    UserEntity findByName(@Param("username") String username);
}
