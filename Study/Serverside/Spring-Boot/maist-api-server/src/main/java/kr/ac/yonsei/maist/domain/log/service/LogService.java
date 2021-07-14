package kr.ac.yonsei.maist.domain.log.service;

import kr.ac.yonsei.maist.domain.log.dao.ErrorLogRepository;
import kr.ac.yonsei.maist.domain.log.dao.UseLogFactory;
import kr.ac.yonsei.maist.domain.log.dao.UseLogRepository;
import kr.ac.yonsei.maist.domain.log.domain.ErrorLog;
import kr.ac.yonsei.maist.domain.log.domain.UseLog;
import kr.ac.yonsei.maist.domain.log.dto.*;
import kr.ac.yonsei.maist.domain.machine.dto.MachineUploadRequestDto;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class LogService {

    @NonNull
    private final UseLogRepository useLogRepository;

    @NonNull
    private final ErrorLogRepository errorLogRepository;

    @Autowired
    private UseLogFactory useLogFactory;

    @Transactional(readOnly = true)
    public List<UseLogListResponseDto> findUseLogByUserId(ArrayList userId) throws Exception {
        List<UseLogListResponseDto> useLogList = useLogRepository
                .findByUserIdIn(userId)
                .stream()
                .map(UseLogListResponseDto::new)
                .collect(Collectors.toList());

        return useLogList;
    }

    @Transactional(readOnly = true)
    public List<ErrorLogListResponseDto> findErrorLogByUserId(ArrayList userId) throws Exception {
        List<ErrorLogListResponseDto> errorLogList = errorLogRepository
                .findByUserIdIn(userId)
                .stream()
                .map(ErrorLogListResponseDto::new)
                .collect(Collectors.toList());

        return errorLogList;
    }

    @Transactional
    public UseLog createUseLog(MachineUploadRequestDto.UseLog dto) throws Exception {

        UseLog useLog = useLogRepository.save(dto.toEntity());

        return useLog;
    }

    @Transactional
    public UseLog createUseLog(UseLogRequestDto dto) throws Exception {

        UseLog useLog = useLogRepository.save(dto.toEntity());

        return useLog;
    }

    @Transactional
    public ErrorLog createErrorLog(MachineUploadRequestDto.ErrorLog dto) throws Exception {

        ErrorLog errorLog = errorLogRepository.save(dto.toEntity());

        return errorLog;
    }

    @Transactional
    public List<UseLogDateListResponseDto> findDateList(UseLogDateListRequestDto dto) throws Exception {
        List<UseLogDateListResponseDto> listResponseDtos = useLogFactory.findDateList(dto);

        return listResponseDtos;
    }

    @Transactional
    public UseLogRunningResponseDto findLogByWeekOfYear(UseLogRunningRequestDto dto) throws Exception {
        UseLogRunningResponseDto responseDto = new UseLogRunningResponseDto();
        responseDto.setLogList(useLogFactory.findAllLogData(dto));
        responseDto.setSumByWeekOfYearListByMode(useLogFactory.findSumLogDataByMode(dto));
        responseDto.setSumByWeekOfYearListByTime(useLogFactory.findSumLogDataByTime(dto));
        responseDto.setTotalRunningTime(useLogFactory.findTotalLogData(dto));

        return responseDto;
    }
}
