package com.eugene.keycloakassessment.controller;

import com.eugene.keycloakassessment.model.Organization;
import com.eugene.keycloakassessment.service.OrganizationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/organizations")
public class OrganizationController {

    private final OrganizationService service;

    //@Autowired
    public OrganizationController(OrganizationService service) {
        this.service = service;
    }

    // Create an organization
    @PostMapping
    public ResponseEntity<Organization> create(@RequestBody Organization newOrganization) {
        return ResponseEntity.ok(service.create(newOrganization));
    }

    // List all organization
    @GetMapping
    public List<Organization> listAll() {
        return service.findAll();
    }

    // Get details of a specific organization
    @GetMapping("/{id}")
    public ResponseEntity<Organization> get(@PathVariable UUID id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Update an organization
    @PutMapping("/{id}")
    public ResponseEntity<Organization> update(@PathVariable UUID id, @RequestBody Organization org) {
        return ResponseEntity.ok(service.update(id, org));
    }

    // Delete an organization
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Enable or Disable an organization.
    @PatchMapping("/{id}/status")
    public ResponseEntity<Organization> toggleStatus(@PathVariable UUID id, @RequestParam boolean enabled) {
        return ResponseEntity.ok(service.enableDisable(id, enabled));
    }
}