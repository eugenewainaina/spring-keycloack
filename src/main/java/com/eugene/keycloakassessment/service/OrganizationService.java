package com.eugene.keycloakassessment.service;

import com.eugene.keycloakassessment.model.Organization;
import com.eugene.keycloakassessment.repository.OrganizationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrganizationService {
    private final OrganizationRepository repository;

    private final KeycloakService keycloakService;


    public OrganizationService(OrganizationRepository repository, KeycloakService keycloakService) {
        this.repository = repository;
        this.keycloakService = keycloakService;
    }


    // Create an organization
    public Organization create(Organization organization) {
        Organization savedOrganization = repository.save(organization);
        keycloakService.createRealm(savedOrganization.getName());

        return savedOrganization;
    }

    // List all organizations
    public List<Organization> findAll() {
        return repository.findAll();
    }

    // Get details of a specific organization
    public Optional<Organization> findById(UUID id) {
        return repository.findById(id);
    }

    // Update the organization
    public Organization update(UUID id, Organization updatedOrganization) {
        return repository.findById(id).map(existing -> {
            existing.setName(updatedOrganization.getName());
            existing.setDescription(updatedOrganization.getDescription());
            return repository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Not found"));
    }

    // Delete an organization
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    // Enable or Disable an organization.
    public Organization enableDisable(UUID id, boolean enabled) {
        return repository.findById(id).map(existing -> {
            existing.setEnabled(enabled);
            return repository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Not found"));
    }
}