package com.sithija.travelsearch.repository;

import com.sithija.travelsearch.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    @Query(value = "SELECT COUNT(*) FROM user_post WHERE post_id = :postId", nativeQuery = true)
    int countLikeByPostId(@Param("postId") int postId);

    @Query(value = "SELECT * FROM post WHERE hotel_id = :hotelId", nativeQuery = true)
    List<Post> findAllByHotelId(@Param("hotelId") int hotelId);

}
