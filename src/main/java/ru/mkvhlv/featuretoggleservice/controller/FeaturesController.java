package ru.mkvhlv.featuretoggleservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mkvhlv.featuretoggleservice.controller.dto.ResponseEvaluate;
import ru.mkvhlv.featuretoggleservice.domian.context.EvaluationContext;
import ru.mkvhlv.featuretoggleservice.domian.decision.FeatureDecision;
import ru.mkvhlv.featuretoggleservice.dto.FeatureDto;
import ru.mkvhlv.featuretoggleservice.service.FeatureServiceImpl;

@RestController
@RequestMapping("/api/features")
public class FeaturesController {

    private final FeatureServiceImpl featureService;

    public FeaturesController(FeatureServiceImpl featureService) {
        this.featureService = featureService;
    }

    @PostMapping
    public ResponseEntity<FeatureDto> createFeature(@RequestBody FeatureDto feature) {
        FeatureDto createdFeature = featureService.create(feature);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFeature);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FeatureDto> getFeatureByFeatureKey(@PathVariable String id) {
        FeatureDto foundFeature = featureService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(foundFeature);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<FeatureDto> updateFeature(@RequestBody FeatureDto feature, @PathVariable String id) {
        FeatureDto updatedFeature = featureService.updateFeatureById(id, feature);
        return ResponseEntity.status(HttpStatus.OK).body(updatedFeature);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteFeatureByFeatureKey(@PathVariable String id) {
        featureService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{id}/evaluate")
    public ResponseEntity<ResponseEvaluate> getEvaluate(@PathVariable String id,
                                                        @RequestParam String role,
                                                        @RequestParam String userId) {
        EvaluationContext context = new EvaluationContext(id, role, userId);
        FeatureDecision decision = featureService.evaluate(context);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseEvaluate(decision.getFeatureKey(), decision.getIsEnabled(), decision.getReason()));
    }

}
