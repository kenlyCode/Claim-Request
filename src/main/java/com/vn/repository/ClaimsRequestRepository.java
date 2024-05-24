package com.vn.repository;

import com.vn.entities.ClaimsRequest;
import com.vn.entities.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClaimsRequestRepository extends JpaRepository<ClaimsRequest, Integer> {
    
    List<ClaimsRequest> findByStaffIdAndStatusNot(Integer staffId, Status s);

    List<ClaimsRequest> findByStaffIdAndStatus(Integer staffId, Status status);

    List<ClaimsRequest> findByStatus(Status status);

    List<ClaimsRequest> findByProjectIdAndStatus(Integer projectId, Status status);
}
