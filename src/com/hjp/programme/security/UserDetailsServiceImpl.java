package com.hjp.programme.security;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.hjp.programme.mapper.StaffMapper;
import com.hjp.programme.vo.Staff;
import com.hjp.programme.vo.StaffRole;

public class UserDetailsServiceImpl implements UserDetailsService {

	@Resource(name = "staffMapper")
	private StaffMapper staffMapper;
	
	/**
	 * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
	 */
	public UserDetails loadUserByUsername(String username)throws UsernameNotFoundException {
		Staff staff = staffMapper.queryStaffByStaffId(username);
	    if(staff==null){
	    	throw new UsernameNotFoundException(username);
	    }
		User user = new User(staff.getStaffId(), staff.getPassword(), true, true, true, true, getGrantedAuthorities(staff));
		return user;
	}
	
	 private ArrayList<GrantedAuthority> getGrantedAuthorities(Staff staff) {
		ArrayList<GrantedAuthority> array = new ArrayList<GrantedAuthority>();
		List<StaffRole> roles = staffMapper.queryStaffRole(staff.getStaffId()); 
        if(roles != null) {
            for(StaffRole role : roles) {  
            	array.add(new SimpleGrantedAuthority(role.getRoleCode()));
            }  
        }
	    return array;  
	 }

}
