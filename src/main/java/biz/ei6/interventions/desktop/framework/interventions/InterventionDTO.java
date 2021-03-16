package biz.ei6.interventions.desktop.framework.interventions;

import biz.ei6.interventions.desktop.framework.clients.ClientDTO;
import biz.ei6.interventions.desktop.framework.clients.SiteDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

/**
 *
 * @author Eixa6
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class InterventionDTO {

    String _id;
    String title;
    ClientDTO client;
    String user_id;
    String description;
    List<PeriodDTO> periods;
    SiteDTO address;
    String km;
    String goKm;
    String backKm;
    String billNumber;
    String billDate;
    String paymentType;
    String paymentDate;
    StatusDTO status;
    List<String> medias;
    String deleted;

    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ClientDTO getClient() {
        return client;
    }

    public void setClient(ClientDTO client) {
        this.client = client;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<PeriodDTO> getPeriods() {
        return periods;
    }

    public void setPeriods(List<PeriodDTO> periods) {
        this.periods = periods;
    }
    
    public SiteDTO getAddress() {
        return address;
    }

    public void setAddress(SiteDTO address) {
        this.address = address;
    }

    public String getKm() {
        return km;
    }

    public void setKm(String km) {
        this.km = km;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public StatusDTO getStatus() {
        return status;
    }

    public void setStatus(StatusDTO status) {
        this.status = status;
    }

    public List<String> getMedias() {
        return medias;
    }

    public void setMedias(List<String> medias) {
        this.medias = medias;
    }
    
    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    public String getGoKm() {
        return goKm;
    }

    public void setGoKm(String goKm) {
        this.goKm = goKm;
    }

    public String getBackKm() {
        return backKm;
    }

    public void setBackKm(String backKm) {
        this.backKm = backKm;
    }
}
