package com.hyy.base;

import javax.jws.WebMethod;
import java.util.List;

/**
 * Created by HengYanYan on 2016/4/16  19:14
 */



/**
 * 功能描述：service的基类
 * <p/>
 * 2010-12-30  上午11:44:14
 *
 * @param <T>
 */
public interface Service<T extends AbstractItem> {

    Integer save(T t);
    /**
     * 添加或修改一条记录
     *
     * @param t
     * @return
     */
    @WebMethod(operationName = "save_or_update_t")
    boolean saveOrUpdate(T t);

    /**
     * 根据id删除实体
     */
    boolean delete(Integer id);

    /**
     * 查询全部
     * @return
     */
    List<T> findAll();
    /**
     * 根据主键id进行查询
     * @param id
     * @return
     */
    T findById(Integer id);

    /**
     * 根据主键id的集合进行查询
     * @param ids
     * @return
     */
    List<T> findByIds(List<Integer> ids);

    /**
     * 根据主键id获取同类型中的子id的集合
     * @param id
     * @return
     */
    List<Integer> findChildIdsById(Integer id);

    List<T> findChildrenById(Integer id);


}

