package soyouarehere.imwork.speed.pager.mine.download.downloading;

public class FileModule {


    /**
     * 文件地址
     */
    String url;
    /**
     * 文件名字
     */
    String name;
    /**
     * 文件的总大小  eg:kb
     */
    long total;
    /**
     * 当前下载的进度
     */
    long currentProgress;
    /**
     * 文件的展示图片
     */
    String imageUrl;
    /**
     * 文件在手机中的物理地址
     */
    String pathUrl;
    /**
     * 还剩余多长时间
     */
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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
