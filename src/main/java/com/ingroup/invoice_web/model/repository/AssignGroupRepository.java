package com.ingroup.invoice_web.model.repository;

import com.ingroup.invoice_web.model.entity.AssignGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AssignGroupRepository extends JpaRepository<AssignGroup, Integer> {

    Optional<AssignGroup> findByYearMonthAndCompanyIdAndPrinterIdAndStatus(String yearMonth, Integer companyId, Integer printerId, Integer status);

    Optional<AssignGroup> findFirstByYearMonthAndCompanyIdAndPrinterIdAndStatusOrderByAssignIdAsc(String yearMonth, Integer companyId, Integer printerId, Integer status);

    Optional<AssignGroup> findFirstByYearMonthAndCompanyIdAndStatusOrderByStatusDescAssignIdAsc(String yearMonth, Integer companyId, Integer status);

}
