package kr.ac.yonsei.maist.domain.user.dto;

import kr.ac.yonsei.maist.domain.user.domain.UserPrivate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
public class UserCreateRequestDto {

    @NotEmpty
    private String id;
    @NotEmpty
    private String name;
    @Min(110)
    @Max(120)
    private int sex;

    private String birth;
    @Min(710)
    @Max(790)
    private int height;

    public UserPrivate toEntity() {
        return UserPrivate.builder()
                .name(name)
                .sex(sex)
                .birth(birth)
                .build();
    }
}
