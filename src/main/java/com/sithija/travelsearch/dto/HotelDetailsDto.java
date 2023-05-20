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
@ApiModel(value="PostDetailsDto")
public class HotelDetailsDto {

    @ApiModelProperty(value = "Post's id", dataType = "int")
    private int hotelId;

    @ApiModelProperty(value = "Post's caption", dataType = "String")
    private String name;

    @ApiModelProperty(value = "Post's image")
    private byte[] hotelImage;
}
