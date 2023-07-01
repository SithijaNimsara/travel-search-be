package com.sithija.travelsearch.controller;

import com.sithija.travelsearch.dto.CreatePostDto;
import com.sithija.travelsearch.dto.GalleryInforDto;
import com.sithija.travelsearch.dto.PostInforDto;
import com.sithija.travelsearch.error.HttpExceptionResponse;
import com.sithija.travelsearch.repository.PostRepository;
import com.sithija.travelsearch.service.GalleryService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static javax.servlet.http.HttpServletResponse.*;
import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

@RestController
public class GalleryController {

    @Autowired
    GalleryService galleryService;

    public GalleryController(GalleryService galleryService) {
        this.galleryService = galleryService;
    }

    @PostMapping(value = "/add-gallery-image/{hotelId}", consumes = { MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE })
    @PreAuthorize("hasRole('BUSINESS')")
    @ApiOperation(value = "Create post", nickname = "createPostOperation")
    @ApiResponses({
            @ApiResponse(code = SC_BAD_REQUEST, message = "Bad request", response = HttpExceptionResponse.class),
            @ApiResponse(code = SC_UNAUTHORIZED, message = "Unauthorized", response = HttpExceptionResponse.class),
            @ApiResponse(code = SC_NOT_FOUND, message = "Unauthorized", response = HttpExceptionResponse.class),
            @ApiResponse(code = SC_INTERNAL_SERVER_ERROR, message = "Internal server error", response = HttpExceptionResponse.class)})
    public ResponseEntity createNewPost(
            @ApiParam(value = "Added image to gallery.") @RequestPart("image") MultipartFile image,
            @ApiParam(value = "Hotel id.", required = true) @PathVariable("hotelId") int hotelId) throws IOException {
        return galleryService.addImageByHotelId(image, hotelId);
    }

    @GetMapping(value = "/get-gallery-image")
    @PreAuthorize("hasRole('USER') or hasRole('BUSINESS')")
    @ApiOperation(value = "Get image by index", nickname = "getImageByIndex")
    @ApiResponses({
            @ApiResponse(code = SC_BAD_REQUEST, message = "Bad request", response = HttpExceptionResponse.class),
            @ApiResponse(code = SC_UNAUTHORIZED, message = "Unauthorized", response = HttpExceptionResponse.class),
            @ApiResponse(code = SC_NOT_FOUND, message = "Unauthorized", response = HttpExceptionResponse.class),
            @ApiResponse(code = SC_INTERNAL_SERVER_ERROR, message = "Internal server error", response = HttpExceptionResponse.class)})
    public ResponseEntity<GalleryInforDto> getGalleryImageByIndex(
            @ApiParam(value = "Hotel id.") @RequestParam(name = "hotelId") int hotelId,
            @ApiParam(value = "Selected index.") @RequestParam("index") int index) {
        return galleryService.getImageByIndex(hotelId, index);
    }
}
