package uoc.ds.pr.model;

import uoc.ds.pr.CTTCompaniesJobs;

import java.util.Comparator;

public class Enrollment implements Comparable<Enrollment> {

    public static final Comparator<Enrollment> CMP = (o1, o2) -> o2.compareTo(o1);

    private Worker worker;
    private JobOffer jobOffer;

    private CTTCompaniesJobs.Response response;

    public Enrollment(JobOffer jobOffer, Worker worker, CTTCompaniesJobs.Response response) {
        this.jobOffer = jobOffer;
        this.worker = worker;
        this.response = response;
    }

    public Worker getWorker() {
        return this.worker;
    }

    @Override
    public int compareTo(Enrollment w) {

        return this.getWorker().compareTo(w.getWorker());
    }
}
