package ru.mkvhlv.featuretoggleservice.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.mkvhlv.featuretoggleservice.domian.decision.DecisionReason;

@Getter
@Setter
@AllArgsConstructor
public class ResponseEvaluate {

    private String featureKey;
    private boolean isEnabled;
    private DecisionReason decisionReason;
}
