package kr.ac.yonsei.maist.domain.log.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class UseLogRequestDto {

    @NotEmpty
    private String machineId;
    @NotEmpty
    private String userName;
    @Min(411)@Max(414)
    private Integer sysFunc;
    private String content;
    private Integer runningTime;
    private Integer internalTemperature;
    private Integer externalTemperature;
    private Integer intnsLowFreqPad;
    private String ip;
    private int userId;

    public kr.ac.yonsei.maist.domain.log.domain.UseLog toEntity() {
        return kr.ac.yonsei.maist.domain.log.domain.UseLog.builder()
                .userId(userId)
                .sysFunc(sysFunc)
                .content(content)
                .runningTime(runningTime)
                .internalTemperature(internalTemperature)
                .externalTemperature(externalTemperature)
                .intnsLowFreqPad(intnsLowFreqPad)
                .ip(ip)
                .build();
    }
}
