package kr.ac.yonsei.maist.domain.command.dao;

import kr.ac.yonsei.maist.domain.command.domain.Command;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandRepository extends JpaRepository<Command, Integer> {
}
