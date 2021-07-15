package kr.ac.yonsei.maist.domain.log.service;

import kr.ac.yonsei.maist.domain.log.dao.ErrorLogRepository;
import kr.ac.yonsei.maist.domain.log.dao.UseLogFactory;
import kr.ac.yonsei.maist.domain.log.dao.UseLogRepository;
import kr.ac.yonsei.maist.domain.log.domain.ErrorLog;
import kr.ac.yonsei.maist.domain.log.domain.UseLog;
import kr.ac.yonsei.maist.domain.log.dto.*;
import kr.ac.yonsei.maist.domain.user.dto.UserInformationDto;
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
    public UseLog createUseLog(String menuID, UserInformationDto dto, String ip, String content) throws Exception {
        UseLog log = UseLog.builder().menuId(menuID).userId(dto.getUserId()).ip(ip).content(content).build();
        UseLog useLog = useLogRepository.save(log);

        return useLog;
    }

    @Transactional
    public ErrorLog createErrorLog(String menuID, UserInformationDto dto, String ip, String content) throws Exception {
        ErrorLog log = ErrorLog.builder().menuId(menuID).userId(dto.getUserId()).ip(ip).content(content).build();
        ErrorLog errorLog = errorLogRepository.save(log);

        return errorLog;
    }
}
