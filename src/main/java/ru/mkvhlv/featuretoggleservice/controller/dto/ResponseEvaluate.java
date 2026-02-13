package ru.mkvhlv.featuretoggleservice.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResponseEvaluate {

    private String featureKey;
    private boolean isEnabled;
}
