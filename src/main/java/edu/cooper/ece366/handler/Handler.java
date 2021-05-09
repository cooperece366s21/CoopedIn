package edu.cooper.ece366.handler;

import edu.cooper.ece366.model.Company;
import edu.cooper.ece366.model.Feed;
import edu.cooper.ece366.model.Job;
import edu.cooper.ece366.model.User;
import edu.cooper.ece366.service.FeedService;
import edu.cooper.ece366.store.ApplicationStorePostgres;
import edu.cooper.ece366.store.CompanyStore;
import edu.cooper.ece366.store.JobStore;
import edu.cooper.ece366.store.UserStore;
import spark.Request;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Handler {

  private final UserStore userStore;
  private final FeedService feedService;
  private final CompanyStore companyStore;

  public Handler(UserStore userStore, FeedService feedService, ApplicationStorePostgres appStorePostgres, CompanyStore companyStore) {
    this.userStore = userStore;
    this.feedService = feedService;
    this.companyStore = companyStore;
  }

  public User getUser(Request request) {
    String userId = request.params(":userId");
    return userStore.get(userId);
  }
  public Company getCompany(Request request) {
    String companyId = request.params(":companyId");
    return companyStore.get(companyId);
  }

  public Feed getFeed(Request request) {
    User user = getUser(request);
    return feedService.getFeedForUser(user);
  }
/*
  public Feed getAllFeed(Request request) {
    List<String> ids = new ArrayList(Arrays.asList("FullTime","Coop","PartTime","Internship"));
    System.out.println("getAllFeed() success!");
    return feedService.get_All(ids);
  }
*/

  public Feed getFeedByJobType(Request request) {
    String jt = request.params(":job_type");
    List<String> types = new ArrayList<String>();
    types.add(jt);
    return feedService.getFeedJobtype(types);
    //return feedService.getFeedJobtype(List.of(jt));
  }

  public Feed getFeedByCompany(Request request) {
    Company company = getCompany(request);
    return feedService.getFeedForCompanies(company);
  }

}
