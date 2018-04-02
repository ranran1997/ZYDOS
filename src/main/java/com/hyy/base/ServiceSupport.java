package com.hyy.base;

import com.hyy.utils.CollectionsUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by HengYanYan on 2016/4/15  17:28
 */
public abstract class ServiceSupport<T extends AbstractItem> implements Service<T> ,Serializable {

    public abstract Dao<T> getDao();

    private static final long serialVersionUID = -1040390621007755334L;
    protected final Log logger = LogFactory.getLog(getClass());


    @SuppressWarnings("unchecked")
    private Class<T> getBeanClass() {
        return (Class<T>) ((ParameterizedType) this.getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public Integer save(T t){
        return getDao().save(t);
    }

    public boolean saveOrUpdate(T t){
        return getDao().saveOrUpdate(t);
    }

    /**
     * 根据id删除实体
     */
    public boolean delete(Integer id){
        getDao().delete(id);
        return true;
    }

    /**
     * 查询全部
     * @return
     */
    public List<T> findAll(){
        return getDao().findAll();
    }

    /**
     * 根据主键id进行查询
     * @param id
     * @return
     */
    public T findById(Integer id){
        return getDao().findById(id);
    }

    /**
     * 根据主键id的集合进行查询
     * @param ids
     * @return
     */
    public List<T> findByIds(List<Integer> ids){
        List<T> tList = new ArrayList<T>();
        for(Integer id :ids){
            T t = this.findById(id);
            if(t != null){
                tList.add(t);
            }
        }
        return tList;
    }

    /**
     * 根据主键id获取同类型中的子id的集合
     * @param id 通过“parentId”字段关联
     * @return
     */
    public List<Integer> findChildIdsById(Integer id){
        List<Integer> ids = new ArrayList<Integer>();

        //getBeanClass();
        //采用深度优先搜索用栈（stack）策略遍历查询所有childId；
        Stack<Integer> stack = new Stack<Integer>();
        stack.push(id);
        while (!stack.empty()) {
            Integer childId = stack.pop();

            ids.add(childId);

            List<T> children = this.findChildListByPId(childId);
            for (T child : children) {
                stack.push(child.getId());
            }
        }
        //
        // ids.remove(0);子属性不包括自身id在内
        return ids;//子属性包括自身id在内
    }

    /**
     * 根据主键id获取同类型中的子集合
     * @param id 通过“parentId”字段关联
     * @return
     */
    public List<T> findChildrenById(Integer id){
        List<T> children = new ArrayList<T>();

        T t = findById(id);

        Stack<T> childStack = new Stack<T>();
        childStack.push(t);

        while (!childStack.empty()){
            T child = childStack.pop();
            children.add(child);

            List<T> childList = this.findChildListByPId(child.getId());
            childStack.addAll(childList);
        }

        return children;
    }

    public List<T> findChildListByPId(Integer parentId) {
        Criterion criterion = Restrictions.eq("parentId", parentId);
        return getDao().find(criterion);
    }

    /**
     * 是否有相同的,如果有 返回该相同数据,默认验证为name
     * 如果用name验证可以使用该方法
     * @param propertyName
     * @param value
     * @return
     */
    public T hasSame(String propertyName, String value) {
        return hasSame(new Pair<String, String>(propertyName, value));
    }

    public T hasSame(Pair<?, ?>... pairs) {
        List<T> finds = hasSames(pairs);
        return CollectionsUtil.isEmpty(finds) ? null : finds.get(0);
    }

    /**
     * 是否有相同的,如果有 返回该相同数据
     * @param pairs
     * @return
     */
    public List<T> hasSames(Pair<?, ?>... pairs) {
        List<Criterion> criterionList = new ArrayList<Criterion>();
        for (Pair<?, ?> pair : pairs) {
            criterionList.add(Restrictions.eq((String) pair.getFirst(), pair.getSecond()));
        }
        Criterion[] criterions = new Criterion[criterionList.size()];
        criterionList.toArray(criterions);
        List<T> finds = getDao().find(criterions);
        if (CollectionsUtil.isEmpty(finds)) return new ArrayList<T>();
        return finds;
    }
}
