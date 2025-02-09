package com.example.ItmoProject.model.dto.response;

import com.example.ItmoProject.model.dto.request.CarInfoReq;
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
    private Long id;

}
