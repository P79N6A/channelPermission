package com.haier.svc.api.controller.util.http;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class PagingIterator<E> implements Iterator<E> {

	private List<E> objectList = null;
	private int cursor = 0;
	private Long pageNo = 0L;
	protected Long pageSize = 50L;
	
	@Override
	public boolean hasNext() {
		if (this.objectList == null || this.objectList.size() == this.cursor) {
            this.cursor = 0;
            this.objectList = this.getObjectList(pageNo++);
        }
        if (this.objectList == null || this.objectList.size() == 0) {
            return false;
        }
        return true;
	}

	@Override
	public E next() {
		E e = this.objectList.get(this.cursor++);
        return e;
	}

	@Override
	public void remove() {
		throw new RuntimeException("当前迭代器不支持remove功能");
	}
	
	/**
	 * 子类需事先的获取目标集合的抽象方法
	 * @param pageNo 取值：0,1,2,。。。。
	 * @return
	 */
	protected abstract List<E> getObjectList(Long pageNo);

	public List<E> getObjectList1() {
		return objectList;
	}

	public void setObjectList(List<E> objectList) {
		this.objectList = objectList;
	}

	public int getCursor() {
		return cursor;
	}

	public void setCursor(int cursor) {
		this.cursor = cursor;
	}

	public Long getPageNo() {
		return pageNo;
	}

	public void setPageNo(Long pageNo) {
		this.pageNo = pageNo;
	}

	/**
	 * 示例
	 * @param args
	 */
	public static void main(String[] args) {
//		分页迭代器定义
		Iterator<String> iter = new PagingIterator<String>() {
			@Override
			protected List<String> getObjectList(Long pageNo) {
				if(pageNo == 10){
					return null;
				}
				String showText = "[页码="+pageNo+",默认页尺寸"+super.pageSize+"]";
				List<String> list = new ArrayList<String>();
				list.add(showText+"-"+1);
				list.add(showText+"-"+2);
				list.add(showText+"-"+3);
				return list;
			}
		};
//		使用方式1
//		while(iter.hasNext()){
//			System.out.println("获取元素:"+iter.next());
//		}
//		使用方式2		
		for(int i=0;iter.hasNext();i++){
			System.out.println((i+1) + "----" + iter.next());
		}
	}
	
}
