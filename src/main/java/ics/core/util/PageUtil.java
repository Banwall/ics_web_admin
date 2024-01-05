package ics.core.util;

import java.util.HashMap;

import lombok.ToString;

@ToString
public class PageUtil {
	private int nowPage;		// 현재 페이지를 기억할 변수
	private int totalCount;	// 총 데이터 개수를 기억할 변수
	
	private int listCount;	// 한 화면에 보여줄 목록의 개수 - 10개
	private int pageGroup;	// 한 화면에 보여줄 페이지 수 - 5페이지
	
	private int totalPage;	// 총 페이지 수
	private int startPage;	// 시작 페이지
	private int endPage;		// 끝 페이지
	
	
	public int start;		//	검색 시작 번호
	public int end;			//	검색 끝 번호
	
	//	페이지당 검색 시작 번호와 끝 번호를 계산해서 반환하는 함수
	public int getStartNum() {
		
		return (nowPage-1) * listCount +1;
	}
	
	public int getEndNum() {
		
		return nowPage * listCount;
	}
	
	// 누군가가 이 클래스를 new 시키면서 필수 정보를 알려주기로 하자.
	public PageUtil(int nowPage, int totalCount, int listCount, int pageGroup) {
		this.nowPage = nowPage;
		this.totalCount = totalCount;
		this.listCount = listCount;
		// 이따가 이 부분을 수정해서 결과를 확인하도록 하자.
		/*listCount = 4;*/
		this.pageGroup = pageGroup;
		
		// 아래쪽에 만든 함수를 하나씩 호출해서 나머지 3개의 정보를 만들어놓자.
		calcTotalPage();
		calcStartPage();
		calcEndPage();
	}
	// 총 페이지수를 계산할 함수
	private void calcTotalPage() {
		// 한 화면에 10개씩 출력하기로 했으므로 총 개수가 100개이면 10페이지가 필요하고 
		// 총 개수가 101개이면 11페이지가 필요하다.
		totalPage = (totalCount % listCount) == 0 ? (totalCount / listCount) : (totalCount / listCount) + 1;
	}
	
	// 시작 페이지를 계산할 함수
	private void calcStartPage() {
		// 원리
		// 현재 페이지가 몇 번째 그룹인지를 알아내고,
		int tempGroup = (nowPage - 1) / pageGroup + 1;
		// 그 그룹의 시작 페이지를 구한다.
		startPage = (tempGroup - 1) * pageGroup + 1;
	}
	
	// 마지막 페이지를 계산할 함수
	private void calcEndPage() {
		// 시작 페이지 + pageGroup - 1을 하면 된다.
		endPage = startPage + pageGroup - 1;
		// 다만 마지막 페이지는 이 결과가 아닐 수 있다.
		if(endPage > totalPage) {
			endPage = totalPage;
		}
	}
	
	// 이 정보는 view에서 JSTL을 이용해서 사용해야 하므로 
	public int getNowPage() {
		return nowPage;
	}
	public void setNowPage(int nowPage) {
		this.nowPage = nowPage;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getListCount() {
		return listCount;
	}
	public void setListCount(int listCount) {
		this.listCount = listCount;
	}
	public int getPageGroup() {
		return pageGroup;
	}
	public void setPageGroup(int pageGroup) {
		this.pageGroup = pageGroup;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getStartPage() {
		return startPage;
	}
	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}
	public int getEndPage() {
		return endPage;
	}
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
	
	public HashMap getPageInfo() {
		HashMap map = new HashMap();
		map.put("totalPage", this.totalPage);
		map.put("startPage", this.startPage);
		map.put("endPage", this.endPage);
		map.put("pageGroup", this.pageGroup);
		map.put("nowPage", this.nowPage);
		return map;
	}
}

