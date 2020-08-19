package com.java.moudle.system.service;


import java.util.List;

import com.java.moudle.common.service.BaseService;
import com.java.moudle.system.domain.SysMenu;


public interface SysMenuService extends BaseService<SysMenu>{

	List<SysMenu> getMenuTree(String userId) throws Exception;
  
}
