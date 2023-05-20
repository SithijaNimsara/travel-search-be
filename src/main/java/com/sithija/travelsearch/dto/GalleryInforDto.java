package com.sithija.travelsearch.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="GalleryInforDto")
public class GalleryInforDto {

    private int galleryId;

    private byte[] image;

    private int hotelId;

    private int currentPage;

    private int totalItem;

}
