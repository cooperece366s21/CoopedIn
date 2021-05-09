package edu.cooper.ece366.store;

import edu.cooper.ece366.model.Job;

import java.util.List;

public interface JobStore {

    //Job getByType(Job.JobType jobType);

    List<Job> getByCompany(String company);

    //List<Job> getAll(String jobTypesForAll);

    List<Job> getByLocation(String location);

    List<Job> getByJobtype(String jobType);
}
