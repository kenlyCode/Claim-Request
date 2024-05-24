package com.vn.repository;

import com.vn.entities.Role;
import com.vn.entities.Working;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkingRepository extends JpaRepository<Working, Integer> {
    Working findByStaffIdAndProjectId(Integer staffId, Integer projectId);

    List<Working> findByStaffIdAndRoleStaff(Integer id, Role role);

    Working findByProjectIdAndRoleStaff(Integer id, Role role);

    List<Working> findByStaffId(Integer id);
}
