package uoc.ds.pr.model;

import edu.uoc.ds.adt.nonlinear.PriorityQueue;
import edu.uoc.ds.adt.sequential.LinkedList;
import edu.uoc.ds.adt.sequential.List;
import edu.uoc.ds.adt.sequential.Queue;
import edu.uoc.ds.traversal.Iterator;
import uoc.ds.pr.CTTCompaniesJobs;
import uoc.ds.pr.util.QueueLinkedList;

import java.time.LocalDate;
import java.util.Comparator;

import static java.time.temporal.ChronoUnit.DAYS;

public class JobOffer implements Comparable<JobOffer>  {
    public static final Comparator<JobOffer> CMP_V = (jo1, jo2)->Double.compare(jo1.rating(), jo2.rating());

    private String jobOfferId;
    private Request request;
    private Company company;
    private String description;
    private CTTCompaniesJobs.Qualification minQualification;
    private int maxWorkers;
    private LocalDate startDate;
    private LocalDate endDate;

    private Queue<Enrollment> enrollments;
   // private Queue<Enrollment> substitutes;
    //PR2
   private PriorityQueue<Enrollment> substitutes;
    private List<Rating> ratings;
    private double sumRating;

    public JobOffer(String jobOfferId, Request request, Company company, String description,
                    CTTCompaniesJobs.Qualification minQualification, int maxWorkers,
                    LocalDate startDate, LocalDate endDate) {
        this.jobOfferId = jobOfferId;
        this.company = company;
        this.description = description;
        this.minQualification = minQualification;
        this.maxWorkers = maxWorkers;
        this.startDate = startDate;
        this.endDate = endDate;
        this.request = request;
        this.enrollments = new QueueLinkedList<>();
        //PR2
        //this.substitutes = new QueueLinkedList<>();
        this.substitutes = new PriorityQueue<>(Enrollment.CMP);
        this.ratings = new LinkedList<>();
    }

    public String getJobOfferId() {
        return jobOfferId;
    }

    public Company getCompany() {
        return this.company;
    }

    public void addRegistration(Worker worker, CTTCompaniesJobs.Response response) {
        Enrollment registration = new Enrollment(this, worker, response);
        if (response== CTTCompaniesJobs.Response.SUBSTITUTE) {
            substitutes.add(registration);
        }
        else {
            enrollments.add(registration);
        }
    }

    public double rating() {
        return (this.ratings.size()>0?(sumRating / this.ratings.size()):0);
    }

    @Override
    public int compareTo(JobOffer o) {
        return this.jobOfferId.compareTo(o.jobOfferId);
    }


    public void addRating(int value, String message, Worker worker) {
        this.ratings.insertEnd(new Rating(this, value, message, worker));
        this.sumRating+=value;
    }

    public Iterator<Rating> getRatings() {
        return ratings.values();
    }

    public boolean workerHasMinimumQualification(Worker worker) {
        return worker.getQualification().getValue() >= this.minQualification.getValue();
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public CTTCompaniesJobs.Qualification getMinQualification() {
        return minQualification;
    }

    public int getNumWorkers() {
        return this.enrollments.size();
    }

    public int getMaxWorkers() {
        return maxWorkers;
    }

    public boolean isfull() {
        return (getNumWorkers()>=this.getMaxWorkers());
    }

    public int getNumSubstitutes() {
        return substitutes.size();
    }

    public double getTotalRating() {
        return (this.ratings.size()!=0?this.sumRating/this.ratings.size():0);
    }

    public long getWorkingDays() {
        return DAYS.between(this.getStartDate(), this.getEndDate());
    }

    public Iterator<Enrollment> substitutes() {
        return substitutes.values();
    }

    public Iterator<Enrollment> enrollments() {
        return enrollments.values();
    }

}
