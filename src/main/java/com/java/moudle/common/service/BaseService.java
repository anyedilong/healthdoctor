package com.java.moudle.common.service;


import com.java.until.dba.BaseDomain;

public interface BaseService<T extends BaseDomain> {

	/**
	 * 保存数据（插入或更新）
	 *
	 * @param
	 */
	public void save(T entity);

	/**
	 * 删除数据
	 *
	 * @param
	 */
	public void delete(String id);

	public T get(String id);

}
