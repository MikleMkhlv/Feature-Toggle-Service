package ru.mkvhlv.featuretoggleservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mkvhlv.featuretoggleservice.domian.Feature;

import java.util.Optional;

public interface FeatureRepository extends JpaRepository<Feature, Long> {
    Optional<Feature> findFeatureByFeatureKey(String key);

    void deleteByFeatureKey(String featureKey);

    boolean existsByFeatureKey(String featureKey);
}
