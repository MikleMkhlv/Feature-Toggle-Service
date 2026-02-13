package ru.mkvhlv.featuretoggleservice.service;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.mkvhlv.featuretoggleservice.domian.Feature;
import ru.mkvhlv.featuretoggleservice.domian.decision.FeatureDecision;
import ru.mkvhlv.featuretoggleservice.dto.FeatureDto;
import ru.mkvhlv.featuretoggleservice.exceptions.FeatureAlreadyExistsException;
import ru.mkvhlv.featuretoggleservice.exceptions.FeatureNotFoundException;
import ru.mkvhlv.featuretoggleservice.repository.FeatureRepository;

import java.util.Optional;

@Service
public class FeatureServiceImpl implements FeatureService<FeatureDto, String> {

    private final ModelMapper modelMapper;
    private final FeatureRepository featureRepository;

    public FeatureServiceImpl(FeatureRepository featureRepository, ModelMapper modelMapper) {
        this.featureRepository = featureRepository;
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

    public FeatureDecision evaluate(String id) {
        Feature feature = featureRepository.findFeatureByFeatureKey(id).orElseThrow(() -> new FeatureNotFoundException("feature is not found"));

        Boolean decision = feature.getIsEnabled();

        return new FeatureDecision(id, decision);
    }
}
