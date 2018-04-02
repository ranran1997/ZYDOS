package com.hyy.base;


import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

import java.sql.SQLException;
import java.util.List;


/**
 * 功能描述：数据库操作接口
 */
public interface Dao<T extends AbstractItem> {

	Integer save(T t);
    /**
     * 添加或修改一条记录
     *
     * @param t
     * @return
     */

    boolean saveOrUpdate(T t);

	//根据id删除实体
	boolean delete(Integer id);

    /**
     * 查询全部
     */
    List<T> findAll();

    /**
     * 查询全部,使用排序
     */
    List<T> findAll(Order[] orders);


    /**
     * 使用where查询
     *
     * @param criterion
     * @return
     */

    List<T> find(final Criterion criterion);

    /**
     * 使用where查询
     *
     * @param criteriones
     * @return
     */

    List<T> find(final Criterion[] criteriones);

    /**
     * 使用where查询
     *
     * @param criteriones
     * @param orders
     * @return
     */

    List<T> find(final Criterion[] criteriones, final Order... orders);

    /**
     * 使用id查找
     *
     * @param id
     * @return
     */

    T findById(Integer id);

/*-----------------------分界线-----------------------*/


    /**
     * 使用id删除
     * @param id
     * @return
     *//*

	boolean delete(Integer id);

	*//**
     * 使用id删除
     * @param ids
     * @return
     *//*

	boolean delete(Integer[] ids);

	*//**
     * 删除该项
     * @param t
     * @return
     *//*

	boolean delete(T t);




	
	*//**
     * 使用id查找有延迟加载的数据
     * @param id
     * @return
     *//*

	T load(Integer id);



	*//**
     * 使用pager查询
     * @param pager
     * @return
     *//*

	List<T> find(Pager pager);

	*//**
     * 使用pager查询,并包含query参数
     * @param pager
     * @param queryMap
     * @return
     *//*

	List<T> find(Pager pager, Map<String, Object> queryMap);

	
	*//**
     * 使用pager查询
     * @param pager
     * @return
     *//*

	List<T> find(Pager pager, Order... order);

	*//**
     * 查找所有
     * @param order
     * @return
     *//*

	List<T> findAll(final Order... order);


	*//**
     * 使用pager查询,并包含query参数
     * @param pager
     * @param queryMap
     * @param order
     * @return
     *//*

	List<T> find(Pager pager, Map<String, Object> queryMap, Order... order);

*//*
    *//**//**
     * 使用where查询
     * @param criterion
     * @param orders
     * @return
     *//**//*

	List<T> find(final Criterion criterion, final Order... orders);


	*//**
     * 使用pager查询,并包含子查询对象和参数
     * @param pager
     * @param criteriones
     * @param orders
     * @return
     *//*

	List<T> find(final Pager pager, final Criterion[] criteriones, final Order... orders);

	*//**
     * 使用pager查询,并包含子查询对象和参数
     * @param pager
     * @param criterion
     * @param orders
     * @return
     *//*

	List<T> find(final Pager pager, final Criterion criterion, final Order... orders);

	
	*//**
     * 使用pager查询
     * @param pager
     * @return
     *//*

	List<T> load(Pager pager);
	
	*//**
     * 使用pager查询,并包含query参数
     * @param pager
     * @param queryMap
     * @return
     *//*

	List<T> load(Pager pager, Map<String, Object> queryMap);

	
	*//**
     * 使用pager查询
     * @param pager
     * @return
     *//*

	List<T> load(Pager pager, Order... order);
	
	*//**
     * 查找所有
     * @param order
     *//*

	List<T> loadAll(final Order... order);


	*//**
     * 使用pager查询,并包含query参数
     * @param pager
     * @param queryMap
     * @param order
     * @return
     *//*

	List<T> load(Pager pager, Map<String, Object> queryMap, Order... order);
	
	*//**
     * 使用where查询
     * @param criteriones
     * @param orders
     * @return
     *//*

	List<T> load(final Criterion[] criteriones, final Order... orders);

	*//**
     * 使用where查询
     * @param criterion
     * @param orders
     * @return
     *//*

	List<T> load(final Criterion criterion, final Order... orders);

	*//**
     * 使用where查询
     * @param criterion
     * @return
     *//*
	List<T> load(final Criterion criterion);

	
	
	*//**
     * 使用pager查询,并包含子查询对象和参数
     * @param pager
     * @param criteriones
     * @param orders
     * @return
     *//*

	List<T> load(final Pager pager, final Criterion[] criteriones, final Order... orders);

	*//**
     * 使用pager查询,并包含子查询对象和参数
     * @param pager
     * @param criterion
     * @param orders
     * @return
     *//*

	List<T> load(final Pager pager, final Criterion criterion, final Order... orders);

	
	*//**
     * 使用实体查找,屏蔽null
     * @param t
     * @return
     *//*

	List<T> findByItem(T t);

	
	*//**
     * 使用实体查找,屏蔽null
     * @param t
     * @return
     *//*

	List<T> findByItem(Pager pager, T t);

	*//**
     * 使用实体查找,屏蔽null
     * @param t
     * @param orders
     * @return
     *//*

	List<T> findByItem(T t, Order... orders);

	*//**
     * 使用实体查找,屏蔽null
     * @param pager
     * @param t
     * @param orders
     * @return
     *//*

	List<T> findByItem(final Pager pager, final T t, final Order... orders);
	
	*//**
     * 使用条件查找,返回总数
     * @param criteriones
     * @return
     *//*

	int getTotal(final Criterion... criteriones);
	
	*//**
     * 按分页和hql 查询数据
     * <br/>
     * <font style='color:red;'>存在问题:当hql中有group  by语句时,只能有一个group by条件,如果是两个group by条件,自己去写底层,不要调用这个方法</font>
     * @param pager
     * @param hql
     * @return
     *//*

	List<T> findByHql(final Pager pager, final String hql);

	*//**
     * 按分页和hql 查询数据
     * <br/>
     * <font style='color:red;'>存在问题:当hql中有group  by语句时,只能有一个group by条件,如果是两个group by条件,自己去写底层,不要调用这个方法</font>
     * @param hql
     * @return
     *//*

	List<T> findByHql(final String hql);
	
	*//**
     * 按分页和hql 和条件查询数据
     * <br/>
     * <font style='color:red;'>存在问题:当hql中有group  by语句时,只能有一个group by条件,如果是两个group by条件,自己去写底层,不要调用这个方法</font>
     * @param pager
     * @param hql
     * @param param
     * @return
     *//*

	List<T> findByHql(final Pager pager, final String hql, final List<?> param);
	
	*//**
     * 按hql查询总数
     * @param hql
     * @return
     *//*

	int getTotal(final String hql);
	
	*//**
     * 按hql和条件查询总数
     * @param hql
     * @return
     *//*

	int getTotal(final String hql, final List<?> param);
	
	*//**
     * 按分页和sql 和条件查询数据
     * @param pager
     * @param sql
     * @param scalars 返回数据类型
     * @return
     *//*

	List<?> findBySql(final Pager pager, final String sql,
					  final Pair<String, BasicType>... scalars);

	*//**
     * 按sql和条件查询总数
     *
     * @param sql
     * @return
     *//*

	int getTotalBySql(final String sql);
	
	*//**
     * 按class查询 , 正常情况禁止使用,可以在一些非正常操作中使用
     @param clazz
     @param criteriones
      * @return
     *//*

	List<?> findByClazz(Class<?> clazz, Criterion... criteriones);
	
	*//**
     * 删除本实体中的其他实体, 正常情况禁止使用,可以在一些非正常操作中使用
     * @param obj
     * @return
     *//*

	boolean deleteOtherObj(Object obj);
	
	*//**
     * 函数可以级联了。。。  所以可以用级联的实体类的树形进行排序什么的。。。
     * @param pager 分页类
     * @param criteriones 查询条件
     * @param criterias   创造级联类别名
     * @param loadSubClass 是否加载子类  一般都是false
     * @param orders  排序规则
     * @return
     *//*

	List<T> find(final Pager pager, final Criterion[] criteriones, final List<Pair<String, String>> criterias, final boolean loadSubClass,
				 final Order... orders);
	
	*//**
     * ...
     * @param criteriones
     * @param criterias
     * @return
     *//*

	int getTotal(final Criterion[] criteriones,
				 final List<Pair<String, String>> criterias);

	*//**
     * 自定义级联查询
     * @param pager 分页信息
     * @param criteriones 查询条件
     * @param criteriAsMap 级联的查询条件
     * @param orders 排序
     * @return 要查询的信息
     *//*
*//*	List<T> findCascade(final Pager pager, final List<Criterion> criteriones,
						final Map<String, SubSelectParam> criteriAsMap,
						final Order... orders);*//*

	*//**
     * 自定义级联查询
     * @param criteriones 查询条件
     * @param orders 排序
     * @return 要查询的信息
     *//*
	List<T> findCascade(List<Criterion> criteriones, Order... orders);

	*//**
     * 取出其中的自定义级联信息，如果已经有的话则覆盖
     * @param list 需要取级联信息的list
     * @return 取出自定义级联信息后的list
     * @throws SQLException
     *//*
	List<T> fetchCascade(List<T> list) throws SQLException;
*/
    /**
     * 取出其中的自定义级联信息，如果已经有的话则覆盖
     * @param list 需要取级联信息的list
     * @param criteriAsMap 级联的查询条件
     * @return 取出自定义级联信息后的list
     * @throws SQLException
     */
/*
	List<T> fetchCascade(List<T> list, Map<String, SubSelectParam> criteriAsMap) throws SQLException;
*/
}
