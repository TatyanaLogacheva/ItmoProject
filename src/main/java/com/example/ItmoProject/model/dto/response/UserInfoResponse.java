package com.example.ItmoProject.model.dto.response;

import com.example.ItmoProject.model.dto.request.UserInfoRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoResponse extends UserInfoRequest {

    @Schema(description = "Id пользователя")
    private Long id;
}
