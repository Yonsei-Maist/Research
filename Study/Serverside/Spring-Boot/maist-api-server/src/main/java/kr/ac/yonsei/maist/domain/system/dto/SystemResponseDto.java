/**
 * @Author Chanwoo Gwon, Yonsei Univ. Researcher, since 2020.05
 * @Date 2021.01.03
 */
package kr.ac.yonsei.maist.domain.system.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SystemResponseDto {
    int id;
    String domain;
    String description;
    int depth1;
    int depth2;
    int depth3;
}
