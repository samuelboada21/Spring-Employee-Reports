package com.gestion.util.pagination;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;

/**
 *
 * @author SamuelBoada
 */
public class PageRender<T> {

    private String url;
    private Page<T> page;
    private int totalPages;
    private int numElementsByPage;
    private int CurrentPage;
    private List<PageItem> pages;

    public PageRender(String url, Page<T> page) {
        this.url = url;
        this.page = page;
        this.pages = new ArrayList<PageItem>();

        numElementsByPage = 5;
        totalPages = page.getTotalPages();
        CurrentPage = page.getNumber() + 1;

        int desde;
        int hasta;
        if (totalPages <= numElementsByPage) {
            desde = 1;
            hasta = totalPages;
        } else {
            if (CurrentPage <= numElementsByPage / 2) {
                desde = 1;
                hasta = numElementsByPage;
            } else if (CurrentPage >= totalPages - numElementsByPage / 2) {
                desde = totalPages - numElementsByPage + 1;
                hasta = numElementsByPage;
            } else {
                desde = CurrentPage - numElementsByPage / 2;
                hasta = numElementsByPage;
            }
        }
        for (int i = 0; i < hasta; i++) {
            pages.add(new PageItem(desde+i, CurrentPage == desde + i));
        }
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getCurrentPage() {
        return CurrentPage;
    }

    public void setCurrentPage(int CurrentPage) {
        this.CurrentPage = CurrentPage;
    }

    public List<PageItem> getPages() {
        return pages;
    }

    public void setPages(List<PageItem> pages) {
        this.pages = pages;
    }
    
    public boolean isLast(){
        return page.isLast();
    }
    
    public boolean isHasNext(){
        return page.hasNext();
    }
    
    public boolean isHasPrevious(){
        return page.hasPrevious();
    }

}
