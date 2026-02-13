package ru.mkvhlv.featuretoggleservice.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mkvhlv.featuretoggleservice.domian.Feature;
import ru.mkvhlv.featuretoggleservice.domian.FeatureRole;
import ru.mkvhlv.featuretoggleservice.domian.context.EvaluationContext;
import ru.mkvhlv.featuretoggleservice.domian.decision.DecisionReason;
import ru.mkvhlv.featuretoggleservice.domian.decision.FeatureDecision;
import ru.mkvhlv.featuretoggleservice.dto.FeatureDto;
import ru.mkvhlv.featuretoggleservice.exceptions.FeatureAlreadyExistsException;
import ru.mkvhlv.featuretoggleservice.exceptions.FeatureNotFoundException;
import ru.mkvhlv.featuretoggleservice.repository.FeatureRepository;
import ru.mkvhlv.featuretoggleservice.repository.FeatureRoleRepository;
import ru.mkvhlv.featuretoggleservice.rule.RoleType;
import ru.mkvhlv.featuretoggleservice.util.SortedFeature;

import java.util.List;

@Service
public class FeatureServiceImpl implements FeatureService<FeatureDto, String> {

    private final ModelMapper modelMapper;
    private final FeatureRepository featureRepository;
    private final FeatureRoleRepository featureRoleRepository;

    public FeatureServiceImpl(FeatureRepository featureRepository, FeatureRoleRepository featureRoleRepository, ModelMapper modelMapper) {
        this.featureRepository = featureRepository;
        this.featureRoleRepository = featureRoleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public FeatureDto getById(String idEntry) {
        return featureRepository.findFeatureByFeatureKey(idEntry)
                .map(feature -> modelMapper.map(feature, FeatureDto.class))
                .orElseThrow(() -> new FeatureNotFoundException("feature is not found"));

    }


    @Override
    public FeatureDto create(FeatureDto feature) {
        if(featureRepository.existsByFeatureKey(feature.getFeatureKey())) {
            throw new FeatureAlreadyExistsException("Feature with key " + feature.getFeatureKey() + " already exist");
        }

        Feature saveFeature = featureRepository.save(modelMapper.map(feature, Feature.class));
        return modelMapper.map(saveFeature, FeatureDto.class);
    }


    @Override
    public FeatureDto updateFeatureById(String id, FeatureDto feature) {
        if (id == null || feature == null) {
            throw new IllegalArgumentException("feature is not found or id");
        }

        Feature foundFeature = featureRepository.findFeatureByFeatureKey(id).orElseThrow(() -> new FeatureNotFoundException("feature is not found"));
        foundFeature.setIsEnabled(feature.isEnabled());

        Feature savedFeature = featureRepository.save(foundFeature);
        return modelMapper.map(savedFeature, FeatureDto.class);
    }

    @Override
    @Transactional
    public void deleteById(String id) {
        if (id == null) {
            throw new RuntimeException("id is null");
        }
        featureRepository.findFeatureByFeatureKey(id).orElseThrow(() -> new FeatureNotFoundException("feature is not found"));
        featureRepository.deleteByFeatureKey(id);
    }

    public FeatureDecision evaluate(EvaluationContext context) {
        Feature feature = featureRepository.findFeatureByFeatureKey(context.getFeatureKey()).orElseThrow(() -> new FeatureNotFoundException("feature is not found"));

        if (!feature.getIsEnabled()) {
            return new FeatureDecision(context.getFeatureKey(), false, DecisionReason.GLOBAL_DISABLED);
        }

        List<FeatureRole> roles = SortedFeature.quickSortRoleByPriority(featureRoleRepository.findFeatureRolesByFeature(feature));

        for (FeatureRole role : roles) {
            if (role.matches(context)) {
                DecisionReason reason = switch (role.getType()) {
                    case ROLE -> DecisionReason.ROLE_MATCH;
                    case PERCENTAGE -> DecisionReason.PERCENTAGE_MATCH;
                };

                return new FeatureDecision(
                        context.getFeatureKey(),
                        true,
                        reason
                );
            }
        }
        return new FeatureDecision(context.getFeatureKey(), false, DecisionReason.NO_MATCH);
    }
}
