package com.example.ItmoProject.model.dto.request;

import com.example.ItmoProject.model.enums.CarType;
import com.example.ItmoProject.model.enums.Color;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class CarInfoReq {
        @Schema(description = "Бренд автомобиля")
        private String brand;

        @Schema(description = "Модель")
        private String model;

        @Schema(description = "Цвет")
        private Color color;

        @Schema(description = "Год выпуска")
        private Integer year;

        @Schema(description = "Стоимость")
        private Long price;

        @Schema(description = "Новый")
        private Boolean isNew;

        @Schema(description = "Тип")
        private CarType type;
    }
