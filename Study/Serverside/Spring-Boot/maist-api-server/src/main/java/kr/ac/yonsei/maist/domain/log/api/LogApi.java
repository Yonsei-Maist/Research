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
}
