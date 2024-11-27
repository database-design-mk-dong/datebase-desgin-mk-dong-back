package com.example.demo.repository;

import com.example.demo.domain.FarmCrop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FarmCropRepository extends JpaRepository<FarmCrop, Long> {
}
