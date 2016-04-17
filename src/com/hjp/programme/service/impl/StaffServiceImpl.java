package com.hjp.programme.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hjp.programme.mapper.StaffMapper;
import com.hjp.programme.service.IStaffService;
import com.hjp.programme.util.CCHException;
import com.hjp.programme.util.Page;
import com.hjp.programme.vo.Role;
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

	@Transactional(rollbackFor=CCHException.class)
	@Override
	public void insertStaff(Staff staff, StaffRole staffRole) throws CCHException {
		
		Staff existStaff = staffMapper.queryStaffByStaffId(staff.getStaffId());
		if (existStaff != null) {
			throw new CCHException("0", "该[" + staff.getStaffId() + "]已存在，请换另外的工号");
		}
		
		try {
			staffMapper.insertStaff(staff);
			//将选择的权限入库
			staffRole.setStaffId(staff.getStaffId());
			staffMapper.insertStaffRole(staffRole);
			//将登录权限入库
			staffRole.setRoleCode("ROLE_LOGIN");
			staffMapper.insertStaffRole(staffRole);
		} catch (Exception e) {
			throw new CCHException("0", "新增用户失败");
		}
	}

	@Override
	public void updateStaffPassword(Staff staff) {
		staffMapper.updateStaffPassword(staff);
	}

	@Override
	public List<Staff> queryStaffByPage(Page page) {
		return staffMapper.queryStaffByPage(page);
	}

	@Override
	public List<Role> queryRole(HashMap<String, Object> cond) {
		return staffMapper.queryRole(cond);
	}

	@Override
	public List<StaffRole> queryStaffRoleByCond(HashMap<String, Object> cond) {
		return staffMapper.queryStaffRoleByCond(cond);
	}

}
