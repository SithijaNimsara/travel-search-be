package com.sithija.travelsearch.repository;

import com.sithija.travelsearch.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByName(String name);

    Optional<User> findByEmail(String email);

    @Query(value = "SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END FROM user_post WHERE user_id = :userId AND post_id = :postId", nativeQuery = true)
    BigInteger checkLikeByUserIdAndPostId(@Param("userId") int userId, @Param("postId") int postId);

    @Query(value = "SELECT u.role from User u WHERE u.user_id = :userId", nativeQuery = true)
    String getRoleById(@Param("userId") int userId);

}
