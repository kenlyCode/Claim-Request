package com.vn.auth;

import com.vn.entities.Staff;
import com.vn.entities.Working;
import com.vn.repository.StaffRepository;
import com.vn.repository.WorkingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CustomStaffDetailService implements UserDetailsService {

    @Autowired
    StaffRepository staffRepository;

    @Autowired
    WorkingRepository workingRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Staff staff = staffRepository.findByName(username);
        if (staff == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return new CustomStaffDetail(staff) {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                Set<SimpleGrantedAuthority> grantedAuthorities = new HashSet<>();
                String roleStaff = staff.getRole().name();
                grantedAuthorities.add(new SimpleGrantedAuthority(roleStaff));

                List<Working> workings = new ArrayList<>();
                workings = workingRepository.findByStaffId(staff.getId());

                for (Working working : workings) {
                    grantedAuthorities.add(new SimpleGrantedAuthority(working.getRoleStaff().name()));
                }

                return grantedAuthorities;

            }
        };

    }
}
