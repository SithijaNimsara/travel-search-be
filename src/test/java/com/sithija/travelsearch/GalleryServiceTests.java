package com.sithija.travelsearch;

import com.sithija.travelsearch.controller.CommentController;
import com.sithija.travelsearch.controller.GalleryController;
import com.sithija.travelsearch.dto.GalleryInforDto;
import com.sithija.travelsearch.dto.SendCommentDto;
import com.sithija.travelsearch.entity.User;
import com.sithija.travelsearch.repository.UserRepository;
import com.sithija.travelsearch.service.GalleryService;
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
import java.util.Optional;

import static org.mockito.Mockito.when;

public class GalleryServiceTests {

    private GalleryController galleryController;

    @Mock
    private GalleryService galleryService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        galleryController = new GalleryController(galleryService);
    }

    @Test
    public void testGetImageByIndex() {
        int userId = 1;
        int index = 0;

        User user = new User();
        user.setUserId(userId);
        GalleryInforDto galleryInforDto = new  GalleryInforDto(1, new byte[0], userId, index, 2);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(galleryService.getImageByIndex(Mockito.anyInt(), Mockito.anyInt())).thenReturn(new ResponseEntity<>(galleryInforDto, HttpStatus.OK));

        ResponseEntity<GalleryInforDto> responseEntity = galleryService.getImageByIndex(userId, index);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertTrue(index< responseEntity.getBody().getTotalItem());
    }

    @Test
    public void testAddImageByHotelId() throws IOException {
        int userId = 1;
        MultipartFile multipartFile = new MockMultipartFile("image.jpg", new byte[0]);

        User user = new User();
        user.setUserId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(galleryService.addImageByHotelId(Mockito.any(MultipartFile.class), Mockito.anyInt())).thenReturn(ResponseEntity.status(HttpStatus.CREATED).build());
        ResponseEntity responseEntity = galleryService.addImageByHotelId(multipartFile, userId);

        Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

    }
}
