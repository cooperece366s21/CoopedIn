package edu.cooper.ece366.store;

public interface ApplicationStore {

    boolean add(String appID, String jobID, String userID, String companyID, String CV);
}
