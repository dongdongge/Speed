package soyouarehere.imwork.speed.pager.mine.download.downloading;

/**
 * Created by li.xiaodong on 2018/7/25.
 */
/*
*  http://dl188.80s.im:920/1807/我不是药神纪录片/我不是药神纪录片.mp4
*  http://dl23.80s.im:920/1601/让子弹飞/让子弹飞_hd.mp4Z
*  http://dl05.80s.im:920/1506/黑客帝国[国语配音版]/黑客帝国[国语配音版]_bd.mp4Z
*
*
* */
/**
 * @desc
 * @author li.xiaodong
 * @time 2018/7/25 15:44
 */
public class MovieModule {

    /**
     * 电影地址 eg: http://dl188.80s.im:920/1807/我不是药神纪录片/我不是药神纪录片.mp4
     * */
    String url;
    /**
     * 电影名字
     * */
    String name;
    /**
     * 电影文件的总大小  eg:kb
     * */
    long total;
    /**
     * 当前下载的进度
     * */
    long currentProgress;
    /**
     * 电影的展示图片
     * */
    String imgageUrl;
    /**
     * 电影在手机中的物理地址
     * */
    String pathUrl;
    /**
     *还剩余多长时间
     * */
    String time;


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getCurrentProgress() {
        return currentProgress;
    }

    public void setCurrentProgress(long currentProgress) {
        this.currentProgress = currentProgress;
    }

    public String getImgageUrl() {
        return imgageUrl;
    }

    public void setImgageUrl(String imgageUrl) {
        this.imgageUrl = imgageUrl;
    }

    public String getPathUrl() {
        return pathUrl;
    }

    public void setPathUrl(String pathUrl) {
        this.pathUrl = pathUrl;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
