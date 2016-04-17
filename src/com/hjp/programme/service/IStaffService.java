package com.hjp.programme.service;

import java.util.HashMap;
import java.util.List;

import com.hjp.programme.util.CCHException;
import com.hjp.programme.util.Page;
import com.hjp.programme.vo.Role;
import com.hjp.programme.vo.Staff;
import com.hjp.programme.vo.StaffRole;

public interface IStaffService {
	Staff queryStaffByStaffId(String staffId);
	
	List<StaffRole> queryStaffRole(String staffId);
	
	void insertStaff(Staff staff, StaffRole staffRole) throws CCHException;
	
	void updateStaffPassword(Staff staff);
	
	List<Staff> queryStaffByPage(Page page);
	
	List<Role> queryRole(HashMap<String, Object> cond);
	
	List<StaffRole> queryStaffRoleByCond(HashMap<String, Object> cond);
}
