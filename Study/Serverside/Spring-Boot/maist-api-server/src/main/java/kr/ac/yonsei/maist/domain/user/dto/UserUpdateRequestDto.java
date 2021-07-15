package kr.ac.yonsei.maist.domain.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
public class UserUpdateRequestDto {

    @NotEmpty
    private String name;
    @Min(110)
    @Max(120)
    private int sex;

    private String birth;
}
