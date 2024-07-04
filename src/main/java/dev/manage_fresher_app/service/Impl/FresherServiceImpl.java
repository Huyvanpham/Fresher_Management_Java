package dev.manage_fresher_app.service.Impl;

import dev.manage_fresher_app.entities.Fresher;
import dev.manage_fresher_app.repositories.FresherRepository;
import dev.manage_fresher_app.service.FresherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FresherServiceImpl implements FresherService {
    private final FresherRepository fresherRepository;

    // show Fresher by Id
    @Override
    public Fresher getFresherById(Long id) {
        return fresherRepository.findById(id).orElseThrow(() -> new RuntimeException("Fresher not found with id " + id));
    }

    // show list Fresher
    @Override
    public List<Fresher> getAllFreshers() {
        return fresherRepository.findAll();
    }

    // Create Fresher
    @Override
    public Fresher createFresher(Fresher fresher){
        return fresherRepository.save(fresher);
    }

    // Update Fresher information
    @Override
    public Fresher updateFresher(Long id, Fresher fresherDetails) {
        Fresher fresher = fresherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fresher not found with id "+ id));

        fresher.setStatus(fresherDetails.getStatus());

        // Update Employee information
        fresher.setName(fresherDetails.getName());
        fresher.setDateOfBirth(fresherDetails.getDateOfBirth());
        fresher.setSex(fresherDetails.getSex());
        fresher.setPhoneNumber(fresherDetails.getPhoneNumber());
        fresher.setEmail(fresherDetails.getEmail());
        fresher.setStatus(fresherDetails.getStatus());

        return fresherRepository.save(fresher);
    }

    // Delete Fresher
    @Override
    public void deleteFresher(Long id) {
        Fresher fresher = fresherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fresher not found with id "+ id));
        fresherRepository.delete(fresher);
    }
}
