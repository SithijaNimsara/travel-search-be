package com.sithija.travelsearch;

import com.sithija.travelsearch.controller.CommentController;
import com.sithija.travelsearch.controller.PostController;
import com.sithija.travelsearch.dto.*;
import com.sithija.travelsearch.entity.Post;
import com.sithija.travelsearch.entity.User;
import com.sithija.travelsearch.repository.PostRepository;
import com.sithija.travelsearch.repository.UserRepository;
import com.sithija.travelsearch.service.PostService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class PostServiceTests {

    private PostController postController;

    @Mock
    private PostService postService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PostRepository postRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        postController = new PostController(postService);
    }

    @Test
    public void testGetAllPost() {
        int userId = 1;
        User user = new User();
        user.setUserId(userId);

        PostDetailsDto postDetailsDto = new PostDetailsDto(1, "20% OFF this Weekend", new Timestamp(System.currentTimeMillis()), new byte[0]);
        HotelDetailsDto hotelDetailsDto = new HotelDetailsDto(1, "Hotel ABC", new byte[0]);
        LikeDetailsDto likeDetailsDto = new LikeDetailsDto(1, true);
        List<PostInforDto> postInforDtoList = new ArrayList<>();
        postInforDtoList.add(new PostInforDto(postDetailsDto, hotelDetailsDto, likeDetailsDto));

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(postService.getAllPost(Mockito.anyInt())).thenReturn(new ResponseEntity<>(postInforDtoList, HttpStatus.OK));

        ResponseEntity<List<PostInforDto>> listResponseEntity = postService.getAllPost(userId);
        Assertions.assertEquals(HttpStatus.OK, listResponseEntity.getStatusCode());
        Assertions.assertEquals(postInforDtoList, listResponseEntity.getBody());
    }

    @Test
    public void testSavePost() throws IOException {
        int userId = 1;
        User user = new User();
        user.setUserId(userId);
        MultipartFile multipartFile = new MockMultipartFile("image.jpg", new byte[0]);
        CreatePostDto createPostDto = new CreatePostDto(userId, "10% discount this Weekend");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(postService.savePost(any(MultipartFile.class), any(CreatePostDto.class))).thenReturn(ResponseEntity.status(HttpStatus.CREATED).build());

        ResponseEntity responseEntity = postService.savePost(multipartFile, createPostDto);
        Assertions.assertNotNull(postRepository.findById(userId));
        Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    public void testDeletePost() {
        int postId = 1;
        Post post = new Post();
        post.setPostId(postId);

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(postService.deletePost(postId)).thenReturn(ResponseEntity.status(HttpStatus.NO_CONTENT).build());

        ResponseEntity responseEntity = postService.deletePost(postId);
        Assertions.assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }

}
