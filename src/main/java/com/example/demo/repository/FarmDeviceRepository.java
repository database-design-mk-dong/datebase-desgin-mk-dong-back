package com.example.demo.repository;

import com.example.demo.domain.Device;
import com.example.demo.domain.Farm;
import com.example.demo.domain.FarmDevice;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FarmDeviceRepository extends JpaRepository<FarmDevice, Long> {

    Optional<FarmDevice> findByFarmAndDevice(Farm farm, Device device);
}
