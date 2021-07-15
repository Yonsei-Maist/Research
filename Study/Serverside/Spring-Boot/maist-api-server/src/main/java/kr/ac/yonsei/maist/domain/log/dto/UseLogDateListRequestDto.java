package kr.ac.yonsei.maist.domain.log.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class UseLogDateListRequestDto {
    @NotEmpty
    private String userId;
    private int languageCode;
}
