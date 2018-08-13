package soyouarehere.imwork.speed.pager.mine.download.resouce;

import com.google.gson.Gson;

public class ResourceFile {
    private String name;
    private String fileType;
    private long fileLength;
    private String url;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public long getFileLength() {
        return fileLength;
    }

    public void setFileLength(long fileLength) {
        this.fileLength = fileLength;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


}
