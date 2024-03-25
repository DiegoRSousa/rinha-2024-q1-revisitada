package com.diego.rinha;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    @QueryHints({ @QueryHint(name = "jakarta.persistence.lock.timeout", value = "5000") })
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select c from Cliente c where c.id = :id")
    Optional<Cliente> findByIdLock(Long id);

}
