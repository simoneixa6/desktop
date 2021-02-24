/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biz.ei6.interventions.desktop.lib.domain;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Eixa6
 */
public class InterventionDTO {

    String _id;
    String title;
    String client_id;
    String description;
    //ArrayList<PeriodDTO> periods;
    String address;
    String km;
    String billNumber;
    String billDate;
    String paymentType;
    String paymentDate;
    String status;
    //ArrayList<String> medias;
    String deleted;

    
    public void setInterventionDTO(Intervention intervention) {
        this.setId(intervention.getId());
        this.setTitle(intervention.getTitle());
        this.setClient_id(intervention.getClient_id());
        this.setDescription(intervention.getDescription());

       // this.setPeriods(new ArrayList<PeriodDTO>());

        this.setAddress(intervention.getAddress());
        this.setKm(intervention.getKm());
        this.setBillNumber(intervention.getBillNumber());
        this.setBillDate(intervention.getBillDate());
        this.setPaymentType(intervention.getPaymentType());
        this.setPaymentDate(intervention.getPaymentDate());
        this.setStatus(intervention.getStatus());
       // this.setMedias(new ArrayList<String>());
        this.setDeleted(String.valueOf(intervention.getDeleted()));
    }

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

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    public List<PeriodDTO> getPeriods() {
//        return periods;
//    }
//
//    public void setPeriods(ArrayList<PeriodDTO> periods) {
//        this.periods = periods;
//    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

//    public List<String> getMedias() {
//        return medias;
//    }
//
//    public void setMedias(ArrayList<String> medias) {
//        this.medias = medias;
//    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }
}
