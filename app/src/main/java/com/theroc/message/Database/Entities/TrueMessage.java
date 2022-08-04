package com.theroc.message.Database.Entities;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class TrueMessage extends Message implements Serializable {

    private String reason;
    private String lastName;
    private String firstName;
    private String address;

    public TrueMessage() {
    }

    public TrueMessage(String reason, String lastName, String firstName, String address) {
        this.reason = reason;
        this.lastName = lastName;
        this.firstName = firstName;
        this.address = address;
    }

    public TrueMessage(String reason, String lastName, String firstName) {
        this.reason = reason;
        this.lastName = lastName;
        this.firstName = firstName;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @NonNull
    @Override
    public String toString() {
        return reason + " " + lastName + " " + firstName + " " + address;
    }

    public String toStringTransport() {
        return "ΜΕΤΑΚΙΝΗΣΗ " + reason + " " + lastName + " " + firstName + " " + address;
    }
}
