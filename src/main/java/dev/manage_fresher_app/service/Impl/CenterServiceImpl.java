package dev.manage_fresher_app.service.Impl;

import dev.manage_fresher_app.entities.Center;
import dev.manage_fresher_app.exceptions.ResourceNotFoundException;
import dev.manage_fresher_app.repositories.CenterRepository;
import dev.manage_fresher_app.service.CenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CenterServiceImpl implements CenterService {

    @Autowired
    private CenterRepository centerRepository;

    // hien thi danh sach cac trung tam
    @Override
    public List<Center> getAllCenters() {
        return centerRepository.findAll();
    }

    // xem thong tin trung tam
    @Override
    public Optional<Center> getCenterById(Long id) {
        return centerRepository.findById(id);
    }

    // them moi trung tam
    @Override
    public Center addCenter(Center center) {
        return centerRepository.save(center);
    }

    // cap nhat thong tin trung tam
    @Override
    public Center updateCenter(Long id, Center centerDetails) {
        Center center = centerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Center not found"));
        center.setName(centerDetails.getName());
        center.setAddress(centerDetails.getAddress());
        center.setPhoneNumber(centerDetails.getPhoneNumber());
        center.setEmail(centerDetails.getEmail());
        center.setYearEstablished(centerDetails.getYearEstablished());
        return centerRepository.save(center);
    }

    // xoa trung tam
    @Override
    public void deleteCenter(Long id) {
        Center center = centerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Center not found"));
        centerRepository.delete(center);
    }
}
