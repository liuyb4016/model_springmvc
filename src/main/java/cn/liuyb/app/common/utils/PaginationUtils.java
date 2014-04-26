package cn.liuyb.app.common.utils;

/*分页工具类，记录索引从0开始，页数索引从1开始，
total: 总记录数
totalPage: 总页数
pageSize: 每页记录数
currentPage: 当前页，从1开始索引
startPosition: 起始位置，从0开始索引
*/
public class PaginationUtils {
    public static int computeTotalPage(int total, int pageSize) {
        return total / pageSize + (total % pageSize > 0 ? 1 : 0);
    }
    
    public static int computeStartPosition(int currentPage, int pageSize) {
        return pageSize * (currentPage - 1);
    }
    
}
