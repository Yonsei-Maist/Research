package kr.ac.yonsei.maist.domain.command.api;

import kr.ac.yonsei.maist.domain.command.dto.CommandCreateRequestDto;
import kr.ac.yonsei.maist.domain.command.dto.CommandListResponseDto;
import kr.ac.yonsei.maist.domain.command.dto.CommandUpdateRequestDto;
import kr.ac.yonsei.maist.domain.command.service.CommandService;
import kr.ac.yonsei.maist.global.dto.PagingDto;
import kr.ac.yonsei.maist.global.response.ResponseMessage;
import kr.ac.yonsei.maist.global.response.dataMessage.PagingDataMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class CommandApi {

    private final CommandService commandService;

    /**
     * Get list of command data (paging).
     * @return ResponseEntity<PagingDataMessage>
     */
    @GetMapping("/maist/command")
    public ResponseEntity<PagingDataMessage> findCommand(
            @Valid @RequestParam(value="currentPageNo") int currentPageNo,
            @Valid @RequestParam(value="elementsPerPage") int elementsPerPage) throws Exception {

        List<CommandListResponseDto> commandList = null;

        PagingDto pagingDto = new PagingDto();
        long totalRecordCount = commandService.countCommand();
        if(totalRecordCount > 0) {
            pagingDto.setCurrentPageNo(currentPageNo);
            pagingDto.setTotalRecordCount(totalRecordCount);
            pagingDto.setRecordCountPerPage(elementsPerPage);

            commandList = commandService.findCommand(pagingDto);
        }

        PagingDataMessage responseMessage = PagingDataMessage.builder()
                .totalPages(pagingDto.getTotalPageCount())
                .totalElements(totalRecordCount)
                .elementsPerPage(elementsPerPage)
                .data(commandList)
                .build();

        return new ResponseEntity<PagingDataMessage>(responseMessage, HttpStatus.OK);
    }

    /**
     * Create command data.
     * @param dto command data
     */
    @PostMapping("/maist/command")
    public ResponseEntity<ResponseMessage> createCommand(@Valid @RequestBody CommandCreateRequestDto dto) throws Exception {

        commandService.createCommand(dto);

        return new ResponseEntity<ResponseMessage>(new ResponseMessage(), HttpStatus.OK);
    }

    /**
     * Update command data.
     * @param commandDataId command data id
     * @param dto command data
     * @return ResponseEntity<ResponseMessage>
     */
    @PutMapping("/maist/command/{commandDataId}")
    public ResponseEntity<ResponseMessage> updateCommand(@PathVariable int commandDataId,
                                                          @Valid @RequestBody CommandUpdateRequestDto dto) throws Exception {
        commandService.updateCommand(commandDataId, dto);

        return new ResponseEntity<ResponseMessage>(new ResponseMessage(), HttpStatus.OK);
    }

    /**
     * Delete command data.
     * @param commandDataId command data id
     * @return ResponseEntity<ResponseMessage>
     */
    @DeleteMapping("/maist/command/{commandDataId}")
    public ResponseEntity<ResponseMessage> deleteCommand(@PathVariable int commandDataId) throws Exception {

        commandService.deleteCommand(commandDataId);

        return new ResponseEntity<ResponseMessage>(new ResponseMessage(), HttpStatus.OK);
    }
}
