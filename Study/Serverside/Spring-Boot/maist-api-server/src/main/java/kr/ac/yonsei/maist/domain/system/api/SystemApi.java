/**
 * @Author Chanwoo Gwon, Yonsei Univ. Researcher, since 2020.05
 * @Author Mina Kim, Yonsei Univ. Researcher, since 2020.08~
 * @Date 2021.01.03
 */
package kr.ac.yonsei.maist.domain.system.api;

import kr.ac.yonsei.maist.global.dto.PagingDto;
import kr.ac.yonsei.maist.global.response.ResponseMessage;
import kr.ac.yonsei.maist.global.response.dataMessage.GeneralDataMessage;
import kr.ac.yonsei.maist.global.response.dataMessage.DownloadDataMessage;
import kr.ac.yonsei.maist.global.response.dataMessage.PagingDataMessage;
import kr.ac.yonsei.maist.domain.language.dto.LanguageListResponseDto;
import kr.ac.yonsei.maist.domain.language.service.LanguageService;
import kr.ac.yonsei.maist.domain.system.dto.*;
import kr.ac.yonsei.maist.domain.system.service.SystemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

/**
 * System code controller
 */
@RequiredArgsConstructor
@RestController
public class SystemApi {

    private final SystemService systemService;
    private final LanguageService languageService;

    /**
     * Get list of system code by domain name and depth.
     */
    @GetMapping("/maist/system/{domain}/{depth1}/{depth2}/{depth3}")
    public ResponseEntity<GeneralDataMessage> systemCodeList(
            @PathVariable String domain,
            @PathVariable String depth1,
            @PathVariable String depth2,
            @PathVariable String depth3
    ) throws Exception {
        List<SystemResponseDto> systemCodeList = systemService.findAllByDomain(domain, depth1, depth2, depth3);

        GeneralDataMessage responseMessage = GeneralDataMessage.builder()
                .data(systemCodeList)
                .build();

        return new ResponseEntity<GeneralDataMessage>(responseMessage, HttpStatus.OK);
    }

    /**
     * Get list of system code by domain name, language and depth.
     */
    @GetMapping("/maist/system/{domain}/{languageCode}/{depth1}/{depth2}/{depth3}")
    public ResponseEntity<GeneralDataMessage> systemCodeList(
            @PathVariable String domain,
            @PathVariable int languageCode,
            @PathVariable String depth1,
            @PathVariable String depth2,
            @PathVariable String depth3
    ) throws Exception {
        List<SystemResponseDto> systemCodeList =
                systemService.findAllByDomainWithLanguage(domain, languageCode, depth1, depth2, depth3);

        GeneralDataMessage responseMessage = GeneralDataMessage.builder()
                .data(systemCodeList)
                .build();

        return new ResponseEntity<GeneralDataMessage>(responseMessage, HttpStatus.OK);
    }

    /**
     * Get list of system code's domain.
     */
    @GetMapping("/maist/system/domain")
    public ResponseEntity<GeneralDataMessage> systemCodeList() throws Exception {
        List<SystemResponseDto> systemCodeList = systemService.findAllDomain();

        GeneralDataMessage responseMessage = GeneralDataMessage.builder()
                .data(systemCodeList)
                .build();

        return new ResponseEntity<GeneralDataMessage>(responseMessage, HttpStatus.OK);
    }

    /**
     * Get list of system code (paging).
     * @return ResponseEntity<PagingDataMessage>
     */
    @GetMapping("/maist/system")
    public ResponseEntity<PagingDataMessage> findSystemCode(
            @Valid @RequestParam(value="currentPageNo") int currentPageNo,
            @Valid @RequestParam(value="elementsPerPage") int elementsPerPage) throws Exception {

        List<SystemListResponseDto> systemCodeList = null;

        PagingDto pagingDto = new PagingDto();
        long totalRecordCount = systemService.countSystemCode();
        if(totalRecordCount > 0) {
            pagingDto.setCurrentPageNo(currentPageNo);
            pagingDto.setTotalRecordCount(totalRecordCount);
            pagingDto.setRecordCountPerPage(elementsPerPage);

            systemCodeList = systemService.findSystemCode(pagingDto);
        }

        PagingDataMessage responseMessage = PagingDataMessage.builder()
                .totalPages(pagingDto.getTotalPageCount())
                .totalElements(totalRecordCount)
                .elementsPerPage(elementsPerPage)
                .data(systemCodeList)
                .build();

        return new ResponseEntity<PagingDataMessage>(responseMessage, HttpStatus.OK);
    }

    /**
     * Create a system code.
     * @param dto system code information
     * @return ResponseEntity<ResponseMessage>
     */
    @PostMapping("/maist/system")
    public ResponseEntity<ResponseMessage> createSystemCode(@Valid @RequestBody SystemCreateRequestDto dto) throws Exception {

        systemService.createSystemCode(dto);

        return new ResponseEntity<ResponseMessage>(new ResponseMessage(), HttpStatus.OK);
    }

    /**
     * Update system code information.
     * @param sysCodeId system code id
     * @param dto system code information
     * @return ResponseEntity<ResponseMessage>
     */
    @PutMapping("/maist/system/{sysCodeId}")
    public ResponseEntity<ResponseMessage> updateSystemCode(@PathVariable int sysCodeId,
                                                      @Valid @RequestBody SystemUpdateRequestDto dto) throws Exception {

        systemService.updateSystemCode(sysCodeId, dto);

        return new ResponseEntity<ResponseMessage>(new ResponseMessage(), HttpStatus.OK);
    }

    /**
     * Delete a system code.
     * @param sysCodeId system code id
     * @return ResponseEntity<ResponseMessage>
     */
    @DeleteMapping("/maist/system/{sysCodeId}")
    public ResponseEntity<ResponseMessage> deleteSystemCode(@PathVariable int sysCodeId) throws Exception {

        systemService.deleteSystemCode(sysCodeId);

        return new ResponseEntity<ResponseMessage>(new ResponseMessage(), HttpStatus.OK);
    }
}
