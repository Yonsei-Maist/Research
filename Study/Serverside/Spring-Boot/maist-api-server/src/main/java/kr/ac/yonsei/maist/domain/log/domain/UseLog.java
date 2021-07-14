package kr.ac.yonsei.maist.domain.log.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@Entity
@DynamicInsert
@Table(name = "TBL_LOG_USE")
public class UseLog {

    @Id
    @Column(name = "id_log_use")
    private int logId;

    @Column(name = "id_user")
    private int userId;

    @Column(name = "sys_func")
    private int sysFunc;

    @Column(name = "date_log")
    private String logDate;

    @Column(name = "var_content")
    private String content;

    @Column(name = "id_menu")
    private String menuId;

    @Column(name = "var_ip")
    private String ip;

    @Column(name = "int_running_time")
    private Integer runningTime;

    @Column(name = "int_internal_temperature")
    private Integer internalTemperature;

    @Column(name = "int_external_temperature")
    private Integer externalTemperature;

    @Column(name = "int_intensity_low_frequency_pad")
    private Integer intnsLowFreqPad;

    @Builder
    public UseLog(int userId,
                  int sysFunc,
                  String logDate,
                  String content,
                  Integer runningTime,
                  Integer internalTemperature,
                  Integer externalTemperature,
                  Integer intnsLowFreqPad,
                  String ip) {

        this.userId = userId;
        this.sysFunc = sysFunc;
        this.logDate = logDate;
        this.content = content;
        this.runningTime = runningTime;
        this.internalTemperature = internalTemperature;
        this.externalTemperature = externalTemperature;
        this.intnsLowFreqPad = intnsLowFreqPad;
        this.ip = ip;
    }



}
