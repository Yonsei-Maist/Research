package kr.ac.yonsei.maist.domain.language.dao;

import kr.ac.yonsei.maist.domain.language.domain.Language;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LanguageRepository extends JpaRepository<Language, Integer> {

    //int countAll();

    int countByEditDateAfter(String downloadDate);

    List<Language> findByEditDateAfter(String downloadDate);
}
