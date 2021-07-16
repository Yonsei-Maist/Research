package kr.ac.yonsei.maist.domain.system.dto;

import kr.ac.yonsei.maist.domain.system.domain.System;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
public class SystemCreateRequestDto {

    private int sysCodeId;
    @NotEmpty
    private String domain;
    @NotEmpty
    private String description;
    private int depth1;
    private int depth2;
    private int depth3;

    public System toEntity() {
        return System.builder()
                .sysCodeId(sysCodeId)
                .domain(domain)
                .description(description)
                .depth1(depth1)
                .depth2(depth2)
                .depth3(depth3)
                .build();
    }
}
