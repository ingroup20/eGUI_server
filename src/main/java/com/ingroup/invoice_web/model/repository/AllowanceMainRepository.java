package com.ingroup.invoice_web.model.repository;

import com.ingroup.invoice_web.model.entity.AllowanceMain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AllowanceMainRepository extends JpaRepository<AllowanceMain, Long> {
}
