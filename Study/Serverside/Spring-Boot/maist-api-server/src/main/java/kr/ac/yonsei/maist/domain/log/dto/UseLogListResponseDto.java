package kr.ac.yonsei.maist.domain.log.dto;

import kr.ac.yonsei.maist.domain.log.domain.UseLog;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UseLogListResponseDto {

    private int logId;
    private int userId;
    private Integer sysFunc;
    private String content;
    private String logDate;
    private Integer runningTime;
    private Integer internalTemperature;
    private Integer externalTemperature;
    private Integer intnsLowFreqPad;

    public UseLogListResponseDto(UseLog entity) {
        this.logId = entity.getLogId();
        this.userId = entity.getUserId();
        this.sysFunc = entity.getSysFunc();
        this.content = entity.getContent();
        this.logDate = entity.getContent();
        this.runningTime = entity.getRunningTime();
        this.internalTemperature = entity.getInternalTemperature();
        this.externalTemperature = entity.getExternalTemperature();
        this.intnsLowFreqPad = entity.getIntnsLowFreqPad();
    }
}
