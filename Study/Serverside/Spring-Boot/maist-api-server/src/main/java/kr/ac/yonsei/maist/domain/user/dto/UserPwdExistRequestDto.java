package kr.ac.yonsei.maist.domain.user.dto;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class UserPwdExistRequestDto {

    @NotEmpty
    private int userId;
    @NotEmpty
    private String password;

}
