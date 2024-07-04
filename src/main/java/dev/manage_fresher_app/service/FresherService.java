package dev.manage_fresher_app.service;

import dev.manage_fresher_app.entities.Fresher;

import java.util.List;

public interface FresherService {
    Fresher getFresherById(Long id);

    List<Fresher> getAllFreshers();

    Fresher createFresher(Fresher fresher);

    Fresher updateFresher(Long id, Fresher fresherDetails);

    void deleteFresher(Long id);
}
