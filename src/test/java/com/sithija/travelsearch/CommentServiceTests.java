package com.sithija.travelsearch;

import com.sithija.travelsearch.controller.CommentController;
import com.sithija.travelsearch.dto.CommentInforDto;
import com.sithija.travelsearch.dto.SendCommentDto;
import com.sithija.travelsearch.entity.Comment;
import com.sithija.travelsearch.entity.Post;
import com.sithija.travelsearch.entity.User;
import com.sithija.travelsearch.repository.CommentRepository;
import com.sithija.travelsearch.repository.PostRepository;
import com.sithija.travelsearch.repository.UserRepository;
import com.sithija.travelsearch.service.CommentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

//@ExtendWith(SpringExtension.class)
//@WebMvcTest(value = TravelSearchApplication.class)
//@WebAppConfiguration
//@SpringBootTest
class CommentServiceTests {

    @Mock
    private CommentService commentService;

    private CommentController commentController;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private CommentRepository commentRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        commentController = new CommentController(commentService);  // Pass the mocked service to the controller
    }

    @Test
    public void testGetCommentByPostId() {
        int postId = 1;
        List<CommentInforDto> commentInforDtoList = new ArrayList<>();
        commentInforDtoList.add(new CommentInforDto(1, "John", "Great post!", new Timestamp(System.currentTimeMillis()), new byte[0]));
        when(commentService.getCommentByPostId(postId)).thenReturn(new ResponseEntity<>(commentInforDtoList, HttpStatus.OK));

        ResponseEntity<List<CommentInforDto>> response = commentService.getCommentByPostId(postId);

        verify(commentService, times(1)).getCommentByPostId(postId);  // Ensure the service method was called once
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());  // Verify the response status code
        Assertions.assertEquals(commentInforDtoList, response.getBody());  // Verify the response body
    }

    @Test
    public void testSendComment() {
        int userId = 1;
        int postId = 1;
        int commentId = 1;
        String commentText = "Great post!";
        SendCommentDto sendCommentDto = new SendCommentDto(userId, postId, commentText);

        User user = new User();
        user.setUserId(userId);
        Post post = new Post();
        post.setPostId(postId);
        Comment comment = new Comment();
        comment.setCommentId(commentId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(commentService.sendComment(any(SendCommentDto.class))).thenReturn(ResponseEntity.status(HttpStatus.CREATED).build());

        ResponseEntity<Comment> responseEntity = commentService.sendComment(sendCommentDto);
        Assertions.assertNotNull(commentRepository.findById(commentId));
        Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

//    @Test()
//    public void testSendComment_UserNotFound() {
//        SendCommentDto sendCommentDto = new SendCommentDto();
//        sendCommentDto.setUserId(1);
//        sendCommentDto.setPostId(1);
//        sendCommentDto.setComment("Test comment");
//
//        when(userRepository.findById(1)).thenReturn(Optional.empty());
//
//        commentService.sendComment(sendCommentDto);
//    }
//
//    @Test()
//    public void testSendComment_PostNotFound() {
//        SendCommentDto sendCommentDto = new SendCommentDto();
//        sendCommentDto.setUserId(1);
//        sendCommentDto.setPostId(1);
//        sendCommentDto.setComment("Test comment");
//
//        User user = new User();
//        user.setUserId(1);
//
//        when(userRepository.findById(1)).thenReturn(Optional.of(user));
//        when(postRepository.findById(1)).thenReturn(Optional.empty());
//
//        commentService.sendComment(sendCommentDto);
//    }


}
