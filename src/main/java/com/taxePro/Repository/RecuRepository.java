package com.taxePro.Repository;

import com.taxePro.Entity.Recu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecuRepository extends JpaRepository<Recu, Long> {
}
