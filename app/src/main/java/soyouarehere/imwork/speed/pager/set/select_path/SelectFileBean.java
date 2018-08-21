package soyouarehere.imwork.speed.pager.set.select_path;

import com.google.gson.Gson;
import java.util.List;

public class SelectFileBean {

    private String fileName;
    private String updateTime;
    private String fileType;
    private String currentFilePath;
    private String childFileNumber;
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public String getChildFileNumber() {
        return childFileNumber;
    }

    public void setChildFileNumber(String childFileNumber) {
        this.childFileNumber = childFileNumber;
    }

    public String getCurrentFilePath() {
        return currentFilePath;
    }

    public void setCurrentFilePath(String currentFilePath) {
        this.currentFilePath = currentFilePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
}
