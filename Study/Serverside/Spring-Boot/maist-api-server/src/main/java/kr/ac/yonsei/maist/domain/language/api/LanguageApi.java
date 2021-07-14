package kr.ac.yonsei.maist.domain.language.api;

import kr.ac.yonsei.maist.global.dto.PagingDto;
import kr.ac.yonsei.maist.global.response.ResponseMessage;
import kr.ac.yonsei.maist.global.response.dataMessage.PagingDataMessage;
import kr.ac.yonsei.maist.domain.language.dto.LanguageCreateRequestDto;
import kr.ac.yonsei.maist.domain.language.dto.LanguageListResponseDto;
import kr.ac.yonsei.maist.domain.language.dto.LanguageUpdateRequestDto;
import kr.ac.yonsei.maist.domain.language.service.LanguageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class LanguageApi {

    private final LanguageService languageService;

/*    @GetMapping(value="/maist/language")
    public ResponseEntity<SynchDataMessage> findAllLanguage() throws Exception {

        int totalRecordCount = languageService.countAllLanguage();
        List<LanguageListResponseDto> languageList = null;
        Boolean isNeededSynch = false;

        if(totalRecordCount > 0) {
            isNeededSynch = true;
            languageList = languageService.findAllLanguage();
        }
        SynchDataMessage responseMessage = SynchDataMessage.builder()
                .isNeededSynch(isNeededSynch)
                .totalElements(totalRecordCount)
                .data(languageList)
                .build();

        return new ResponseEntity<SynchDataMessage>(responseMessage, HttpStatus.OK);
    }*/

/*    @GetMapping(value="/maist/download/language/necessity")
    public ResponseEntity<DownloadMessage> findLangDownloadNecessity(@RequestParam(value="downloadDate") String downloadDate) throws Exception {

        int totalRecordCount = languageService.countLanguageByEditDateAfter(downloadDate);
        Boolean isNeededSynch = false;

        if(totalRecordCount > 0) {
            isNeededSynch = true;
        }

        DownloadMessage responseMessage = DownloadMessage.builder()
                .isNeededSynch(isNeededSynch)
                .totalElements(totalRecordCount)
                .build();

        return new ResponseEntity<DownloadMessage>(responseMessage, HttpStatus.OK);
    }*/

/*    @GetMapping(value="/maist/download/language")
    public ResponseEntity<DownloadDataMessage> findLangDownloadData(@RequestParam(value="downloadDate") String downloadDate,
                                                                    @RequestParam(value="id") String machineId) throws Exception {

        int totalRecordCount = languageService.countLanguageByEditDateAfter(downloadDate);
        List<LanguageListResponseDto> languageList = null;
        Boolean isNeededSynch = false;

        if(totalRecordCount > 0) {
            isNeededSynch = true;
            languageList = languageService.findLanguageByEditDateAfter(downloadDate);
        }
        machineService.updateSysCodeDownloadDate(machineId);

        DownloadDataMessage responseMessage = DownloadDataMessage.builder()
                .isNeededSynch(isNeededSynch)
                .totalElements(totalRecordCount)
                .data(languageList)
                .build();

        return new ResponseEntity<DownloadDataMessage>(responseMessage, HttpStatus.OK);
    }*/

    /**
     * Get list of language (paging).
     * @return ResponseEntity<PagingDataMessage>
     */
    @GetMapping("/maist/language")
    public ResponseEntity<PagingDataMessage> findLanguage(
            @Valid @RequestParam(value="currentPageNo") int currentPageNo,
            @Valid @RequestParam(value="elementsPerPage") int elementsPerPage) throws Exception {

        List<LanguageListResponseDto> languageList = null;

        PagingDto pagingDto = new PagingDto();
        long totalRecordCount = languageService.countLanguage();
        if(totalRecordCount > 0) {
            pagingDto.setCurrentPageNo(currentPageNo);
            pagingDto.setTotalRecordCount(totalRecordCount);
            pagingDto.setRecordCountPerPage(elementsPerPage);

            languageList = languageService.findLanguage(pagingDto);
        }

        PagingDataMessage responseMessage = PagingDataMessage.builder()
                .totalPages(pagingDto.getTotalPageCount())
                .totalElements(totalRecordCount)
                .elementsPerPage(elementsPerPage)
                .data(languageList)
                .build();

        return new ResponseEntity<PagingDataMessage>(responseMessage, HttpStatus.OK);
    }

    /**
     * Create a language.
     * @param dto language information
     */
    @PostMapping("/maist/language")
    public ResponseEntity<ResponseMessage> createLanguage(@Valid @RequestBody LanguageCreateRequestDto dto) throws Exception {

        languageService.createLanguage(dto);

        return new ResponseEntity<ResponseMessage>(new ResponseMessage(), HttpStatus.OK);
    }

    /**
     * Update language information.
     * @param languageId language id
     * @param dto language information
     * @return ResponseEntity<ResponseMessage>
     */
    @PutMapping("/maist/language/{languageId}")
    public ResponseEntity<ResponseMessage> updateLanguage(@PathVariable int languageId,
                                                            @Valid @RequestBody LanguageUpdateRequestDto dto) throws Exception {
        languageService.updateLanguage(languageId, dto);

        return new ResponseEntity<ResponseMessage>(new ResponseMessage(), HttpStatus.OK);
    }

    /**
     * Delete a language.
     * @param languageId language id
     * @return ResponseEntity<ResponseMessage>
     */
    @DeleteMapping("/maist/language/{languageId}")
    public ResponseEntity<ResponseMessage> deleteLanguage(@PathVariable int languageId) throws Exception {

        languageService.deleteLanguage(languageId);

        return new ResponseEntity<ResponseMessage>(new ResponseMessage(), HttpStatus.OK);
    }

}
