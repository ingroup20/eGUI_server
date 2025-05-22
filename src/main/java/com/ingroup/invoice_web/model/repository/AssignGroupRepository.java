package com.ingroup.invoice_web.model.repository;

import com.ingroup.invoice_web.model.entity.AssignGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AssignGroupRepository extends JpaRepository<AssignGroup, Integer> {

    @Query(value = "select * from assign_group where year_month = :yearMonth and company_id = :companyId and printer_id = :printerId and status = 1", nativeQuery = true)
    Optional<AssignGroup> findByCompanyAndPrinterAndInUse(@Param("yearMonth") String yearMonth,
                                                          @Param("companyId") Integer companyId,
                                                          @Param("printerId") Integer printerId);

    @Query(value = "select * from assign_group where ear_month = :yearMonth and company_id = :companyId and printer_id = :printerId and status = 0 order by assignId asc limit 1", nativeQuery = true)
    Optional<AssignGroup> findByCompanyAndPrinterAndPreUse(@Param("yearMonth") String yearMonth,
                                                           @Param("companyId") Integer companyId,
                                                           @Param("printerId") Integer printerId);

    @Query(value = "select * from assign_group where year_month = :yearMonth and company_id = :companyId and printer_id = :printerId and status in (0 or 1) order by status desc , assign_id asc limit 1", nativeQuery = true)
    Optional<AssignGroup> findByCompanyAndPrinterAndAvailable(@Param("yearMonth") String yearMonth,
                                                              @Param("companyId") Integer companyId,
                                                              @Param("printerId") Integer printerId);
}
