package ru.mkvhlv.featuretoggleservice.domian;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Primary;

@Entity

@Table(name = "feature")
public class Feature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Getter
    @Setter
    private long id;

    @Column(name = "feature_key", unique = true, nullable = false)
    @Getter
    @Setter
    private String featureKey;

    @Column(name = "is_enabled")
    private Boolean isEnabled;


    public Boolean getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }
}
