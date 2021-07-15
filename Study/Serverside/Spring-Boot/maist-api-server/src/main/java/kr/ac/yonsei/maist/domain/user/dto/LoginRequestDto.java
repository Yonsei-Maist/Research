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
public class LoginRequestDto {

    private int id;
    @NotEmpty
    private String password;
    @Min(510)@Max(520)
    private int language;
}
