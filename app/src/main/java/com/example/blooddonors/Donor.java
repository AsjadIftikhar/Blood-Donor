package com.example.blooddonors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;

public class Donor implements Serializable {
    private String name;
    private String address;
    private String availability;
    private Boolean status;
    private String number;
    private String bloodGroup;

    public Donor() { }

    public Donor(String name, Boolean status, String address, String availability, String number, String bloodGroup) {
        this.name = name;
        this.status = status;
        this.address = address;
        this.availability = availability;
        this.number = number;
        this.bloodGroup = bloodGroup;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public Hashtable<String, String> ObjToHashTable() {
        Hashtable<String, String> hashtable = new Hashtable<>();
        hashtable.put("name", name);
        hashtable.put("address", address);
        hashtable.put("availability", availability);
        if (getStatus()) {
            hashtable.put("status", "True");
        } else {
            hashtable.put("status", "False");
        }
        hashtable.put("number", number);
        hashtable.put("bloodGroup", bloodGroup);

        return hashtable;
    }

    public static Donor HashTableToObj(Hashtable<String, String> hashtable) {
        Donor donor = new Donor(hashtable.get("name"), Boolean.FALSE, hashtable.get("address"), hashtable.get("availability"), hashtable.get("number"), hashtable.get("bloodGroup"));
        donor.setStatus(hashtable.get("status").equals("True"));

        return donor;
    }

    public static ArrayList<Donor> load(DonorDbInterface donorDbInterface) {
      return donorDbInterface.getDonorsList();
    }

    public static void save(Donor donor, DonorDbInterface donorDbInterface) {
        donorDbInterface.addDonor(donor);
    }

    @Override
    public String toString() {
        return "Donor{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", availability='" + availability + '\'' +
                ", status=" + status +
                ", number='" + number + '\'' +
                ", bloodGroup='" + bloodGroup + '\'' +
                '}';
    }
}
