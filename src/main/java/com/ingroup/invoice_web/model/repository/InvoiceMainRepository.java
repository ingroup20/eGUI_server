package com.ingroup.invoice_web.model.repository;

import com.ingroup.invoice_web.model.entity.InvoiceMain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InvoiceMainRepository extends JpaRepository<InvoiceMain, Long> {

    @Query(value = "SELECT * FROM invoice_main WHERE id = :invoiceId AND upload_status = 'C'", nativeQuery = true)
    Optional<InvoiceMain> findByInvoiceIdAndUploadDone(Long invoiceId);
}
