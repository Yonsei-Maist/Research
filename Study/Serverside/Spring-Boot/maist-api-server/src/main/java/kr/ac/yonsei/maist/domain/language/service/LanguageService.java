package kr.ac.yonsei.maist.domain.language.service;

import kr.ac.yonsei.maist.global.dto.PagingDto;
import kr.ac.yonsei.maist.domain.language.dao.LanguageRepository;
import kr.ac.yonsei.maist.domain.language.domain.Language;
import kr.ac.yonsei.maist.domain.language.dto.LanguageCreateRequestDto;
import kr.ac.yonsei.maist.domain.language.dto.LanguageListResponseDto;
import kr.ac.yonsei.maist.domain.language.dto.LanguageUpdateRequestDto;
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
public class LanguageService {

    @NonNull
    private final LanguageRepository languageRepository;

    @Transactional(readOnly = true)
    public List<LanguageListResponseDto> findAllLanguage() throws Exception {
        List<LanguageListResponseDto> languageList = languageRepository
                .findAll()
                .stream()
                .map(LanguageListResponseDto::new)
                .collect(Collectors.toList());

        return languageList;
    }

    @Transactional(readOnly = true)
    public List<LanguageListResponseDto> findLanguageByEditDateAfter(String downloadDate) throws Exception {
        List<LanguageListResponseDto> languageList = languageRepository
                .findByEditDateAfter(downloadDate)
                .stream()
                .map(LanguageListResponseDto::new)
                .collect(Collectors.toList());

        return languageList;
    }

/*    @Transactional(readOnly = true)
    public int countAllLanguage() throws Exception {
        int totalRecordCount = languageRepository.countAll();

        return totalRecordCount;
    }*/

    @Transactional(readOnly = true)
    public int countLanguageByEditDateAfter(String downloadDate) throws Exception {
        int totalRecordCount = languageRepository.countByEditDateAfter(downloadDate);

        return totalRecordCount;
    }

    /**
     * Find all of language.
     * @return list of language
     */
    @Transactional(readOnly = true)
    public List<LanguageListResponseDto> findLanguage(PagingDto pagingDto) throws Exception {

        Pageable paging = PageRequest.of(pagingDto.getCurrentPageNo()-1, pagingDto.getRecordCountPerPage());

        List<LanguageListResponseDto> languageList = languageRepository
                .findAll(paging)
                .stream()
                .map(LanguageListResponseDto::new)
                .collect(Collectors.toList());

        return languageList;
    }

    /**
     * Find count of all language.
     * @return count
     */
    @Transactional(readOnly = true)
    public long countLanguage() throws Exception {
        long totalRecordCount = languageRepository.count();

        return totalRecordCount;
    }

    /**
     * Create a language.
     * @param dto language information
     */
    @Transactional
    public void createLanguage(LanguageCreateRequestDto dto) throws Exception {

        languageRepository.save(dto.toEntity());

    }

    /**
     * Update language information.
     * @param languageId language id
     * @param dto language information
     */
    @Transactional
    public void updateLanguage(int languageId, LanguageUpdateRequestDto dto) throws Exception {

        Language entity = languageRepository.findById(languageId)
                .orElseThrow(() -> new IllegalArgumentException("id="+languageId));

        entity.update(dto);
    }

    /**
     * Delete a language.
     * @param languageId language id
     */
    @Transactional
    public void deleteLanguage(int languageId) throws Exception {

        Language entity = languageRepository.findById(languageId)
                .orElseThrow(() -> new IllegalArgumentException("id="+languageId));

        languageRepository.delete(entity);
    }
}
