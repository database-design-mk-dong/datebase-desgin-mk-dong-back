package com.example.demo.repository;

import com.example.demo.domain.Environment;
import com.example.demo.domain.Farm;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnvironmentRepository extends JpaRepository<Environment, Long> {
    Optional<Environment> findByFarm(Farm farm);
}
