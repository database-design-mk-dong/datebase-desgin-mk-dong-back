package com.example.demo.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "device")
@DynamicUpdate
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(nullable = false)
    private String deviceName;

    @OneToMany(mappedBy = "device", cascade = CascadeType.MERGE)
    private List<FarmDevice> farmDevices;

    @Builder
    public Device(String deviceName) {
        this.deviceName = deviceName;
    }

    public static Device toEntity(String deviceName) {
        return Device.builder()
                .deviceName(deviceName)
                .build();
    }
}
