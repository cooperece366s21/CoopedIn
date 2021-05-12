package edu.cooper.ece366.store;

import edu.cooper.ece366.model.*;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class JobStoreImpl implements JobStore {

    private static final Map<String, Job> jobMap;

    static {
        List<Job> jobs =
                List.of(
                        new JobBuilder().jobType(Job.JobType.FullTime)
                                .id("1")
                                .company("Adobe")
                                .jobTitle("R&D Engineer")
                                .available(true)
                                .location("NY").build(),
                        new JobBuilder().jobType(Job.JobType.FullTime)
                                .id("2")
                                .company("Adobe")
                                .jobTitle("SW Engineer")
                                .available(true)
                                .location("NY").build(),
                        new JobBuilder().jobType(Job.JobType.FullTime)
                                .id("3")
                                .company("Adobe")
                                .jobTitle("Marketing Manager")
                                .available(true)
                                .location("NY").build(),
                        new JobBuilder().jobType(Job.JobType.FullTime)
                                .id("4")
                                .company("Adobe")
                                .jobTitle("SW Engineer")
                                .available(true)
                                .location("NJ").build(),
                        new JobBuilder().jobType(Job.JobType.Coop)
                                .id("5")
                                .company("Amazon")
                                .jobTitle("Mechanical Engineer")
                                .available(true)
                                .location("NJ").build(),
                        new JobBuilder().jobType(Job.JobType.PartTime)
                                .id("6")
                                .company("Amazon")
                                .jobTitle("Quality Engineer")
                                .available(true)
                                .location("NJ").build(),
                        new JobBuilder().jobType(Job.JobType.Internship)
                                .id("7")
                                .company("Amazon")
                                .jobTitle("Backend Engineer")
                                .available(true)
                                .location("NJ").build(),
                        new JobBuilder().jobType(Job.JobType.FullTime)
                                .id("8")
                                .company("NYU")
                                .jobTitle("Professor")
                                .available(true)
                                .location("NY").build(),
                        new JobBuilder().jobType(Job.JobType.FullTime)
                                .id("9")
                                .company("Aetna")
                                .jobTitle("Lawyer")
                                .available(true)
                                .location("NJ").build());
        //Ella: Monday afternoon: change the way job is mapped to hashmap
        jobMap = jobs.stream().collect(Collectors.toMap(Job::id, Function.identity()));
    }

    public JobStoreImpl() {}

    //@Override
    //public Job get(final Locations location) { return jobMap.get(location); }
    //List<String> jobTypesForAll = {"Fulltime", "PartTime", "Internship", "Coop"};
    /*
    @Override
    public List<Job> getAll(final String ids) {
        System.out.println("Here in JobStoreImpl!");
        return jobMap.values().stream()
                .filter(jobs -> ids.equals(jobs.id()))
                .collect(Collectors.toList());
    }*/

    @Override
    public List<Job> getByLocation(final String location) {
        return jobMap.values().stream()
                .filter(jobs -> location.equals(jobs.location()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Job> getByJobtype(final String jobtype) {
        return jobMap.values().stream()
                .filter(jobs -> Job.JobType.fromDbValue(jobtype).equals(jobs.location()))
                .collect(Collectors.toList());
    }

    //this is feed for the company

    public List<Job> getByCompany(final String company) {
        return jobMap.values().stream()
                .filter(jobs -> company.equals(jobs.company()))
                .collect(Collectors.toList());
    }

    public boolean addJob(final String id, final String company,
                              final String jobTitle, final String location,
                              final String job_type) {
        // create new user
        if(jobMap.containsKey(id)) {
            System.out.println("Create another id!");
            return false;
        }
        Job.JobType jobType=Job.JobType.valueOf(job_type);
        Job newJob = new JobBuilder()
                .jobType(jobType)
                .id(id)
                .company(company)
                .jobTitle(jobTitle)
                .available(true)
                .location(location)
                .build();
        /*3/10:I want to only compare the variables beside the id; so that this msg will show up when
        the added new job has the other information (besides id) similar to one that already existed;
        but it seems like we need to exclude id out of the job variables*/
        /*if(jobMap.containsValue(newJob)){
            System.out.println("Job already existed!");
            return false;
        }*/
        // store new job
        jobMap.put(id, newJob);

        return (jobMap.containsKey(id));
    }
}
