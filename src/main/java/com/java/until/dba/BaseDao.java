package com.java.until.dba;

import com.java.until.UUIDUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

public class BaseDao<R extends JpaRepository, B extends BaseDomain> extends EntityManagerDao {
	
	/** JpaRepository操作类 */
	@Inject
	protected R repository;
	


	/**
	 * 获取单条数据
	 * 
	 * @param id
	 * @return
	 */
	public B get(String id) {
		Optional optional = repository.findById(id);
		if (optional.isPresent())
			return (B) optional.get();
		return null;
	}

	/**
	 * 查询列表数据
	 * 
	 * @return
	 */
	public List<B> findList() {
		return repository.findAll();
	}

	/**
	 * 保存数据（插入或更新）
	 * 
	 */
	@Transactional(readOnly = false)
	public void save(B entity) {
		if(StringUtils.isBlank(entity.getId())){
			entity.setId(UUIDUtil.getUUID());
		}
		repository.saveAndFlush(entity);
		
	
	}

	/**
	 * 删除数据
	 * 
	 */
	@Transactional(readOnly = false)
	public void delete(String id) {
		repository.deleteById(id);
	}

	/**
	 * 删除数据
	 * 
	 * @param entity
	 */
	@Transactional(readOnly = false)
	public void delete(B entity) {
		repository.delete(entity);
	}

	/**
	 * 判断是否存在
	 * @param id
	 * @return
	 */
	public Boolean exists(String id) {
		return repository.existsById(id);
	}
}
