package edu.cooper.ece366;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.cooper.ece366.handler.Handler;
import edu.cooper.ece366.model.Job;
import edu.cooper.ece366.service.FeedServiceImpl;
import edu.cooper.ece366.store.ApplicationStorePostgres;
import edu.cooper.ece366.store.CompanyStorePostgres;
import edu.cooper.ece366.store.CoopedInJdbi;
import edu.cooper.ece366.store.JobStorePostgres;
import edu.cooper.ece366.store.UserStorePostgres;
import io.norberg.automatter.gson.AutoMatterTypeAdapterFactory;
import org.jdbi.v3.core.Jdbi;
import spark.Spark;

//import static edu.cooper.ece366.store.JobStoreImpl.addJob;
import static spark.Spark.before;
import static spark.Spark.get;
import static spark.Spark.initExceptionHandler;
import static spark.Spark.options;

public class App {
    public static void main(String[] args) {
        Gson gson =
                new GsonBuilder().registerTypeAdapterFactory(new AutoMatterTypeAdapterFactory()).create();

        initExceptionHandler(Throwable::printStackTrace);

        String jdbcUrl = "jdbc:postgresql://localhost:5432/c0mpany";

        Jdbi jdbi = CoopedInJdbi.create(jdbcUrl);
        UserStorePostgres userStorePostgres = new UserStorePostgres(jdbi);
        CompanyStorePostgres companyStorePostgres = new CompanyStorePostgres(jdbi);
        JobStorePostgres jobStorePostgres = new JobStorePostgres(jdbi);
        ApplicationStorePostgres appStorePostgres = new ApplicationStorePostgres(jdbi);


        Handler handler =
                new Handler(
                        userStorePostgres,
                        new FeedServiceImpl(jobStorePostgres),
                        appStorePostgres,
                        companyStorePostgres);

        get("/ping", (req, res) -> "OK");
        get("/user/:userId", (req, res) -> handler.getUser(req), gson::toJson);
        Spark.get("/user/:userId/feed", (req, res) -> handler.getFeed(req), gson::toJson);
        Spark.get("/company/:companyId/feed", (req, res) -> handler.getFeedByCompany(req), gson::toJson);
        //iterate four times in curl, eg.: curl -s localhost:4567/job/FullTime/feed localhost:4567/job/PartTime/feed
        // localhost:4567/job/Internship/feed localhost:4567/job/Coop/feed | jq
        Spark.get("/job/:job_type/feed", (req, res) -> handler.getFeedByJobType(req), gson::toJson);


        // POST command to insert a new user
        Spark.post("/newUser", (req, res) -> {
            String id = req.queryParams("id");
            String name = req.queryParams("name");
            //List<String> location = Arrays.asList(req.queryParamsValues("location"));
            String location = req.queryParams("location");

            // store
            //boolean keySuccess_user = addUser(id, name, location);
            //return (keySuccess_user ? ("Success! New User Created with id = " + id + "\n") : ("Failed in creating a new user.\n"));
            boolean newUserFlag = userStorePostgres.add(id, name, location);
            return (newUserFlag ? ("Success! New User Created with id = " + id + "\n") : ("Failed in creating a new user.\n"));
        });

        Spark.post("/newApp", (req, res) -> {
                    String appID = req.queryParams("app");
                    String jobID = req.queryParams("job");
                    String userID = req.queryParams("user");
                    String companyID = req.queryParams("company");
                    String CV = req.queryParams("CV");

                    //add from ApplicationStorePostgres
                    boolean keySuccess = appStorePostgres.add(appID, jobID, userID, companyID, CV);
                    return (keySuccess ? ("Success! New Application Created with id =" + appID + "\n") : ("Failed in adding new application.\n"));
                }

        );

        // POST command to insert a new job
        Spark.post("/newJob", (req, res) -> {
            String id = req.queryParams("id");
            String company = req.queryParams("company");
            String jobTitle = req.queryParams("jobTitle");
            String location = req.queryParams("location");
            String jobType = req.queryParams("jobType");

            //Job.JobType jobType = Job.JobType.valueOf(req.queryParams("jobType"));

            // store
            boolean keySuccess = jobStorePostgres.addJob(id, company, jobTitle, location, jobType);
            return (keySuccess ? ("Success! New Job Created with id =" + id + "\n") : ("Failed in adding new job.\n"));
        });

        options(
                "/*",
                (request, response) -> {
                    String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
                    if (accessControlRequestHeaders != null) {
                        //            response.header("Access-Control-Allow-Headers",
                        // accessControlRequestHeaders);
                        response.header("Access-Control-Allow-Headers", "*");
                    }

                    String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
                    if (accessControlRequestMethod != null) {
                        response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
                        response.header("Access-Control-Allow-Methods", "*");
                    }

                    return "OK";
                });

        before(
                (req, res) -> {
                    res.header("Access-Control-Allow-Origin", "*");
                    res.header("Access-Control-Allow-Headers", "*");
                    res.type("application/json");
                });
    }

}
