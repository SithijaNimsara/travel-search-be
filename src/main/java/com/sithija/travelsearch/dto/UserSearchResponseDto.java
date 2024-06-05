package com.sithija.travelsearch.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Arrays;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSearchResponseDto {

    private int userId;

    private String name;

    private byte[] image;

    @Override
    public String toString() {
        return "UserSearchResponseDto{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", image=" + Arrays.toString(image) +
                '}';
    }
}
