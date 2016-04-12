package com.hjp.programme.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.hjp.programme.util.Page;
import com.hjp.programme.vo.Staff;
import com.hjp.programme.vo.StaffRole;

@Component("staffMapper")
public interface StaffMapper {
	
	Staff queryStaffByStaffId(String staffId);
	
	List<StaffRole> queryStaffRole(String staffId);
	
	int insertStaff(Staff staff);
	
	int insertStaffRole(StaffRole staffRole);
	
	void updateStaffPassword(Staff staff);
	
	List<Staff> queryStaffByPage(Page page);
}
