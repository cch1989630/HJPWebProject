package com.hjp.programme.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hjp.programme.mapper.StaffMapper;
import com.hjp.programme.service.IStaffService;
import com.hjp.programme.util.Page;
import com.hjp.programme.vo.Staff;
import com.hjp.programme.vo.StaffRole;

@Service(value="staffService")
public class StaffServiceImpl implements IStaffService {
	
	@Resource(name="staffMapper")  
    private StaffMapper staffMapper;

	@Override
	public Staff queryStaffByStaffId(String staffId) {
		return staffMapper.queryStaffByStaffId(staffId);
	}

	@Override
	public List<StaffRole> queryStaffRole(String staffId) {
		return staffMapper.queryStaffRole(staffId);
	}

	@Override
	public int insertStaff(Staff staff) {
		staffMapper.insertStaff(staff);
		StaffRole staffRole = new StaffRole();
		staffRole.setStaffId(staff.getStaffId());
		staffRole.setRoleCode("ROLE_LOGIN");
		staffMapper.insertStaffRole(staffRole);
		return 1;
	}

	@Override
	public void updateStaffPassword(Staff staff) {
		staffMapper.updateStaffPassword(staff);
	}

	@Override
	public List<Staff> queryStaffByPage(Page page) {
		return staffMapper.queryStaffByPage(page);
	}

}
