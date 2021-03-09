package biz.ei6.interventions.desktop.framework.clients;


import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

/**
 *
 * @author Eixa6
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ClientDTO {
    String _id;
    String civility;
    String name;
    String lastname;
    String company;
    String companyStatus;
    String phone;
    String mail;
    List<SiteDTO> addresses;
    String firstVisitDate;
    String how;
    String why;
    String problematic;
    String deleted;

    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public String getCivility() {
        return civility;
    }

    public void setCivility(String civility) {
        this.civility = civility;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCompanyStatus() {
        return companyStatus;
    }

    public void setCompanyStatus(String companyStatus) {
        this.companyStatus = companyStatus;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public List<SiteDTO> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<SiteDTO> addresses) {
        this.addresses = addresses;
    }

    public String getFirstVisitDate() {
        return firstVisitDate;
    }

    public void setFirstVisitDate(String firstVisitDate) {
        this.firstVisitDate = firstVisitDate;
    }

    public String getHow() {
        return how;
    }

    public void setHow(String how) {
        this.how = how;
    }

    public String getWhy() {
        return why;
    }

    public void setWhy(String why) {
        this.why = why;
    }

    public String getProblematic() {
        return problematic;
    }

    public void setProblematic(String problematic) {
        this.problematic = problematic;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }
}
