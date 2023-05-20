package com.sithija.travelsearch.repository;

import com.sithija.travelsearch.entity.Gallery;
import com.sithija.travelsearch.entity.Post;
import com.sithija.travelsearch.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GalleryRepository extends JpaRepository<Gallery, Integer> {
    Page<Gallery> findByUserId(User userId, Pageable pageable);
}
