package com.sithija.travelsearch.service;

import com.sithija.travelsearch.dto.*;
import com.sithija.travelsearch.entity.Comment;
import com.sithija.travelsearch.entity.Post;
import com.sithija.travelsearch.entity.User;
import com.sithija.travelsearch.repository.PostRepository;
import com.sithija.travelsearch.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    public ResponseEntity<List<PostInforDto>> getAllPost(int userId) {
        String role = userRepository.getRoleById(userId);
        System.out.println("role "+role+" id "+userId);
        List<Post> post=null;
        if(Objects.equals(role, "BUSINESS")) {
            post = postRepository.findAllByHotelId(userId);
        }else if(Objects.equals(role, "USER")) {
            post = postRepository.findAll();
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
//        List<Post> post = postRepository.findAll();

        List<PostInforDto> postInforDtos = post.stream().map(element -> {
            PostDetailsDto postDetailsDto = PostDetailsDto.builder()
                    .postId(element.getPostId())
                    .caption(element.getCaption())
                    .time(element.getTime())
                    .postImage(element.getImage())
                    .build();

            HotelDetailsDto hotelDetailsDto = HotelDetailsDto.builder()
                    .hotelId(element.getHotelId().getUserId())
                    .name(element.getHotelId().getName())
                    .hotelImage(element.getHotelId().getImage())
                    .build();


            int likeCount = postRepository.countLikeByPostId(element.getPostId());
            BigInteger isLike = userRepository.checkLikeByUserIdAndPostId(userId, element.getPostId());



            LikeDetailsDto likeDetailsDto = LikeDetailsDto.builder()
                    .likeCount(likeCount)
                    .liked((isLike.compareTo(BigInteger.valueOf(0)) > 0))
                    .build();

            PostInforDto postInforDto = PostInforDto.builder()
                    .postDetailsDto(postDetailsDto)
                    .hotelDetailsDto(hotelDetailsDto)
                    .likeDetailsDto(likeDetailsDto)
                    .build();
            return postInforDto;
        }).collect(Collectors.toList());


        return new ResponseEntity<>(postInforDtos, HttpStatus.OK);
    }

    public ResponseEntity savePost(MultipartFile image, CreatePostDto createPostDto) throws IOException {


        User user = userRepository.findById(createPostDto.getHotelId())
                .orElseThrow(() -> new IllegalArgumentException("Hotel not found"));

        Post post = Post.builder()
                        .caption(createPostDto.getCaption())
                        .image(image.getBytes())
                        .hotelId(user)
                        .build();
        postRepository.save(post);
        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    public ResponseEntity deletePost(int postId){

        Post post = postRepository.findById(postId).orElse(null);

        if(post != null) {
            postRepository.deleteById(postId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

}
