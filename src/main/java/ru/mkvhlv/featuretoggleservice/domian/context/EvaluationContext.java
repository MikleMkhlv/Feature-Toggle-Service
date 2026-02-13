package ru.mkvhlv.featuretoggleservice.domian.context;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EvaluationContext {

    private String featureKey;
    private String role;
    private String userId;
}
