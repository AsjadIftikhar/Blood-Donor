package com.example.blooddonors;

import java.util.ArrayList;

public interface DonorDbInterface {

    public ArrayList<Donor> getDonorsList();

    public void addDonor(Donor donor);
}
