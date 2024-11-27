package com.example.demo.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "farm")
@DynamicUpdate
public class Farm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(nullable = false)
    private String farmName;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToMany(mappedBy = "farm", cascade = CascadeType.ALL)
    private List<FarmCrop> farmCrops;

    @OneToMany(mappedBy = "farm", cascade = CascadeType.ALL)
    private List<FarmDevice> farmDevices;

    @OneToMany(mappedBy = "farm", cascade = CascadeType.ALL)
    private List<Environment> environments;

    @Builder
    public Farm(String farmName, User user) {
        this.farmName = farmName;
        this.user = user;
    }

    public static Farm toEntity(String farmName , User user) {
        return Farm.builder()
                .farmName(farmName)
                .user(user)
                .build();
    }

    public void updateFarm(String farmName){
        this.farmName = farmName;
    }


}
