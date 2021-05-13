package edu.cooper.ece366.model;

import io.norberg.automatter.AutoMatter;

import java.util.Arrays;
import java.util.List;

@AutoMatter
public interface Application {
    String appID();
    String jobID();
    String userID();
    String companyID();
    String CV();  //resume link is saved in text format


    List<Status> status();
    enum Status{
        Submitted,
        Reviewed,   //together with the decision of interview
        Accepted,
        Rejected;

        public static Application.Status fromDbValue(String dbValue) {
            return Arrays.asList(Application.Status.values()).stream()
                    .filter(s -> dbValue.equalsIgnoreCase(s.name()))
                    .findFirst().orElseThrow();
        }

    }
}
