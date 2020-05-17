package br.com.lucas.brewer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.lucas.brewer.model.Cerveja;

/**
 * 
 * @author Lucas Oliveira
 *
 */

@Repository
public interface CervejaRepository extends JpaRepository<Cerveja, Long> {
}
