package com.ingroup.invoice_web.model.repository;

import com.ingroup.invoice_web.model.entity.AllowanceMain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AllowanceMainRepository extends JpaRepository<AllowanceMain, Long> {

    AllowanceMain findByCompanyIdAndAllowanceNumber(Integer companyId, String allowanceNumber);

    Optional<AllowanceMain> findByIdAndProcessStatusAndUploadStatus(Long allowanceId, String processStatus, String uploadStatus);

}
