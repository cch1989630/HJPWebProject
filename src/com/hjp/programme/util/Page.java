package com.hjp.programme.util;

import java.util.List;
/**
 * 
 * 用来封装参数的Page
 * @param <T>
 */
public class Page <T>{
	/**
	 * 总页数
	 */
	private Integer countPage;
	/**
	 * 当前页
	 */
	private Integer currPage = 1;
	/**
	 * 每页显示记录数
	 */
	private Integer pageSize=5;
	/**
	 * 开始位置
	 */
	private Integer startIndex;
	/**
	 * 记录的数据
	 */
	private List<T> entryList;
	/**
	 * 总记录数
	 */
	private Integer countRecord = -1 ;
	/**
	 * Oracle数据库的结束索引
	 */
	private Integer endIndex;
	/**
	 * 上一页
	 */
	private Integer lastPage;
	
	/**
	 * 下一页
	 */
	private Integer nextPage;
	
	
	/**
	 * 参数类
	 */
	private T t;
	
	public T getT() {
		return t;
	}

	public Integer getCountRecord() {
		return countRecord;
	}

	public void setCountRecord(Integer countRecord) {
		this.countRecord = countRecord;
	}

	public void setT(T t) {
		this.t = t;
	}
	/**
	 * MySQL数据库的构造函数
	 * @param currPage
	 * @param pageSize
	 */
	public Page(Integer currPage, Integer pageSize) {
		this.currPage = currPage;
		this.pageSize = pageSize;
		this.startIndex = (currPage-1)*pageSize;
	}
	public Integer getEndIndex() {
		return endIndex;
	}

	public void setEndIndex(Integer endIndex) {
		this.endIndex = endIndex;
	}

	/**
	 * Oracle数据库的构造函数
	 * @param currPage
	 * @param pageSize
	 * @param startIndex
	 */
	public Page(Integer currPage, Integer pageSize, Integer startIndex) {
		this.currPage = currPage;
		this.pageSize = pageSize;
		this.startIndex = startIndex;
		this.endIndex = startIndex+pageSize;
	}
	
	public Page() {}

	public List<T> getEntryList() {
		return entryList;
	}

	public Integer getCountPage() {
		return countPage = ((countRecord%pageSize)!=0) ? countRecord/pageSize+1 : countRecord/pageSize;
	}

	public void setCountPage(Integer countPage) {
		this.countPage = countPage;
	}

	public Integer getCurrPage() {
		return currPage;
	}

	public void setCurrPage(Integer currPage) {
		this.currPage = currPage;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(Integer startIndex) {
		this.startIndex = startIndex;
	}

	public void setEntryList(List<T> entryList) {
		this.entryList = entryList;
	}
	
	public Integer getLastPage() {
		if(this.currPage > 1){
			this.lastPage = this.currPage-1 ;
		}else{
			this.lastPage = currPage;
		}
		return this.lastPage ;
	}

	public void setLastPage(Integer lastPage) {
		this.lastPage = lastPage;
	}

	public Integer getNextPage() {
		if((this.currPage+1) > getCountPage() ){
			this.nextPage = this.currPage ;
		}else{
			this.nextPage = this.currPage+1 ;
		}
		return this.nextPage ;
	}

	public void setNextPage(Integer nextPage) {
		this.nextPage = nextPage;
	}
	
}
