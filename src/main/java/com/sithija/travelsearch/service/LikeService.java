package com.sithija.travelsearch.service;

import com.sithija.travelsearch.dto.LikeRequestDto;
import com.sithija.travelsearch.entity.Post;
import com.sithija.travelsearch.entity.User;
import com.sithija.travelsearch.repository.PostRepository;
import com.sithija.travelsearch.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class LikeService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    public ResponseEntity addLike(LikeRequestDto likeRequestDto) {

        User user = userRepository.findById(likeRequestDto.getUserId()).orElse(null);
        Post post = postRepository.findById(likeRequestDto.getPostId()).orElse(null);

        if(post != null && Objects.requireNonNull(user).getRole().equals("USER")) {
            user.getUserPosts().add(post);
            userRepository.save(user);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }else {
            assert user != null;
            if (user.getRole().equals("BUSINESS")) {
                return new ResponseEntity<String>("Invalid User Role", HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<String>("Something went Wrong", HttpStatus.BAD_REQUEST);
    }


}
