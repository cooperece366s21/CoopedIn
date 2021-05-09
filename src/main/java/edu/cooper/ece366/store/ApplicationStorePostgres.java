package edu.cooper.ece366.store;

import edu.cooper.ece366.model.Job;
import org.jdbi.v3.core.Jdbi;

import java.util.List;

public class ApplicationStorePostgres implements ApplicationStore{
    //for testing
    private final Jdbi jdbi;

    public ApplicationStorePostgres (final Jdbi jdbi) {
            this.jdbi = jdbi;
    }

    @Override
    public boolean add(String appID, String jobID, String userID, String companyID, String CV) {
        try {jdbi.useHandle(
                handle -> {
                    handle.attach(ApplicationDao.class).insertApp(appID, jobID, userID, companyID, CV);
                });
            return true;} catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
