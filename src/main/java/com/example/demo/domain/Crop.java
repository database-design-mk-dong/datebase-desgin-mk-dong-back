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
@Table(name = "crop")
@DynamicUpdate
public class Crop {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(nullable = false)
    private String cropName;

    @OneToMany(mappedBy = "crop", cascade = CascadeType.MERGE)
    private List<FarmCrop> farmCrops;

    @Builder
    public Crop(String cropName, List<FarmCrop> farmCrops) {
        this.cropName = cropName;
    }

    public static Crop toEntity(String cropName) {
        return Crop.builder()
                .cropName(cropName)
                .build();
    }
}
