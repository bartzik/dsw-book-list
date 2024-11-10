package com.dsw_pin.book_list.repositories;

import com.dsw_pin.book_list.model.Sessao;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SessaoRepository extends JpaRepository<Sessao, String> {
    Optional<Sessao> findTopByIdOrderByDataCriacaoDesc(String sessionId);
}
