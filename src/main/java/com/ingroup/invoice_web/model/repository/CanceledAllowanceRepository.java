package com.ingroup.invoice_web.model.repository;

import com.ingroup.invoice_web.model.entity.CanceledAllowance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CanceledAllowanceRepository extends JpaRepository<CanceledAllowance,Long> {

    CanceledAllowance findByCompanyIdAndCancelAllowanceNumberAndUploadStatus(Integer companyId,String cancelAllowanceNumber, String uploadStatus);
}
