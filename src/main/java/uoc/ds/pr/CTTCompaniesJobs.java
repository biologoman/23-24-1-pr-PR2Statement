package uoc.ds.pr;

import edu.uoc.ds.adt.nonlinear.Dictionary;
import edu.uoc.ds.traversal.Iterator;
import uoc.ds.pr.exceptions.*;
import uoc.ds.pr.model.*;

import java.time.LocalDate;



public interface CTTCompaniesJobs {

    public static final int MAX_NUM_WORKERS = 250;
    public static final int MAX_NUM_COMPANIES = 26;
    public static final int MAX_NUM_JOBOFFERS= 350;



    enum Qualification {
        COMPULSARY (0),
        HIGH_SCHOOL (1),
        VOCATIONAL_TRAINING (2),
        UNIVERSITY (3),
        MASTER (4),
        DOCTORATE (5);
        private final int value;

        Qualification(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

    }

    enum Status {
        PENDING,
        ENABLED,
        DISABLED
    }

    enum Response {
        ACCEPTED,
        SUBSTITUTE,
        REJECTED
    }


    /**
     * @pre True.
     * @post If the worker code is new, the workers will be the same plus a new one.
     * If not, the worker's data will have been updated.
     */
    public void addWorker(String id, String name, String surname, LocalDate dateOfBirth, Qualification qualification);

    /**
     * @pre True.
     * @post If the company code is new, the companies will be the same plus one new one.
     * If not, the company data will have been updated.
     */
    public void addCompany(String id, String name, String description);


    /**
     * @pre The job application and offer do not exist.
     * @post The requests will be the same plus a new one.
     * If the company does not exist, the error will be reported
     */
    public void addRequest(String id, String jobOfferId, String companyId, String description,
                           Qualification minQualification, int maxWorkers,
                           LocalDate startDate, LocalDate endDate)  throws CompanyNotFoundException;


    /**
     * @pre True.
     * @post The status of the first request is modified
     * Returns the result request.
     * The count of pending requests awaiting a response will decrease by one.
     * If the application receives a favorable response, the count of job offers will increase by one.
     * If the response is unfavorable, the count of rejected applications will increase by one.
     * If there are no pending requests, an error will be reported.
     */
     public Request updateRequest(Status status, LocalDate date, String description) throws NoRequestException;


    /**
     * @pre True.
     * @post The count of workers enrolled for a job offer will increase by one.
     * If the worker lacks the minimum qualifications, they will be excluded.
     * If the maximum capacity has been reached, an error will be reported, and
     * an alternative will be added.
     * If either the company or the worker does not exist, an error will be reported.
     */
     public Response signUpJobOffer(String workerId, String jobOfferId) throws JobOfferNotFoundException, WorkerNotFoundException, WorkerAlreadyEnrolledException;


    /**
     * @pre True.
     * @post Provides a real number representing the percentage of denied requests
     */
    public double getPercentageRejectedRequests();

    /**
     * @pre The company exists.
     * @post Returns an iterator over a company's job offers.
     * If they do not exist, the error will be reported.
     */
    public Iterator<JobOffer> getJobOffersByCompany(String companyId) throws NOJobOffersException;

    /**
     * @pre True.
     * @post Returns an iterator over all job offers.
     * If they do not exist, the error will be reported.
     */
    public Iterator<JobOffer> getAllJobOffers() throws NOJobOffersException;

    /**
     * @pre The worker exists.
     * @post Returns an iterator over the job offers in which a worker has enrolled.
     * If they do not exist, an error is returned.
     *
     */
    public Iterator<JobOffer> getJobOffersByWorker(String workerId) throws NOJobOffersException;

    /**
     * @pre True.
     * @post The ratings will be the same plus one.
     * If the worker or the job offer does not exist, the error will be reported.
     * If the worker has not been enrolled in the job offer, an error will be reported
     */
     public void addRating(String workerId, String jobOfferId, int value, String message) throws WorkerNotFoundException, JobOfferNotFoundException, WorkerNOEnrolledException;


    /**
     * @pre True.
     * @post Returns an iterator over the ratings of a job offer.
     * If the job offer does not exist, the error will be reported.
     * If there are no ratings, the error will be reported.
     */
     public Iterator<Rating> getRatingsByJobOffer(String jobOfferId) throws JobOfferNotFoundException, NORatingsException;


    /**
     * @pre True.
     * @post Returns the most active worker, the one who has been working the longest.
     * If it does not exist, the error will be reported.
     */
     public Worker getMostActiveWorker() throws NoWorkerException;

    /**
     * @pre True.
     * @post Returns the highest rated job offer.
     * If it does not exist, the error will be reported.
     */
     public JobOffer getBestJobOffer() throws NOJobOffersException;

    /***********************************************************************************/
    /******************** AUX OPERATIONS  **********************************************/
    /***********************************************************************************/


    public Worker getWorker(String id);

    public Company getCompany(String id);

    public JobOffer getJobOffer(String jobOfferId);

    public int numWorkers();

    public int numCompanies();
    public int numJobOffers();

    public int numPendingRequests();

    public int numTotalRequests();
    public int numRejectedRequests();




}


