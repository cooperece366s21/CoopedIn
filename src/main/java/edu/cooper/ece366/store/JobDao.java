package edu.cooper.ece366.store;

import edu.cooper.ece366.model.Job;
import edu.cooper.ece366.model.User;
import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

public interface JobDao {
    // data access object
    //@SqlQuery("select id, company, location, job_title, true as available from jobs j left join job_types jt on j.id = jt.job_id where j.company = :company")
    //@SqlQuery("select * from jobs where jobs.company = :company")
    @SqlQuery("select id, company, location, job_title, true as available, jt.job_type from jobs j left join job_types jt on j.id = jt.job_id where j.company = :company")
    @RegisterRowMapper(CoopedInJdbi.JobRowMapper.class)
    //Job getJob(@Bind("id") String id);
    List<Job> getByCompany(@Bind("company") String company);

    @SqlQuery("select id, company, location, job_title, true as available, jt.job_type from jobs j left join job_types jt on j.id = jt.job_id where j.location = :location")
    @RegisterRowMapper(CoopedInJdbi.JobRowMapper.class)
    List<Job> getByLocation(@Bind("location") String location);

    //@SqlQuery("select id, company, location, job_title, true as available, jt.job_type from jobs j left join job_types jt on j.id = jt.job_id where jt.job_type = :job_type")
    @SqlQuery("select id, company, location, job_title, true as available, jt.job_type from jobs j left join job_types jt on j.id = jt.job_id where cast(jt.job_type as varchar) = cast(:job_type as varchar)")
    @RegisterRowMapper(CoopedInJdbi.JobRowMapper.class)
    List<Job> getByJobtype(@Bind("job_type") String job_type);

   /* @SqlQuery("select id, company, location, job_title, true as available, jt.job_type from jobs j left join job_types jt on j.id = jt.job_id")
    @RegisterRowMapper(CoopedInJdbi.JobRowMapper.class)
    List<Job> getAll(@Bind("id") String id);*/

    // EC 4.18.21: Need to test functionality

    @SqlUpdate("insert into jobs(id, company, location, job_title) values (:id, :company, :location, :job_title)")
    //@SqlUpdate("insert into job_types(job_id, job_type) values (:id, :job_type)")
    void insertJob(
            @Bind("id") String id,
            @Bind("company") String company,
            @Bind("location") String location,
            @Bind("job_title") String job_title,
            @Bind("job_type") String job_type);

}
