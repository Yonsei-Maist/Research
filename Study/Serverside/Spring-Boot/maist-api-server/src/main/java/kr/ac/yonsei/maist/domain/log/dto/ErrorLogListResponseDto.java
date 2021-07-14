package kr.ac.yonsei.maist.domain.log.dto;

import kr.ac.yonsei.maist.domain.log.domain.ErrorLog;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ErrorLogListResponseDto {

    private int logId;
    private int userId;
    private Integer sysError;
    private String content;
    private String logDate;

    public ErrorLogListResponseDto(ErrorLog entity) {
        this.logId = entity.getLogId();
        this.userId = entity.getUserId();
        this.sysError = entity.getErrorCode();
        this.content = entity.getContent();
        this.logDate = entity.getLogDate();
    }
}
