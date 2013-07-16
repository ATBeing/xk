package xk.dao.base;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
public interface BaseDaoI<T> {

	public T get(String hql);

	public T get(String hql, Map<String, Object> params);

	public List<T> find(String hql, Map<String, Object> params);

	public List<T> find(String hql, Map<String, Object> params, Integer page, Integer rows);

	public List<T> find(String hql, Integer page, Integer rows);

	public Long count(String hql, Map<String, Object> params);

	// 以上为自己写的代码
	/**
	 * 保存一个对象
	 * 
	 * @param o
	 * @return
	 */
	public Serializable save(T o);

	/**
	 * 删除一个对象
	 * 
	 * @param o
	 */
	public void delete(T o);

	/**
	 * 更新一个对象
	 * 
	 * @param o
	 */
	public void update(T o);

	/**
	 * 保存或更新对象
	 * 
	 * @param o
	 */
	public void saveOrUpdate(T o);

	/**
	 * 查询
	 * 
	 * @param hql
	 * @return
	 */
	public List<T> find(String hql);
	
	/**
	 * 获得一个对象
	 * 
	 * @param c
	 *            对象类型
	 * @param id
	 * @return Object
	 */
	public T get(Class<T> c, Serializable id);

	/**
	 * select count(*) from 类
	 * 
	 * @param hql
	 * @return
	 */
	public Long count(String hql);

	/**
	 * 执行HQL语句
	 * 
	 * @param hql
	 * @return 响应数目
	 */
	public Integer executeHql(String hql);
	
	public Integer executeHql(String hql,Map<String , Object> params);

}
