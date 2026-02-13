package ru.mkvhlv.featuretoggleservice.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FeatureDto {

    private String featureKey;
    private boolean isEnabled;
}
