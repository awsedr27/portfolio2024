package com.portfolio.common;

public class Paging {

    private int currentPage;         // 현재 페이지 번호
    private int pageSize;            // 한 페이지당 출력할 개수
    private int pageListSize;		 // 한 페이지리스트에 출력할 개수
    private int totalRecordCount;    // 전체 데이터 개수
    private int totalPageCount;      // 전체 페이지 수
    private int startRecordIndex;    // 조회 시작 레코드 인덱스 (0부터 시작)
    private int endRecordIndex;      // 조회 종료 레코드 인덱스 (0부터 시작)
    private int firstPage;           // 페이지 리스트의 첫 페이지 번호
    private int lastPage;            // 페이지 리스트의 마지막 페이지 번호
    private boolean hasPreviousPage; // 이전 페이지 존재 여부
    private boolean hasNextPage;     // 다음 페이지 존재 여부

    public Paging(int currentPage, int pageSize, int pageListSize) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.pageListSize=pageListSize;
    }

    // 전체 데이터 개수 설정 및 계산 메서드
    public void setTotalRecordCount(int totalRecordCount) {
        this.totalRecordCount = totalRecordCount;
        calculatePaging();
    }

    // 페이징 계산 메서드
    private void calculatePaging() {
        // 전체 페이지 수 계산
        totalPageCount = (int) Math.ceil((double) totalRecordCount / pageSize);

        // 현재 페이지 번호 보정
        if (currentPage < 1) {
            currentPage = 1;
        } else if (currentPage > totalPageCount) {
            currentPage = totalPageCount;
        }

        // 페이지 리스트의 첫 페이지 번호
        firstPage = ((currentPage - 1) / pageListSize) * pageListSize + 1;

        // 페이지 리스트의 마지막 페이지 번호
        lastPage = Math.min(firstPage + pageListSize - 1, totalPageCount);

        // 조회 시작 레코드 인덱스 계산
        startRecordIndex = (currentPage - 1) * pageSize;

        // 조회 종료 레코드 인덱스 계산
        endRecordIndex = Math.min(startRecordIndex + pageSize, totalRecordCount) - 1;

        // 이전 페이지 존재 여부
        hasPreviousPage = firstPage > 1;

        // 다음 페이지 존재 여부
        hasNextPage = lastPage < totalPageCount;
    }

    // GETTER 메서드들
    public int getCurrentPage() {
        return currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getTotalRecordCount() {
        return totalRecordCount;
    }

    public int getTotalPageCount() {
        return totalPageCount;
    }

    public int getStartRecordIndex() {
        return startRecordIndex;
    }

    public int getEndRecordIndex() {
        return endRecordIndex;
    }

    public int getFirstPage() {
        return firstPage;
    }

    public int getLastPage() {
        return lastPage;
    }

    public boolean isHasPreviousPage() {
        return hasPreviousPage;
    }

    public boolean isHasNextPage() {
        return hasNextPage;
    }
}
