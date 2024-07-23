package dev.manage_fresher_app.controller;

import dev.manage_fresher_app.DTO.Response.CenterDTO;
import dev.manage_fresher_app.entities.Center;
import dev.manage_fresher_app.exceptions.ResourceNotFoundException;
import dev.manage_fresher_app.service.CenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/centers")
public class CenterController {
    @Autowired
    private CenterService centerService;

    @GetMapping
    public List<CenterDTO> getAllCenters(){
        return centerService.getAllCenters();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Center> getCenterById(@PathVariable Long id) {
        Center center = centerService.getCenterById(id).orElseThrow(() -> new ResourceNotFoundException("Center not found"));
        return ResponseEntity.ok().body(center);
    }

    @PostMapping
    public CenterDTO createCenter(@RequestBody Center center) {
        return centerService.addCenter(center);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Center> updateCenter(@PathVariable Long id, @RequestBody Center centerDetails) {
        Center updatedCenter = centerService.updateCenter(id, centerDetails);
        return ResponseEntity.ok(updatedCenter);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCenter(@PathVariable Long id) {
        centerService.deleteCenter(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/merge")
    public ResponseEntity<Center> mergeCenters(@RequestParam Long centerId1, @RequestParam Long centerId2, @RequestBody Center newCenterInfo){
        Center newCenter = centerService.mergeCenters(centerId1,centerId2,newCenterInfo);
        return ResponseEntity.ok(newCenter);
    }


}
