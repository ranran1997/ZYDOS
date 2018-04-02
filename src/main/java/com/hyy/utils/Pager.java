package com.hyy.utils;




import java.net.URLEncoder;
import java.io.UnsupportedEncodingException;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Order;

import java.util.*;

/**
 * Created by HengYanYan on 2016/4/11  23:54
 */
public class Pager implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    protected static final Logger logger = Logger.getLogger(Pager.class);

    private int page = 1;
    private int pageSize = 10;
    private int totalNum = 0;
    private int totalPage = 0;

    private List<Order> listSort = new ArrayList<Order>();

    /*private List<QueryMap> queryMaps = new ArrayList<QueryMap>();*/

    private Map<String, Object> query = new HashMap<String, Object>();

    public Pager() {
    }

    public boolean putSort(String column, boolean isAsc) {
        return listSort.add(isAsc ? Order.asc(column) : Order.desc(column));
    }

    /*public boolean putQueryMaps(String columnName, Object value, Prompt.QueryOption queryOption) {
        QueryMap queryMap = new QueryMap();
        queryMap.columnName = columnName;
        queryMap.value = value;
        queryMap.queryOption = queryOption;
        return queryMaps.add(queryMap);
    }

    public List<QueryMap> getQueryMaps() {
        return queryMaps;
    }

    public void setQueryMaps(List<QueryMap> queryMaps) {
        this.queryMaps = queryMaps;
    }
    */
    /**
     * 返回所有记录
     *
     * @return
     */
    public static Pager getMaxPager() {
        return new Pager(1, Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    /**
     * 返回记录只有一条
     *
     * @return
     */
    public static Pager getMinPager() {
        return new Pager(1, 1, Integer.MAX_VALUE);
    }

    public Pager(int page, int totalNum) {
        init(page, pageSize, totalNum);
    }

    public Pager(int page, int pageSize, int totalNum) {
        init(page, pageSize, totalNum);
    }

    public void init(int page, int pageSize, int totalNum) {
        this.page = page;
        this.pageSize = pageSize;
        this.totalNum = totalNum;
        calculatePage();
    }

    private void calculatePage() {
        if (totalNum % pageSize == 0)
            this.totalPage = totalNum / pageSize;
        else
            this.totalPage = totalNum / pageSize + 1;
        if (page < 1) page = 1;
        if (page > totalPage) page = totalPage;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        if (pageSize < 60) {//最多允许一次查30条记录
            this.pageSize = pageSize;
        } else {
            this.pageSize = 60;
        }
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
        calculatePage();
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getFirstRow() {
        return (page - 1) * pageSize;
    }

    public String toString() {
        return ObjectUtil.toString(this);
    }

    public Map<String, Object> getQuery() {
        return query;
    }

    public void setQuery(Map<String, Object> query) {
        this.query = query;
    }

    public List<Order> getListSort() {
        return listSort;
    }

    public void setListSort(List<Order> listSort) {
        this.listSort = listSort;
    }

    public String getQueryString() {
        if (!CollectionsUtil.isEmpty(query)) {
            Iterator<Map.Entry<String, Object>> iterator = query.entrySet().iterator();
            StringBuffer sb = new StringBuffer(100);
            boolean first = true;
            while (iterator.hasNext()) {
                Map.Entry<String, Object> entry = iterator.next();
                try {

                    if (!ObjectUtil.isEmpty(entry.getValue())) {
                        if (entry.getValue().getClass().isArray()) {
                            Object[] objs = (Object[]) entry.getValue();
                            for (Object obj : objs) {
                                sb.append(StringUtils.join(new String[]{"query[", entry.getKey(), "]", "=", URLEncoder.encode(obj.toString(), "UTF-8")}));
                                sb.append("&");
                                first = false;
                            }
                        } else {
                            sb.append(StringUtils.join(new String[]{"query[", entry.getKey(), "]", "=", URLEncoder.encode(entry.getValue().toString(), "UTF-8")}));
                            sb.append("&");
                            first = false;
                        }
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    logger.warn("分页包含错误参数!");
                }
            }
            if (!first) {
                sb.deleteCharAt(sb.length() - 1);
                return sb.toString();
            }
        }
        return StringUtils.EMPTY;
    }


  /*  public static class QueryMap {
        public String columnName;
        public Object value;
        public Prompt.QueryOption queryOption;
    }*/

}
