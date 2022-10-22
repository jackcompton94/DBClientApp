package model;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @Author Jack Compton
 */

public class Division {

    private int divisionId;
    private String division;
    private Date createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;
    private int countryId;

    public Division(int divisionId, String division, Date createDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy, int countryId) {
        this.divisionId = divisionId;
        this.division = division;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.countryId = countryId;
    }

    public int getDivisionId() {
        return divisionId;
    }

    public String getDivision() {
        return division;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public int getCountryId() {
        return countryId;
    }

    /**
     * overrides default Object Class behavior when added to a ComboBox
     *
     */
    @Override
    public String toString(){   // overrides default Object Class behavior when added to a ComboBox
        return (getDivision());
    }
}
