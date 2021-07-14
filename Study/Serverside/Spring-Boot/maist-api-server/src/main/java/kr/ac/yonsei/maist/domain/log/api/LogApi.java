/**
 * Controller that performs all logic such as inquiry, creation, modification, deletion of NX machine
 * @author Mina Kim, Yonsei Univ. Researcher, since 2020.08~
 * @Date 2021.01.07
 */
package kr.ac.yonsei.maist.domain.log.api;

import kr.ac.yonsei.maist.domain.log.dto.*;
import kr.ac.yonsei.maist.domain.log.service.LogService;
import kr.ac.yonsei.maist.domain.user.dto.UserResponseDto;
import kr.ac.yonsei.maist.domain.user.service.UserService;
import kr.ac.yonsei.maist.global.response.ResponseMessage;
import kr.ac.yonsei.maist.global.response.dataMessage.GeneralDataMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class LogApi {

    private final LogService logService;
    private final UserService userService;

    /**
     * Create an NX machine.
     * @param dto UseLogRequestDto
     * @return ResponseEntity<ResponseMessage>
     */
    @PostMapping("/maist/report")
    public ResponseEntity<ResponseMessage> createLog(@Valid @RequestBody UseLogRequestDto dto, HttpServletRequest request) throws Exception {
        UserResponseDto user = userService.findUserByMachineIdAndName(dto.getMachineId(), dto.getUserName());
        String ip = request.getRemoteAddr();

        dto.setIp(ip);
        dto.setUserId(user.getUserId());
        logService.createUseLog(dto);

        return new ResponseEntity<ResponseMessage>(new ResponseMessage(), HttpStatus.OK);
    }

    /**
     * Check the existence of the NX machine.
     * @param dto UseLogRequestDto
     * @return ResponseEntity<ResponseDataMessage> NX machine presence data
     */
    @GetMapping("/maist/report/date")
    public ResponseEntity<GeneralDataMessage> logDateList(@Valid @RequestBody UseLogDateListRequestDto dto) throws Exception {
        List<UseLogDateListResponseDto> datas = logService.findDateList(dto);

        GeneralDataMessage responseMessage = GeneralDataMessage.builder()
                .data(datas)
                .build();

        return new ResponseEntity<GeneralDataMessage>(responseMessage, HttpStatus.OK);
    }

    /**
     * Get the list of NX machines connected to the user.
     * @param dto UseLogRequestDto
     * @return ResponseEntity<ResponseDataMessage> NX machine list data
     */
    @GetMapping("/maist/report")
    public ResponseEntity<GeneralDataMessage> logDataList(@Valid @RequestBody UseLogRunningRequestDto dto) throws Exception {
        UseLogRunningResponseDto responseDto = logService.findLogByWeekOfYear(dto);

        GeneralDataMessage responseMessage = GeneralDataMessage.builder()
                .data(responseDto)
                .build();

        return new ResponseEntity<GeneralDataMessage>(responseMessage, HttpStatus.OK);
    }
}
