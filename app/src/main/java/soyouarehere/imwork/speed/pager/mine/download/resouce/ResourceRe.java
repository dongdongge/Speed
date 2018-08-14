package soyouarehere.imwork.speed.pager.mine.download.resouce;
import com.google.gson.Gson;

/**
 * Created by li.xiaodong on 2018/8/6.
 */

public class ResourceRe{
    /**
     * 文件名
     * */
    private String fileName;
    /**
     * 文件地址
     * */
    private String fileUrl;
    /**
     * 文件大小
     * */
    private long contentLength;
    /**
     * 文件缩略图
     * */
    private String imageThumbnail;
    /**
     * 文件类型
     * */
    private String fileType;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public ResourceRe() {
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public long getContentLength() {
        return contentLength;
    }

    public void setContentLength(long contentLength) {
        this.contentLength = contentLength;
    }

    public String getImageThumbnail() {
        return imageThumbnail;
    }

    public void setImageThumbnail(String imageThumbnail) {
        this.imageThumbnail = imageThumbnail;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
}
