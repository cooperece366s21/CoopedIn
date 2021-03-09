package edu.cooper.ece366.store;

import edu.cooper.ece366.model.Company;
import edu.cooper.ece366.model.Job;

import java.util.List;

public interface JobStore {

    // Job getByType(Job.JobType jobType);
    List<Job> getByCompany(Company company);
    List<Job> getByLocation(String location);
}
