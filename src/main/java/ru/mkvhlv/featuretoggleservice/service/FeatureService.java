package ru.mkvhlv.featuretoggleservice.service;

public interface FeatureService<T,I> {
    T getById(I idEntry);
    T create(T feature);
    T updateFeatureById(I id, T feature);
    void deleteById(I id);
}
