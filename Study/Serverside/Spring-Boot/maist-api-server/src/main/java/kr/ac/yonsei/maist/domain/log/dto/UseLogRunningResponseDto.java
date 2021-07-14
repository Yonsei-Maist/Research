package kr.ac.yonsei.maist.domain.log.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UseLogRunningResponseDto {
    private List<UseLogListResponseDto> logList;
    private List<UseLogDataByModeResponseDto> sumByWeekOfYearListByMode;
    private List<UseLogDataByTimeResponseDto> sumByWeekOfYearListByTime;
    private Integer totalRunningTime;

    @Getter
    @Setter
    public static class UseLogDataByModeResponseDto {
        private Integer sumRunningTimeByWeekOfYear;
        private Integer sysFunc;
    }

    @Getter
    @Setter
    public static class UseLogDataByTimeResponseDto {
        private Integer sumRunningTimeByWeekOfYear;
        private Integer dayOfWeek;
    }
}
