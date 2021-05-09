package edu.cooper.ece366.store;

import edu.cooper.ece366.model.Application;
import edu.cooper.ece366.model.ApplicationBuilder;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ApplicationStoreImpl implements ApplicationStore{
    private static final Map<String, Application> appMap;
    static{
        List<Application> apps =
                List.of(
                        new ApplicationBuilder().status(Application.Status.Submitted)
                                    .appID("1")
                                    .jobID("4")
                                    .userID("8")
                                    .companyID("C4")
                                    .CV("http://ellaCV.com").build(),
                        new ApplicationBuilder().status(Application.Status.Accepted)
                                    .appID("2")
                                    .jobID("5")
                                    .userID("5")
                                    .companyID("C5")
                                    .CV("http://BobCV.com").build(),
                        new ApplicationBuilder().status(Application.Status.Submitted)
                                    .appID("3")
                                    .jobID("4")
                                    .userID("1")
                                    .companyID("C4")
                                    .CV("http://RobertCV.com").build());
                appMap = apps.stream().collect(Collectors.toMap(Application::appID, Function.identity()));
    }
    public ApplicationStoreImpl() {}


    @Override
    public boolean add(String appID, String jobID, String userID, String companyID, String CV) {
        if(appMap.containsKey(appID)) {
            System.out.println("Application already exists!");
            return false;
        }
        Application.Status status = Application.Status.valueOf("Submitted");

        Application newApp = new ApplicationBuilder()
                .status(status)
                .appID(appID)
                .companyID(companyID)
                .userID(userID)
                .jobID(jobID)
                .CV(CV)
                .build();

        appMap.put(appID, newApp);

        return (appMap.containsKey(appID));
    }
}
