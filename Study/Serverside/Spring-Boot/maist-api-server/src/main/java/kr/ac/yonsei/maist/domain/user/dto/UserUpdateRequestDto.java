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
    @Min(810)
    @Max(890)
    private int age;
    @Min(710)
    @Max(790)
    private int height;
}
