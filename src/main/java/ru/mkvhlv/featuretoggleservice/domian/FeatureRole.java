package ru.mkvhlv.featuretoggleservice.domian;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.mkvhlv.featuretoggleservice.domian.context.EvaluationContext;
import ru.mkvhlv.featuretoggleservice.domian.decision.DecisionReason;
import ru.mkvhlv.featuretoggleservice.domian.decision.FeatureDecision;
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

    public boolean matches(EvaluationContext context) {
        switch (this.type) {
            case ROLE -> {
                return context.getRole() != null && this.value.equals(context.getRole());
            }
            case PERCENTAGE -> {
                if (context.getUserId() == null) {
                    return false;
                }

                int percentage = Integer.parseInt(this.value);
                int bucket = (context.getUserId().hashCode() & Integer.MAX_VALUE) % 100;

                return bucket < percentage;
            }
            default -> {
                return false;
            }
        }
    }
}
