package com.eugene.keycloakassessment.repository;

import com.eugene.keycloakassessment.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrganizationRepository extends JpaRepository<Organization, UUID> {}