package ru.mkvhlv.featuretoggleservice.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseFeature<T> {

    private String status;
    private String responseMessage;
    @JsonIgnore
    private List<T> body;
}
