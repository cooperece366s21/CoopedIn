package edu.cooper.ece366.store;

import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface ApplicationDao {
    @SqlUpdate("insert into apps(appID, jobID, userID, companyID, CV) values (:appID, :jobID, :userID, :companyID, :CV)")
    void insertApp(
            @Bind("appID") String appID,
            @Bind("jobID") String jobID,
            @Bind("userID") String userID,
            @Bind("companyID") String companyID,
            @Bind("CV") String CV);

}
