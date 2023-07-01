package com.sithija.travelsearch.service;

import com.sithija.travelsearch.dto.GalleryInforDto;
import com.sithija.travelsearch.dto.PostInforDto;
import com.sithija.travelsearch.entity.Gallery;
import com.sithija.travelsearch.entity.User;
import com.sithija.travelsearch.repository.GalleryRepository;
import com.sithija.travelsearch.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GalleryService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    GalleryRepository galleryRepository;

    GalleryInforDto galleryInforDto;


    public ResponseEntity<GalleryInforDto> getImageByIndex(int hotelId, int index) {

        try {
            Pageable pageable = PageRequest.of(index, 1);
            User user = userRepository.findById(hotelId)
                    .orElseThrow(() -> new IllegalArgumentException("Hotel ID not found"));
            Page<Gallery> galleryPage = galleryRepository.findByUserId(user, pageable);

            List<Gallery> galleryList = galleryPage.getContent();
            long totalElements = galleryPage.getTotalElements();

            if (totalElements>0 && totalElements>index) {
                galleryInforDto = GalleryInforDto.builder()
                        .galleryId(galleryList.get(0).getGalleryId())
                        .image(galleryList.get(0).getImage())
                        .hotelId(galleryList.get(0).getUserId().getUserId())
                        .currentPage(galleryPage.getNumber())
                        .totalItem(galleryPage.getTotalPages())
                        .build();
                return new ResponseEntity<>(galleryInforDto, HttpStatus.OK);
            }else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity addImageByHotelId(MultipartFile image, int hotelId) throws IOException {
        User user = userRepository.findById(hotelId)
                .orElseThrow(() -> new IllegalArgumentException("Hotel ID not found"));

        Gallery gallery = Gallery.builder()
                            .image(image.getBytes())
                            .userId(user)
                            .build();
        galleryRepository.save(gallery);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
