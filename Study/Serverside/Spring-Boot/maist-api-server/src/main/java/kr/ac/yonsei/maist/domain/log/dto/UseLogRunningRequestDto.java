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
    private String userName;
    @NotEmpty
    private String machineId;
    @Min(1900)@Max(3000)
    private Integer dateYear;
    @Min(1)@Max(12)
    private Integer dateMonth;
    @Min(1)@Max(6)
    private Integer weekOfMonth;
}
