package kr.ac.yonsei.maist.domain.log.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class UseLogRunningRequestDto {
    @NotEmpty
    private String userId;
}
