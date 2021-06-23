package ac.cn.saya.elasticsearch.rest.tools;

/*
 * 名称：分页类
 */

/*
 * 使用说明：
 * 第一步，声明一个分页对象
 * Paging paging =new Paging(); 
 * 第二步，设置每页显示记录的数量
 * paging.setPageSize(pagesize);//每页显示记录的数量
 * 第三步，设置总记录数
 * paging.setDateSum(dateSum);
 * 第四步，根据上面两部的操作，计算总页数
 * paging.setMaxPage();
 * 第五步，设置当前页
 * paging.setPageNow(page);//设置当前的页码
 * 第六步、根据上面接收好的页码，取出该页的数据
 *  list = dao.query((page - 1) * pagesize, pagesize);
 */


/**
 * 注意这里面的页码按照1开始
 * 但是在数据库中是按照0开始，用的时候请注意
 */
public class Paging<T> {
    /**
     * 当前页
     */
    private int pageNow;

    /**
     * 页面显示数据的条数
     */
    private int pageSize;

    /**
     * 总记录数
     */
    private Long dateSum;

    /**
     * 总页数
     */
    private Long totalPage;

    /**
     * 分页后的参数
     */
    private T grid;


    public Long gettotalPage() {
        return totalPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getPageNow() {
        return pageNow;
    }

    public Long getDateSum() {
		return dateSum;
	}


    public void setPageSize(int pageSize) {
        //设置每页显示的记录数据量
        this.pageSize = pageSize;
    }

	public void setDateSum(Long dateSum) {
        //设置总记录数
		this.dateSum = dateSum;
	}

	public void setTotalPage() {//设置总页数
        if (dateSum % pageSize == 0) {
            totalPage = dateSum / pageSize;
        }else {
            totalPage = dateSum / pageSize + 1;
        }
    }

    public void setPageNow(int pageNow) {
        if(pageNow >= 1 && pageNow <= this.totalPage){
            //在范围内
            this.pageNow = pageNow;
        }else{
            //不在范围内
            this.pageNow = 1;
        }
    }

    public Object getGrid() {
        return grid;
    }

    public void setGrid(T grid) {
        this.grid = grid;
    }
}
