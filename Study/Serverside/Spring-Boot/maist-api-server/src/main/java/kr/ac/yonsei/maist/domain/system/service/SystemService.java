/**
 * @Author Chanwoo Gwon, Yonsei Univ. Researcher, since 2020.05
 * @Author Mina Kim, Yonsei Univ. Researcher, since 2020.08~
 * @Date 2021.01.03
 */
package kr.ac.yonsei.maist.domain.system.service;

import kr.ac.yonsei.maist.global.dto.PagingDto;
import kr.ac.yonsei.maist.domain.system.dao.SystemQueryFactory;
import kr.ac.yonsei.maist.domain.system.dao.SystemRepository;
import kr.ac.yonsei.maist.domain.system.domain.System;
import kr.ac.yonsei.maist.domain.system.dto.SystemCreateRequestDto;
import kr.ac.yonsei.maist.domain.system.dto.SystemListResponseDto;
import kr.ac.yonsei.maist.domain.system.dto.SystemResponseDto;
import kr.ac.yonsei.maist.domain.system.dto.SystemUpdateRequestDto;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * system code service
 */
@RequiredArgsConstructor
@Service
public class SystemService {

    @NonNull
    private final SystemRepository systemRepository;
    @Autowired
    private SystemQueryFactory systemQueryFactory;

    /**
     * Find all of system code by domain name and depth level.
     * @param domain domain name
     * @param depth1 depth level 1
     * @param depth2 depth level 2
     * @param depth3 depth level 3
     * @return list of system code
     */
    @Transactional
    public List<SystemResponseDto> findAllByDomain(String domain, String depth1, String depth2, String depth3) {
        List<SystemResponseDto> result = systemQueryFactory.findAllBySystemDomain(domain, depth1, depth2, depth3);

        return result;
    }

    /**
     * Find all of system code by domain name, language and depth level.
     * @param domain domain name
     * @param languageCode language code
     * @param depth1 depth level 1
     * @param depth2 depth level 2
     * @param depth3 depth level 3
     * @return list of system code
     */
    @Transactional
    public List<SystemResponseDto> findAllByDomainWithLanguage(String domain, int languageCode, String depth1, String depth2, String depth3) {
        List<SystemResponseDto> result = systemQueryFactory.findAllBySystemDomainWithLanguage(domain, languageCode, depth1, depth2, depth3);

        return result;
    }

    /**
     * Find all of domain of system code.
     * @return list of domain of system code
     */
    @Transactional
    public List<SystemResponseDto> findAllDomain() {
        List<SystemResponseDto> result = systemQueryFactory.findAllDomain();

        return result;
    }

    /**
     * Find all of system code.
     * @return list of system code
     */
    @Transactional(readOnly = true)
    public List<SystemListResponseDto> findSystemCode(PagingDto pagingDto) throws Exception {

        Pageable paging = PageRequest.of(pagingDto.getCurrentPageNo()-1, pagingDto.getRecordCountPerPage());

        List<SystemListResponseDto> systemCodeList = systemRepository
                .findAll(paging)
                .stream()
                .map(SystemListResponseDto::new)
                .collect(Collectors.toList());

        return systemCodeList;
    }

    /**
     * Find count of all system code.
     * @return count
     */
    @Transactional(readOnly = true)
    public long countSystemCode() throws Exception {
        long totalRecordCount = systemRepository.count();

        return totalRecordCount;
    }

    /**
     * Create a system code.
     * @param dto system code information
     */
    @Transactional
    public void createSystemCode(SystemCreateRequestDto dto) throws Exception {

        Optional<System> entity = systemRepository.findById(dto.getSysCodeId());
        if(!entity.isPresent()) {
            systemRepository.save(dto.toEntity());
        }
        else {
            throw new KeyAlreadyExistsException();
        }
    }

    /**
     * Update system code information.
     * @param sysCodeId system code id
     * @param dto system code information
     */
    @Transactional
    public void updateSystemCode(int sysCodeId, SystemUpdateRequestDto dto) throws Exception {

        System entity = systemRepository.findById(sysCodeId)
                .orElseThrow(() -> new IllegalArgumentException("id="+sysCodeId));

        entity.update(dto);
    }

    /**
     * Delete a system code.
     * @param sysCodeId system code id
     */
    @Transactional
    public void deleteSystemCode(int sysCodeId) throws Exception {

        System entity = systemRepository.findById(sysCodeId)
                .orElseThrow(() -> new IllegalArgumentException("id="+sysCodeId));

        systemRepository.delete(entity);
    }

    @Transactional(readOnly = true)
    public List<SystemListResponseDto> findSystemByEditDateAfter(String downloadDate) throws Exception {
        List<SystemListResponseDto> systemCodeList = systemRepository
                .findByEditDateAfter(downloadDate)
                .stream()
                .map(SystemListResponseDto::new)
                .collect(Collectors.toList());

        return systemCodeList;
    }

    @Transactional(readOnly = true)
    public int countSystemByEditDateAfter(String downloadDate) throws Exception {
        int totalRecordCount = systemRepository.countByEditDateAfter(downloadDate);

        return totalRecordCount;
    }


}
