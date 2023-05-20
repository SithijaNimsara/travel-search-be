package com.sithija.travelsearch.controller;

import com.sithija.travelsearch.dto.CommentInforDto;
import com.sithija.travelsearch.dto.LoginUserDto;
import com.sithija.travelsearch.dto.SendCommentDto;
import com.sithija.travelsearch.entity.Comment;
import com.sithija.travelsearch.error.HttpExceptionResponse;
import com.sithija.travelsearch.service.CommentService;
import com.sithija.travelsearch.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static javax.servlet.http.HttpServletResponse.*;
import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping(value = "/get-comment/{postId}")
    @PreAuthorize("hasRole('USER') or hasRole('BUSINESS')")
    @ApiOperation(value = "Get all post by it's ID", nickname = "getAllPostByIdOperation")
    @ApiResponses({
            @ApiResponse(code = SC_BAD_REQUEST, message = "Bad request", response = HttpExceptionResponse.class),
            @ApiResponse(code = SC_UNAUTHORIZED, message = "Unauthorized", response = HttpExceptionResponse.class),
            @ApiResponse(code = SC_NOT_FOUND, message = "Unauthorized", response = HttpExceptionResponse.class),
            @ApiResponse(code = SC_INTERNAL_SERVER_ERROR, message = "Internal server error", response = HttpExceptionResponse.class)})
    public ResponseEntity<List<CommentInforDto>> getAllCommentByPostId(
            @ApiParam(value = "Get comment by post ID.", required = true) @PathVariable("postId") int postId) {
        return commentService.getCommentByPostId(postId);
    }


    @PostMapping(value = "/sent-comment")
    @PreAuthorize("hasRole('USER')")
    @ApiOperation(value = "Send comment by it's user ID", nickname = "sendCommentOperation")
    @ApiResponses({
            @ApiResponse(code = SC_BAD_REQUEST, message = "Bad request", response = HttpExceptionResponse.class),
            @ApiResponse(code = SC_UNAUTHORIZED, message = "Unauthorized", response = HttpExceptionResponse.class),
            @ApiResponse(code = SC_NOT_FOUND, message = "Unauthorized", response = HttpExceptionResponse.class),
            @ApiResponse(code = SC_INTERNAL_SERVER_ERROR, message = "Internal server error", response = HttpExceptionResponse.class)})
    public ResponseEntity<Comment> sendCommentByUserId(@ApiParam(value = "User credentials") @RequestBody SendCommentDto sendCommentDto) {
        return commentService.sendComment(sendCommentDto);
    }

}
