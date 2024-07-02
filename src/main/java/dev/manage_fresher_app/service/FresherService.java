package dev.manage_fresher_app.service;

import dev.manage_fresher_app.entities.Fresher;
import dev.manage_fresher_app.repositories.FresherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FresherService {
    private final FresherRepository fresherRepository;

    // show Fresher by Id
    public Fresher getFresherById(Long id) {
        return fresherRepository.findById(id).orElseThrow(() -> new RuntimeException("Fresher not found with id " + id));
    }

    // show list Fresher
    public List<Fresher> getAllFreshers() {
        return fresherRepository.findAll();
    }

    // Create Fresher
    public Fresher createFresher(Fresher fresher){
        return fresherRepository.save(fresher);
    }

    // Update Fresher information
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
    public void deleteFresher(Long id) {
        Fresher fresher = fresherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fresher not found with id "+ id));
        fresherRepository.delete(fresher);
    }
}
