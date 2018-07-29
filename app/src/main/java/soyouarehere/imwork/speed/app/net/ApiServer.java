package soyouarehere.imwork.speed.app.net;


import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;
import soyouarehere.imwork.speed.pager.mine.download.manager.MovieResponse;

/**
 * 接口
 * Created by tang.wangqiang on 2018/4/9.
 */

public interface ApiServer {
    /**
     * 获取电影列表
     * */
    @POST("/mobile/movie/getMovieList")
    Observable<BaseEntity<MovieResponse>> getMovieList(@Query("page")String page,@Query("size")String size);

}
