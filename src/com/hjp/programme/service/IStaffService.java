package com.hjp.programme.service;

import java.util.List;

import com.hjp.programme.util.Page;
import com.hjp.programme.vo.Staff;
import com.hjp.programme.vo.StaffRole;

public interface IStaffService {
	Staff queryStaffByStaffId(String staffId);
	
	List<StaffRole> queryStaffRole(String staffId);
	
	int insertStaff(Staff staff);
	
	void updateStaffPassword(Staff staff);
	
	List<Staff> queryStaffByPage(Page page);
}
