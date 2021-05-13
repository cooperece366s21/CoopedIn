package edu.cooper.ece366.store;

import edu.cooper.ece366.model.Job;
import edu.cooper.ece366.model.User;
import org.jdbi.v3.core.Jdbi;

import java.util.List;

public class JobStorePostgres implements JobStore {

    // for testing
    public static void main(String[] args) {

        JobStorePostgres jobStorePostgres = new JobStorePostgres(
                CoopedInJdbi.create("jdbc:postgresql://localhost:5432/c0mpany"));

        String company = "Adobe";
        String location = "NY";
        String job_type = "FullTime";

        try {
            //List<Job> job = jobStorePostgres.getByCompany(company);
            //List<Job> job = jobStorePostgres.getByLocation(location);
            List<Job> job = jobStorePostgres.getByJobtype(job_type);
            System.out.println("success");
            System.out.println(job);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Could not fetch job");
        }

    }

    private final Jdbi jdbi;

    public JobStorePostgres (final Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    @Override
    public boolean addJob(String id, String company, String jobTitle, String location, String jobType) {
        try {jdbi.useHandle(
                handle -> {
                    handle.attach(JobDao.class).insertJob(id, company, location, jobTitle, jobType);
                });
            return true;} catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /* @Override
        public List<Job> getAll(final String id) {
            return (List<Job>) jdbi.withHandle(handle -> handle.attach(JobDao.class).getAll(id));
        }*/
    @Override
    public List<Job> getByCompany(String company) {
        return (List<Job>) jdbi.withHandle(handle -> handle.attach(JobDao.class).getByCompany(company));
    }

    @Override
    public List<Job> getByLocation(String location) {
        return (List<Job>) jdbi.withHandle(handle -> handle.attach(JobDao.class).getByLocation(location));
    }

    @Override
    public List<Job> getByJobtype(String jobType) {
        return (List<Job>) jdbi.withHandle(handle -> handle.attach(JobDao.class).getByJobtype(jobType));
    }


}
