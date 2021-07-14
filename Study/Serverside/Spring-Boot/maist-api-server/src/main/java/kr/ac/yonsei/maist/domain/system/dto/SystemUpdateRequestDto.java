package kr.ac.yonsei.maist.domain.system.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
public class SystemUpdateRequestDto {

    @NotEmpty
    private String domain;
    @NotEmpty
    private String name;
    @NotEmpty
    private String content;
    private int depth1;
    private int depth2;
    private int depth3;

}
