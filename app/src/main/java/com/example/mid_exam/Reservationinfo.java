package com.example.mid_exam;

public class Reservationinfo {

    String from,to,departuredate,returndate,passengers,cabinclass,paymentmethod;

    public Reservationinfo(){}

    public Reservationinfo(String from, String to, String departuredate, String returndate, String passengers, String cabinclass, String paymentmethod) {
        this.from = from;
        this.to = to;
        this.departuredate = departuredate;
        this.returndate = returndate;
        this.passengers = passengers;
        this.cabinclass = cabinclass;
        this.paymentmethod = paymentmethod;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getDeparturedate() {
        return departuredate;
    }

    public void setDeparturedate(String departuredate) {
        this.departuredate = departuredate;
    }

    public String getReturndate() {
        return returndate;
    }

    public void setReturndate(String returndate) {
        this.returndate = returndate;
    }

    public String getPassengers() {
        return passengers;
    }

    public void setPassengers(String passengers) {
        this.passengers = passengers;
    }

    public String getCabinclass() {
        return cabinclass;
    }

    public void setCabinclass(String cabinclass) {
        this.cabinclass = cabinclass;
    }

    public String getPaymentmethod() {
        return paymentmethod;
    }

    public void setPaymentmethod(String paymentmethod) {
        this.paymentmethod = paymentmethod;
    }
}
