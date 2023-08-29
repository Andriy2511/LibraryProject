package com.example.library.filter;

import com.example.library.component.ListPaginationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class ListPaginationFilter extends OncePerRequestFilter {

    private final ListPaginationData listPaginationData;
    private final List<String> allowedUrlList = new ArrayList<>();

    @Autowired
    public ListPaginationFilter(ListPaginationData listPaginationData) {
        this.listPaginationData = listPaginationData;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        changePageAndPageSizeForList(request);
        filterChain.doFilter(request, response);
    }

    /**
     * The method captures the URL from all requests, and if this URL is contained in the allowedUrlList,
     * the method retrieves the 'page' and 'pageSize' parameters and modifies them if they are not null.
     * @param request The object with type HttpServletRequest
     */
    private void changePageAndPageSizeForList(HttpServletRequest request){
        String requestPath = request.getRequestURI();
        if(getAllowedUrlList().contains(requestPath)) {
            String paramPage = request.getParameter("page");
            String paramPageSize = request.getParameter("pageSize");
            setFirstPageIfUserChangePage(requestPath);
            if (paramPage != null) {
                try {
                    Long totalRecords = listPaginationData.getTotalRecords();
                    int page = Integer.parseInt(paramPage) + listPaginationData.getPage();
                    if(page >= 0 && (long) page * listPaginationData.getPageSize() < totalRecords)
                        listPaginationData.setPage(page);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }

            if (paramPageSize != null) {
                try {
                    if(Integer.parseInt(paramPageSize) > 0)
                        listPaginationData.setPageSize(Integer.parseInt(paramPageSize));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
            setLastPageIfPageBiggerThanTotalPage();
        }
    }

    /**
     * Defines the specific path that will capture the 'page' and 'pageSize' parameters.
     * @return The list with allowed URL
     */
    private List<String> getAllowedUrlList(){
        allowedUrlList.add("/admin/showUserList");
        allowedUrlList.add("/admin/showAuthorList");
        allowedUrlList.add("/admin/showBookList");
        allowedUrlList.add("/admin/showOrderList");
        allowedUrlList.add("/admin/showReaderMessages");
        allowedUrlList.add("/admin/showFineList");

        allowedUrlList.add("/user/showReaderOrderList");
        allowedUrlList.add("/user/showReaderMessages");
        allowedUrlList.add("/user/showFineList");

        return allowedUrlList;
    }

    private void setFirstPageIfUserChangePage(String requestPath){
        if (!requestPath.equals(listPaginationData.getLastVisitedPage())) {
            listPaginationData.setPage(0);
            listPaginationData.setLastVisitedPage(requestPath);
        }
    }

    private void setLastPageIfPageBiggerThanTotalPage(){
        Long totalRecords = listPaginationData.getTotalRecords();
        int numberOfRecords = listPaginationData.getPage() * listPaginationData.getPageSize();
        while (numberOfRecords > totalRecords && listPaginationData.getPage() > 0) {
            listPaginationData.setPage(listPaginationData.getPage() - 1);
            numberOfRecords = listPaginationData.getPage() * listPaginationData.getPageSize();
        }
    }
}
