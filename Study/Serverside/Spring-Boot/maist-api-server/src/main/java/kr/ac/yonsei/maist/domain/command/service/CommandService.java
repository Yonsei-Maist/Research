package kr.ac.yonsei.maist.domain.command.service;

import kr.ac.yonsei.maist.domain.command.dao.CommandRepository;
import kr.ac.yonsei.maist.domain.command.domain.Command;
import kr.ac.yonsei.maist.domain.command.dto.CommandCreateRequestDto;
import kr.ac.yonsei.maist.domain.command.dto.CommandListResponseDto;
import kr.ac.yonsei.maist.domain.command.dto.CommandUpdateRequestDto;
import kr.ac.yonsei.maist.global.dto.PagingDto;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CommandService {

    @NonNull
    private final CommandRepository commandRepository;

    /**
     * Find all of command data.
     * @return list of command data
     */
    @Transactional(readOnly = true)
    public List<CommandListResponseDto> findCommand(PagingDto pagingDto) throws Exception {

        Pageable paging = PageRequest.of(pagingDto.getCurrentPageNo()-1, pagingDto.getRecordCountPerPage());

        List<CommandListResponseDto> commandList = commandRepository
                .findAll(paging)
                .stream()
                .map(CommandListResponseDto::new)
                .collect(Collectors.toList());

        return commandList;
    }

    /**
     * Find count of all command data.
     * @return count
     */
    @Transactional(readOnly = true)
    public long countCommand() throws Exception {
        long totalRecordCount = commandRepository.count();

        return totalRecordCount;
    }

    /**
     * Create command data.
     * @param dto command data
     */
    @Transactional
    public void createCommand(CommandCreateRequestDto dto) throws Exception {

        commandRepository.save(dto.toEntity());

    }

    /**
     * Update command data.
     * @param commandDataId command data id
     * @param dto command data
     */
    @Transactional
    public void updateCommand(int commandDataId, CommandUpdateRequestDto dto) throws Exception {

        Command entity = commandRepository.findById(commandDataId)
                .orElseThrow(() -> new IllegalArgumentException("id="+commandDataId));

        entity.update(dto);
    }

    /**
     * Delete command data.
     * @param commandDataId command data id
     */
    @Transactional
    public void deleteCommand(int commandDataId) throws Exception {

        Command entity = commandRepository.findById(commandDataId)
                .orElseThrow(() -> new IllegalArgumentException("id="+commandDataId));

        commandRepository.delete(entity);
    }
}
