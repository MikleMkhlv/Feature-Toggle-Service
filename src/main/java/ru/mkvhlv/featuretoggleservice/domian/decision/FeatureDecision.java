package ru.mkvhlv.featuretoggleservice.domian.decision;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class FeatureDecision {

    private String featureKey;
    private final Boolean isEnabled;


//    public String getId() {
//        return featureKey;
//    }
//
//    public void setId(String featureKey) {
//        this.featureKey = featureKey;
//    }
//
//    public Boolean getIsEnabled() {
//        return isEnabled;
//    }
//
//    public void setIsEnabled(Boolean isEnabled) {
//        this.isEnabled = isEnabled;
//    }
}
