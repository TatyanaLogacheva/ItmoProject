package com.example.ItmoProject.model.dto.request;

import com.example.ItmoProject.model.enums.Gender;
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
public class UserInfoRequest {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String middleName;
    private Integer age;
    private Gender gender;

}
