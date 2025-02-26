package com.example.ItmoProject.model.dto.request;

import com.example.ItmoProject.model.enums.Gender;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserInfoRequest {
    @NotEmpty
    @Schema(description = "Email пользователя")
    private String email;

    @Schema(description = "Пароль")
    private String password;

    @Schema(description = "Имя")
    private String firstName;

    @Schema(description = "Фимилия")
    private String lastName;

    @Schema(description = "Отчество")
    private String middleName;

    @NotNull
    @Schema(description = "Возраст")
    private Integer age;

    @Schema(description = "Пол")
    private Gender gender;
}
