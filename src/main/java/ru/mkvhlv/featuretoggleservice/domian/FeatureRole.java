package ru.mkvhlv.featuretoggleservice.domian;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.mkvhlv.featuretoggleservice.rule.RoleType;

@Entity
@Table(name = "feature_role")
@Getter
@Setter
public class FeatureRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "feature_key", referencedColumnName = "feature_key")
    private Feature feature;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private RoleType type;

    @Column(name = "value")
    private String value;

    @Column(name = "priority")
    private Integer priority;
}
