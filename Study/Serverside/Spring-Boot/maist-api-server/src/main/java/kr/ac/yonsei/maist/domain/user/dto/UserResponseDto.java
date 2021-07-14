package kr.ac.yonsei.maist.domain.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class UserResponseDto {

    private int userId;
    private String password;
    private String name;
    private String sex;
    private String age;
    private String height;

}
