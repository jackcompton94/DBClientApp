package model;

import java.sql.Timestamp;

/**
 * @Author Jack Compton
 * Customer object used in Add/Edit Appointments and Customer
 */

public class Customer {

    private int customerId;
    private String customerName;
    private String address;
    private String postalCode;
    private String phone;
    private Timestamp createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;
    private int divisionId;

    public Customer(int customerId, String customerName, String address, String postalCode, String phone, Timestamp createDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy, int divisionId) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.divisionId = divisionId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getAddress() {
        return address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getPhone() {
        return phone;
    }

    public Timestamp getCreateDate() {
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

    public int getDivisionId() {
        return divisionId;
    }

    /**
     * overrides default Object Class behavior when added to a ComboBox
     *
     */
    @Override
    public String toString(){   // overrides default Object Class behavior when added to a ComboBox
        return ("ID [" + getCustomerId() + "] " + getCustomerName());
    }
}
