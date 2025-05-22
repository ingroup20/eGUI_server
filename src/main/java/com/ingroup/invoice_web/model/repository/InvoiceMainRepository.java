package com.ingroup.invoice_web.model.repository;

import com.ingroup.invoice_web.model.entity.InvoiceMain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceMainRepository extends JpaRepository<InvoiceMain, Long> {
}
