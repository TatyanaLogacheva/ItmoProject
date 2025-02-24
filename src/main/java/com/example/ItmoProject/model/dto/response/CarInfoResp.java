package com.example.ItmoProject.model.dto.response;

import com.example.ItmoProject.model.dto.request.CarInfoReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CarInfoResp extends CarInfoReq {
    @Schema(description = "Id автомобиля")
    private Long id;

    @Schema(description = "Данные пользователя")
    private UserInfoResponse user;

}
