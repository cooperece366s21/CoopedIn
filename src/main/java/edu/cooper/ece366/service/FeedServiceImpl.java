package edu.cooper.ece366.service;

import edu.cooper.ece366.model.*;
import edu.cooper.ece366.store.JobDao;
import edu.cooper.ece366.store.JobStore;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FeedServiceImpl implements FeedService {

    private final JobStore jobStore;

    public FeedServiceImpl(JobStore jobStore) { this.jobStore = jobStore; }

    //final List<String> jobTypesForAll = new Array.asList("Fulltime", "PartTime", "Internship", "Coop");
   /*
    public Feed get_All(final List<String> ids){
        List<JobPosts> jobPostings =
                ids.stream()
                        .map(jobStore::getAll)
                        .map(items ->
                                new JobPostsBuilder()
                                        .jobPostItems(items)
                                        .build())
                        .collect(Collectors.toList());
        return new FeedBuilder().jobPostings(jobPostings).build();
    }
    */
    @Override
    public Feed getFeedForUser(final User user) {
        List<JobPosts> jobPostings =
                user.location().stream()
                        .map(jobStore::getByLocation)
                        .filter(list -> !list.isEmpty())
                        .map(items ->
                                new JobPostsBuilder()
                                        .jobPostItems(items)
                                        .build())
                        .collect(Collectors.toList());

        return new FeedBuilder().jobPostings(jobPostings).build();
    }
    @Override
    public Feed getFeedJobtype (final List<String> types) {
        List<JobPosts> jobPostings =
                types.stream()
                        .map(jobStore::getByJobtype)
                        .filter(list -> !list.isEmpty())
                        .map(items ->
                            new JobPostsBuilder()
                                    .jobPostItems(items)
                                    .build())
                        .collect(Collectors.toList());
        return new FeedBuilder().jobPostings(jobPostings).build();
    }

    //The get feed for user command can be called by: /"user"/#/"feed"
    //Optional for clarity in output: | jq
    @Override
    public Feed getFeedForCompanies(final Company company){
        List<JobPosts> jobPostings =
                company.name()
                        .stream()
                        .map(jobStore::getByCompany)
                        .filter(list -> !list.isEmpty())
                        .map(items ->
                                new JobPostsBuilder()
                                        .jobPostItems(items)
                                        .build())
                        .collect(Collectors.toList());

        return new FeedBuilder().jobPostings(jobPostings).build();
    }


}
