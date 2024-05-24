package com.vn.repository;

import com.vn.entities.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Integer> {
    Staff findByEmail(String email);

    Staff findByName(String username);
    Staff findByEmailAndPassword(String email, String password);
}