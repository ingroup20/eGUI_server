package com.ingroup.invoice_web.model.repository;

import com.ingroup.invoice_web.model.entity.Printer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrinterRepository extends JpaRepository<Printer, Integer> {
}
