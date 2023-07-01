package com.sithija.travelsearch.service;

import com.sithija.travelsearch.dto.CommentInforDto;
import com.sithija.travelsearch.dto.SendCommentDto;
import com.sithija.travelsearch.entity.Comment;
import com.sithija.travelsearch.entity.Post;
import com.sithija.travelsearch.entity.User;
import com.sithija.travelsearch.repository.CommentRepository;
import com.sithija.travelsearch.repository.PostRepository;
import com.sithija.travelsearch.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    public ResponseEntity<List<CommentInforDto>> getCommentByPostId(int postId) {
        List<Comment> comment = commentRepository.findByPostId(postId);


        List<CommentInforDto> commentInforDtoList = comment.stream().map(element -> {
            CommentInforDto commentInforDto = CommentInforDto.builder()
                    .commentId(element.getCommentId())
                    .userName(element.getUserId().getName())
                    .comment(element.getComment())
                    .time(element.getTime())
                    .userImage(element.getUserId().getImage())
                    .build();
            return commentInforDto;
        }).collect(Collectors.toList());
        return new ResponseEntity<>(commentInforDtoList, HttpStatus.OK);
    }

    public ResponseEntity<Comment> sendComment(SendCommentDto sendCommentDto) {

        User user = userRepository.findById(sendCommentDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Post post = postRepository.findById(sendCommentDto.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        Comment comment = Comment.builder()
                .comment(sendCommentDto.getComment())
                .postId(post)
                .userId(user)
                .build();
        commentRepository.save(comment);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
