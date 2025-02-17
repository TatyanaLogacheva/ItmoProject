package com.example.ItmoProject.model.bd.entity;

import com.example.ItmoProject.model.enums.CarStatus;
import com.example.ItmoProject.model.enums.CarType;
import com.example.ItmoProject.model.enums.Color;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name ="cars")
public class Car {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "brand")
    private String brand;

    @Column(name = "model")
    private String model;

    @Column(name = "color")
    @Enumerated (EnumType.STRING)
    private Color color;

    @Column(name = "year")
    private Integer year;

    @Column(name = "price")
    private Long price;

    @Column(name = "isNew")
    private Boolean isNew;

    @Column(name = "type")
    @Enumerated (EnumType.STRING)
    private CarType type;

    @CreationTimestamp
    @Column (name ="created_at")
    private LocalDateTime createdAt;

   @UpdateTimestamp
   @Column (name ="updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private CarStatus status;

    @ManyToOne
    @JsonBackReference(value = "driver_cars")
    private User user;
}
