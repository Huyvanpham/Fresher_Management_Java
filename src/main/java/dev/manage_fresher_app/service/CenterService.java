package dev.manage_fresher_app.service;

import dev.manage_fresher_app.DTO.Response.CenterDTO;
import dev.manage_fresher_app.entities.Center;

import java.util.List;
import java.util.Optional;

public interface CenterService {
    List<CenterDTO> getAllCenters();
    Optional<Center> getCenterById(Long id);
    CenterDTO addCenter(Center center);
    Center updateCenter(Long id, Center centerDetails);
    void deleteCenter(Long id);

    Center mergeCenters(Long centerId1,Long centerId2, Center newCenterInfo);
}
