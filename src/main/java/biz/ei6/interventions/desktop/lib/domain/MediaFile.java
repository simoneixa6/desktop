package biz.ei6.interventions.desktop.lib.domain;

/**
 * @author Eixa6
 */
public class MediaFile {
    
    public String intervention_id;
    public String fileName;
    private String mimeType;
    public String fileData;

    public String getIntervention_id() {
        return intervention_id;
    }

    public void setIntervention_id(String intervention_id) {
        this.intervention_id = intervention_id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String FileName) {
        this.fileName = FileName;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getFileData() {
        return fileData;
    }

    public void setFileData(String fileData) {
        this.fileData = fileData;
    }
}