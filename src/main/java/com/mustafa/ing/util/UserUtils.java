package com.mustafa.ing.util;

import com.mustafa.ing.enums.UserRole;
import com.mustafa.ing.model.Customer;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@UtilityClass
public class UserUtils {

    public UserDetails getUserAuthentication() {
        return (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public boolean hasUserAdminRole() {
        return getUserAuthentication().getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals(UserRole.ADMIN.getName()));
    }

    public boolean hasUserCustomerRole() {
        return getUserAuthentication().getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals(UserRole.CUSTOMER.getName()));
    }

    public String getUserName() {
        return getUserAuthentication().getUsername();
    }

    public boolean validateUser(Customer customer) {
        if (UserUtils.hasUserAdminRole()) {
            return true;
        }

        if(UserUtils.hasUserCustomerRole()) {
            return customer.getUsername().equals(UserUtils.getUserName());
        }

        return false;
    }
}
