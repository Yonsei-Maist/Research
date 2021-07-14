/**
 * Paging dto
 * @author Mina Kim, Yonsei Univ. Researcher, since 2020.08~
 * @Date 2020.10.19
 */
package kr.ac.yonsei.maist.global.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagingDto {

    private int currentPageNo = 1; // current page number
    private int pageSize = 10;// number of pages in the page list (setting)
    private int recordCountPerPage = 20;// number of posts on one page (setting)
    private long totalRecordCount = 0;// total post
    private long totalPageCount = 0;// total page
    private int firstRecordIndex = 0;// first page index number
    private int lastRecordIndex = 0;// last page index number
    private int firstPageNoOnPageList = 1;// first page number of page list
    private long lastPageNoOnPageList = 1;// last page number in page list

    public long getTotalPageCount() {
        totalPageCount = ((getTotalRecordCount()-1)/getRecordCountPerPage()) + 1;
        return totalPageCount;
    }

    public int getFirstPageNoOnPageList() {
        firstPageNoOnPageList = ((getCurrentPageNo()-1)/getPageSize())*getPageSize() + 1;
        return firstPageNoOnPageList;
    }

    public long getLastPageNoOnPageList() {
        lastPageNoOnPageList = getFirstPageNoOnPageList() + getPageSize() - 1;
        if(lastPageNoOnPageList > getTotalPageCount()){
            lastPageNoOnPageList = getTotalPageCount();
        }
        return lastPageNoOnPageList;
    }

    public int getFirstRecordIndex() {
        firstRecordIndex = (getCurrentPageNo()-1) * getRecordCountPerPage();
        return firstRecordIndex;
    }

    public int getLastRecordIndex() {
        lastRecordIndex = getCurrentPageNo() * getRecordCountPerPage();
        return lastRecordIndex;
    }
}
