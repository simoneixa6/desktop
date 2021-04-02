package biz.ei6.interventions.desktop.framework.medias;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 *
 * @author Eixa6
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MediaDTO {

    String id;
    String intervention_id;
    String date;
    String fileName;
    String mimeType;
    String deleted;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIntervention_id() {
        return intervention_id;
    }

    public void setIntervention_id(String intervention_id) {
        this.intervention_id = intervention_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }
}
