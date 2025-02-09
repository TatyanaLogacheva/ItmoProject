package com.example.ItmoProject.model.dto.request;

import com.example.ItmoProject.model.enums.CarType;
import com.example.ItmoProject.model.enums.Color;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

    @Getter
    @Setter
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    public class CarInfoReq {
        private String brand;
        private String model;
        private Color color;
        private Integer year;
        private Long price;
        private Boolean isNew;
        private CarType type;
    }
