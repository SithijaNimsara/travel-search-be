package com.sithija.travelsearch;

import com.sithija.travelsearch.controller.CommentController;
import com.sithija.travelsearch.controller.LikeController;
import com.sithija.travelsearch.dto.LikeRequestDto;
import com.sithija.travelsearch.dto.SendCommentDto;
import com.sithija.travelsearch.entity.Comment;
import com.sithija.travelsearch.entity.Post;
import com.sithija.travelsearch.entity.User;
import com.sithija.travelsearch.repository.PostRepository;
import com.sithija.travelsearch.repository.UserRepository;
import com.sithija.travelsearch.service.LikeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class LikeServiceTests {

    private LikeController likeController;

    @Mock
    private LikeService likeService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PostRepository postRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        likeController = new LikeController(likeService);  // Pass the mocked service to the controller
    }

    @Test
    public void testAddLike() {
        int userId = 1;
        int postId = 1;
        LikeRequestDto likeRequestDto = new LikeRequestDto(userId, postId);

        User user = new User();
        user.setUserId(userId);
        Post post = new Post();
        post.setPostId(postId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(likeService.addLike(any(LikeRequestDto.class))).thenReturn(ResponseEntity.status(HttpStatus.CREATED).build());

        ResponseEntity responseEntity = likeService.addLike(likeRequestDto);

        Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

}
