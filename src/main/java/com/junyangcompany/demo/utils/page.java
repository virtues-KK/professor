package com.junyangcompany.demo.utils;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * author:pan le
 * Date:2019/5/5
 * Time:11:35
 */
public abstract class page<T> implements Iterator<List<T>>, Iterable<List<T>> {
    /**
     * 原集合
     */
    protected List<T> data;

    /**
     * 当前页
     */
    protected int pageNo;

    /**
     * 该字段仅用作迭代每一分页的游标
     */
    protected int cursor;

    /**
     * 总页数
     */
    protected int totalPage;

    /**
     * 每页条数
     */
    protected int pageSize;

    /**
     * 总数据条数
     */
    protected int totalCount;

    /**
     * 双参数构造
     * @author lihong10 2017年12月2日 上午10:42:30
     * @param data
     * @param pageSize 分页大小
     */
    public page(List<T> data, int pageSize) {
        this(data, 1, pageSize);
    }

    /**
     * 三参数构造
     * @param data 被分页的链表
     * @param pageNo 页码
     * @param pageSize 分页大小
     */
    public page(List<T> data, int pageNo, int pageSize) {
        if (data == null || data.isEmpty()) {
            throw new IllegalArgumentException("data can not be empty!");
        }

        if (pageSize <= 0) {
            throw new IllegalArgumentException("pageSize must >= 1");
        }

        this.data = data;
        this.totalCount = data.size();
        this.pageSize = pageSize;
        this.totalPage = (totalCount + pageSize - 1) / pageSize;
        if (pageNo <= 0) {
            pageNo = 1;
        } else if (pageNo > this.totalPage) {
            pageNo = totalPage;
        }
        this.pageNo = pageNo;
        this.cursor = 0;
    }

    /**
     * 返回迭代器对象
     * @author lihong10 2017年12月3日 上午10:42:30
     * @return
     */
    @Override
    public final Iterator<List<T>> iterator() {
        return this;
    }

    @Override
    public void remove() {
    }

    /**
     * @author lihong10 2017年12月3日 上午10:42:30
     * 得到pageNo表示的那一页数据
     * @return
     */
    public final List<T> getPagedList() {
        check();
        int fromIndex = (pageNo - 1) * pageSize;
        if (fromIndex >= data.size()) {
            return Collections.emptyList();//空数组
        }
        if (fromIndex < 0) {
            return Collections.emptyList();//空数组
        }
        int toIndex = pageNo * pageSize;
        if (toIndex >= data.size()) {
            toIndex = data.size();
        }
        return data.subList(fromIndex, toIndex);
    }

    /**
     * @author lihong10 2017年12月3日 上午10:42:30
     * 根据页数获取指定分页
     * @author lihong10 2017年11月30日 下午15:42:30
     * @param pageNo
     * @return
     */
    public final List<T> getPage(int pageNo)  {
        check();
        if (pageNo <= 0) {
            pageNo = 1;
        } else if (pageNo > totalPage) {
            pageNo = totalPage;
        }

        int fromIndex;
        int toIndex;
        if (pageNo * pageSize < totalCount) {// 判断是否为最后一页
            toIndex = pageNo * pageSize;
            fromIndex = toIndex - pageSize;
        } else {
            toIndex = totalCount;
            fromIndex = pageSize * (totalPage - 1);
        }

        List<T> objects = null;
        if (data != null && data.size() > 0) {
            objects = data.subList(fromIndex, toIndex);
        }

        return objects;
    }

    /**
     * 重置游标，默认重置为0
     * @author lihong10 2017年12月3日 上午10:42:30
     * @return
     */
    public abstract  void  reset();

    /**
     * 重置游标操作
     * @author lihong10 2017年12月3日 上午10:42:30
     * @param cursor
     * @return
     */
    public abstract void reset(int cursor);

    /**
     * 判断某个方法是否允许被调用
     * @author lihong10 2017年12月3日 上午10:42:30
     * @return
     */
    public abstract void check();

    public int getPageSize() {
        return pageSize;
    }

    public List<T> getData() {
        return data;
    }

    public int getPageNo() {
        return pageNo;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public int getTotalCount() {
        return totalCount;
    }
}
