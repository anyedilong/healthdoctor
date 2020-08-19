package com.java.until.dba;



import java.util.ArrayList;
import java.util.List;

public class PageModel {


    private int pageNo = 1; // 当前页码
    private int pageSize = 10; // 页面大小，设置为“-1”表示不进行分页（分页无效）
    private long count;// 总记录数，设置为“-1”表示不查询总数
    private long totalPage;// 总页数
    private int numIndex;// 索引
    private List list = new ArrayList();

    public PageModel(){
        this.pageNo = 1;
        this.pageSize = 10;
    }

    public PageModel(int pageNo, int pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
		this.totalPage=(this.count-1)/this.pageSize+1;
	}

	public long getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(long totalPage) {
		this.totalPage =totalPage;
	}

	public int getNumIndex() {
		return this.pageSize * (this.pageNo - 1) + 1;
	}

	public void setNumIndex(int numIndex) {
		this.numIndex =numIndex;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}
}
