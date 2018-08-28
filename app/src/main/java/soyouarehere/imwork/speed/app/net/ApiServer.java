package soyouarehere.imwork.speed.app.net;


import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import soyouarehere.imwork.speed.pager.mine.download.manager.MovieResponse;
import soyouarehere.imwork.speed.pager.mine.download.resouce.ResourceFile;
import soyouarehere.imwork.speed.pager.mine.download.resouce.ResourceRe;

/**
 * 接口
 */

public interface ApiServer {
    /**
     * 获取电影列表
     * */
    @POST("/mobile/movie/getMovieList")
    Observable<BaseEntity<MovieResponse>> getMovieList(@Query("page")String page,@Query("size")String size);
    /**
     * 获取服务器资源  以列表形式展示
     * */
    @GET("/static/file/download/resource")
    Observable<BaseEntity<List<ResourceFile>>>  getServiceResourceList();
}
