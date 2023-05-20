package com.sithija.travelsearch.error;

import com.sithija.travelsearch.dto.ErrorDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class HttpExceptionResponse {
    public final List<ErrorDto> errors;
}
