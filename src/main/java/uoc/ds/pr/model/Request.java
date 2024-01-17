package uoc.ds.pr.model;

import uoc.ds.pr.CTTCompaniesJobs;

import java.time.LocalDate;

public class Request {

    private String jobOfferId;
    private Company company;
    private String description;
    private CTTCompaniesJobs.Qualification minQualification;
    private int maxWorkers;
    private LocalDate startDate;
    private LocalDate endDate;
    private CTTCompaniesJobs.Status status;
    private String descriptionStatus;
    private LocalDate dateStatus;


    public Request(String jobOfferId, Company company, String description, CTTCompaniesJobs.Qualification minQualification,
                   int maxWorkers, LocalDate startDate, LocalDate endDate) {
        this.jobOfferId = jobOfferId;
        this.company = company;
        this.description = description;
        this.minQualification = minQualification;
        this.maxWorkers = maxWorkers;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = CTTCompaniesJobs.Status.PENDING;
    }

    public void update(CTTCompaniesJobs.Status status, LocalDate date, String message) {
        this.status = status;
        this.dateStatus = date;
        this.descriptionStatus = message;
    }

    public boolean isEnabled() {
        return this.status == CTTCompaniesJobs.Status.ENABLED;
    }

    public JobOffer getJobOffer() {
        return new JobOffer(this.jobOfferId, this, this.company, this.description,
                this.minQualification, this.maxWorkers, this.startDate, this.endDate );
    }

    public CTTCompaniesJobs.Status getStatus() {
        return status;
    }

    public LocalDate getDateStatus() {
        return dateStatus;
    }

    public String getDescriptionStatus() {
        return descriptionStatus;
    }
}
