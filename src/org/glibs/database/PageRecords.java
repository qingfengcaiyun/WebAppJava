package org.glibs.database;

import java.util.List;
import java.util.Map;

import org.glibs.text.Tools;

public class PageRecords {
	private int pageSize; // 每页显示的记录数目
	private int recordsCount; // 总记录数目
	private int currentPage; // 当前是第几页
	private int startIndex; // 开始于第几条记录
	private int pageCount; // 一共有几页
	private int firstPage; // 第一页
	private int prevPage; // 上一页
	private int nextPage; // 下一页
	private int lastPage; // 最后一页
	private List<Map<String, Object>> pageResult; // 分页记录集
	private String pageJSON; // 分页记录集JSON

	public PageRecords() {
		this.firstPage = 1;
	}

	public void setBaseParam() {
		this.pageCount = this.recordsCount / this.pageSize;
		if (this.recordsCount % this.pageSize > 0) {
			this.pageCount++;
		}

		this.firstPage = 1;

		this.prevPage = this.currentPage - 1;
		if (this.prevPage < 1) {
			this.prevPage = 1;
		}

		this.nextPage = this.currentPage + 1;
		if (this.nextPage > this.pageCount) {
			this.nextPage = this.pageCount;
		}

		this.lastPage = this.pageCount;

		this.startIndex = this.pageSize * (this.currentPage - 1);
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getRecordsCount() {
		return recordsCount;
	}

	public void setRecordsCount(int recordsCount) {
		this.recordsCount = recordsCount;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getFirstPage() {
		return firstPage;
	}

	public void setFirstPage(int firstPage) {
		this.firstPage = firstPage;
	}

	public int getPrevPage() {
		return prevPage;
	}

	public void setPrevPage(int prevPage) {
		this.prevPage = prevPage;
	}

	public int getNextPage() {
		return nextPage;
	}

	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}

	public int getLastPage() {
		return lastPage;
	}

	public void setLastPage(int lastPage) {
		this.lastPage = lastPage;
	}

	public List<Map<String, Object>> getPageResult() {
		return pageResult;
	}

	public void setPageResult(List<Map<String, Object>> pageResult) {
		this.pageResult = pageResult;
		this.pageJSON = Tools.ListMaptoJSON(this.pageResult);
	}

	public String getPageJSON() {
		return pageJSON;
	}
}
