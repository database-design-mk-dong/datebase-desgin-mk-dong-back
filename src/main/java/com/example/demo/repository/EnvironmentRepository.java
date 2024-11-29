package com.example.demo.repository;

import com.example.demo.domain.Environment;
import com.example.demo.domain.Farm;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EnvironmentRepository extends JpaRepository<Environment, Long> {
    @Query("SELECT e FROM Environment e "
            + "WHERE e.farm = :farm "
            + "ORDER BY e.timestamp DESC "
            + "LIMIT 1 ")
    Optional<Environment> findByFarm(@Param("farm") Farm farm);

    @Query("SELECT e FROM Environment e "
            + "WHERE e.farm = :farm "
            + "ORDER BY e.timestamp DESC "
            + "LIMIT 5 ")
    List<Environment> findAllByOrderByTimestampDesc(@Param("farm") Farm farm);
}
