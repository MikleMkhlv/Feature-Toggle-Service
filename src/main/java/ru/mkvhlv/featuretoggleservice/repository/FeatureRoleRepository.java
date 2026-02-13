package ru.mkvhlv.featuretoggleservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mkvhlv.featuretoggleservice.domian.Feature;
import ru.mkvhlv.featuretoggleservice.domian.FeatureRole;

import java.util.List;

public interface FeatureRoleRepository extends JpaRepository<FeatureRole, Long> {
    List<FeatureRole> findFeatureRolesByFeature(Feature feature);
}
