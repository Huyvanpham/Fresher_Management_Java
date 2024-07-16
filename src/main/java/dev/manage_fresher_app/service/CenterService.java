package dev.manage_fresher_app.service;

import dev.manage_fresher_app.entities.Center;

import java.util.List;
import java.util.Optional;

public interface CenterService {
    List<Center> getAllCenters();
    Optional<Center> getCenterById(Long id);
    Center addCenter(Center center);
    Center updateCenter(Long id, Center centerDetails);
    void deleteCenter(Long id);
}
