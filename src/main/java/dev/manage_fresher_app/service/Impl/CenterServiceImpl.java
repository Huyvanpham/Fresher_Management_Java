package dev.manage_fresher_app.service.Impl;

import dev.manage_fresher_app.DTO.Response.CenterDTO;
import dev.manage_fresher_app.entities.Center;
import dev.manage_fresher_app.entities.WorkingHistory;
import dev.manage_fresher_app.exceptions.ResourceNotFoundException;
import dev.manage_fresher_app.repositories.CenterRepository;
import dev.manage_fresher_app.repositories.WorkingHistoryRepository;
import dev.manage_fresher_app.service.CenterService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CenterServiceImpl implements CenterService {

    @Autowired
    private CenterRepository centerRepository;

    @Autowired
    private WorkingHistoryRepository workingHistoryRepository;

    // hien thi danh sach cac trung tam
    @Override
    public List<CenterDTO> getAllCenters() {
        return centerRepository.findAll().stream().map(CenterDTO::new).collect(Collectors.toList());
    }

    // xem thong tin trung tam
    @Override
    public Optional<Center> getCenterById(Long id) {
        return centerRepository.findById(id);
    }

    // them moi trung tam
    @Override
    public CenterDTO addCenter(Center center) {
        center.getManagerHistories().forEach(history -> history.setCenter(center));
        return new CenterDTO(centerRepository.save(center));
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


    // Gop 2 trung tam
    @Override
    public Center mergeCenters(Long centerId1, Long centerId2, Center newCenterInfo) {
        Optional<Center> center1 = centerRepository.findById(centerId1);
        Optional<Center> center2 = centerRepository.findById(centerId2);

        if(center1.isPresent() && center2.isPresent()) {
            Center newCenter = new Center();
            newCenter.setName(newCenterInfo.getName());
            newCenter.setAddress(newCenterInfo.getAddress());
            newCenter.setPhoneNumber(newCenterInfo.getPhoneNumber());
            newCenter.setEmail(newCenterInfo.getEmail());
            newCenter.setYearEstablished(newCenterInfo.getYearEstablished());

            // luu trung tam moi
            newCenter = centerRepository.save(newCenter);

            // chuyen Fresher tu trung tam 1 va 2 sang trung tam moi thong qua WorkingHistory
            List<WorkingHistory> workingHistoriesCenter1 = workingHistoryRepository.findByCenterId(centerId1);
            List<WorkingHistory> workingHistoriesCenter2 = workingHistoryRepository.findByCenterId(centerId2);

            for(WorkingHistory wh : workingHistoriesCenter1) {
                wh.setCenter(newCenter);
                workingHistoryRepository.save(wh);
            }

            for(WorkingHistory wh : workingHistoriesCenter2) {
                wh.setCenter(newCenter);
                workingHistoryRepository.save(wh);
            }

            // xoa center1 va center2
            centerRepository.delete(center1.get());
            centerRepository.delete(center2.get());

            return newCenter;
        }
        else {
            throw new EntityNotFoundException("One or both Centers not found");
        }
    }
}
