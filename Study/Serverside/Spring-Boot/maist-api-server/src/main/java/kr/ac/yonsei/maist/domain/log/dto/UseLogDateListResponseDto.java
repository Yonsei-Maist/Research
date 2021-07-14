package kr.ac.yonsei.maist.domain.log.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UseLogDateListResponseDto {
    private int dateYear;
    private int dateMonth;
    private int weekOfMonth;
    private String dateMonthName;
    private String dateWeekName;
}
