package com.example.demo.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "farm_crop")
@DynamicUpdate
public class FarmCrop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @JoinColumn(name = "farm_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Farm farm;

    @JoinColumn(name = "crop_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Crop crop;

    @Builder
    public FarmCrop(Farm farm, Crop crop) {
        this.farm = farm;
        this.crop = crop;
    }

    public static FarmCrop toEntity(Farm farm, Crop crop) {
        return FarmCrop.builder()
                .farm(farm)
                .crop(crop)
                .build();
    }
}
