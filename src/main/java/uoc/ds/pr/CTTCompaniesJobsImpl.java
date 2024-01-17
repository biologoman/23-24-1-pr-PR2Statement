package uoc.ds.pr;

import java.time.LocalDate;

import edu.uoc.ds.adt.sequential.Queue;
import edu.uoc.ds.traversal.Iterator;
import uoc.ds.pr.exceptions.*;
import uoc.ds.pr.model.*;
import uoc.ds.pr.util.DSArray;
import uoc.ds.pr.util.OrderedVector;
import uoc.ds.pr.util.QueueLinkedList;


public class CTTCompaniesJobsImpl implements CTTCompaniesJobs {

    private DSArray<Worker> workers;
    private DSArray<Company> companies;

    private Queue<Request> requests;

    private DSArray<JobOffer> jobOffers;
    private int numRejectedRequests;

    private OrderedVector<JobOffer> bestJobOffer;
    private Worker mostActiveWorker;


    public CTTCompaniesJobsImpl() {
        workers = new DSArray<>(MAX_NUM_WORKERS);
        companies = new DSArray<>(MAX_NUM_COMPANIES);
        requests = new QueueLinkedList<>();
        jobOffers = new DSArray<>(MAX_NUM_JOBOFFERS);
        bestJobOffer = new OrderedVector<>(MAX_NUM_JOBOFFERS, JobOffer.CMP_V);
        numRejectedRequests = 0;
        mostActiveWorker = null;
    }


    @Override
    public void addWorker(String id, String name, String surname, LocalDate dateOfBirth, Qualification qualification) {
        Worker worker = getWorker(id);
        if (worker == null) {
            worker = new Worker(id, name, surname, dateOfBirth, qualification);
            this.workers.put(id, worker);
        }
        else {
            worker.update(name, surname, dateOfBirth, qualification);
        }
    }



    @Override
    public void addCompany(String id, String name, String description) {
        Company company = getCompany(id);
        if (company == null) {
            company = new Company(id, name, description);
            this.companies.put(id, company);
        }
        else {
            company.update(name, description);
        }

    }

    public void addRequest(String id, String jobOfferId, String companyId, String description,
                           Qualification minQualification, int maxWorkers, LocalDate startDate,
                           LocalDate endDate) throws CompanyNotFoundException {

        Company company = getCompany(companyId);
        if (company == null) {
            throw new CompanyNotFoundException();
        }

        Request request = new Request(jobOfferId, company, description, minQualification, maxWorkers, startDate, endDate);
        this.requests.add(request);

    }

    public Request updateRequest(Status status, LocalDate date, String description) throws NoRequestException {
        if (requests.isEmpty()) throw new NoRequestException();
        Request request = requests.poll();

        if (request  == null) {
            throw new NoRequestException();
        }

        request.update(status, date, description);
        if (request.isEnabled()) {
            JobOffer jobOffer = request.getJobOffer();

            jobOffers.put(jobOffer.getJobOfferId(), jobOffer);
            Company company = jobOffer.getCompany();
            company.addJobOffer(jobOffer);
        }
        else {
            numRejectedRequests++;
        }
        return request;
    }


    public Response signUpJobOffer(String workerId, String jobOfferId) throws JobOfferNotFoundException, WorkerNotFoundException, WorkerAlreadyEnrolledException {
        Response response = Response.REJECTED;
        Worker worker = getWorker(workerId);
        if (worker == null) {
            throw new WorkerNotFoundException();
        }

        JobOffer jobOffer = getJobOffer(jobOfferId);
        if (jobOffer == null) {
            throw new JobOfferNotFoundException();
        }

        if (worker.isInJobOffer(jobOffer)) {
            throw new WorkerAlreadyEnrolledException();
        }

        if (worker.isInJobOfferAsSubstitute(jobOffer)) {
            throw new WorkerAlreadyEnrolledException();
        }

        if (jobOffer.workerHasMinimumQualification(worker)){
            if (!jobOffer.isfull()) {
                response = Response.ACCEPTED;
                jobOffer.addRegistration(worker, response);
                worker.addJobOffer(jobOffer);
                updateMostActiveWorker(worker);
                response = Response.ACCEPTED;
            }
            else {
                response = Response.SUBSTITUTE;
                jobOffer.addRegistration(worker, response);
            }
        }
        else {
            // Rejected
            response = Response.REJECTED;
        }
        return response;
    }

    private void updateMostActiveWorker(Worker worker) {
        if ((this.mostActiveWorker==null) ||
           (this.mostActiveWorker.getWorkingDays() < worker.getWorkingDays())) {
            this.mostActiveWorker = worker;
        }
    }

    public double getPercentageRejectedRequests() {
        int total = jobOffers.size()+requests.size() + numRejectedRequests;
        return (total!=0?(double) numRejectedRequests / total:0);
    }

    public Iterator<JobOffer> getJobOffersByCompany(String companyId) throws NOJobOffersException {
        Company company = getCompany(companyId);
        Iterator<JobOffer> it = company.getJobOffers();
        if (!it.hasNext()) {
            throw new NOJobOffersException();
        }
        return it;
    }

    public Iterator<JobOffer> getAllJobOffers() throws NOJobOffersException {
        if (jobOffers.size()==0) {
            throw new NOJobOffersException();
        }
        return jobOffers.values();
    }

    public Iterator<JobOffer> getJobOffersByWorker(String workerId) throws NOJobOffersException {
        Worker worker = getWorker(workerId);
        Iterator<JobOffer> it = worker.getJobOffers();

        if (!it.hasNext()) {
            throw new NOJobOffersException();
        }
        return it;
    }

    public void addRating(String workerId, String jobOfferId, int value, String message) throws WorkerNotFoundException, JobOfferNotFoundException, WorkerNOEnrolledException {
        Worker worker = getWorker(workerId);
        if (worker == null) {
            throw new WorkerNotFoundException();
        }

        JobOffer jobOffer = getJobOffer(jobOfferId);
        if (jobOffer == null) {
            throw new JobOfferNotFoundException();
        }

        if (!worker.isInJobOffer(jobOffer)) {
            throw new WorkerNOEnrolledException();
        }

        jobOffer.addRating(value, message, worker);

        updateBestJobOffer(jobOffer);

    }


    public Iterator<Rating> getRatingsByJobOffer(String jobOfferId) throws JobOfferNotFoundException, NORatingsException {
        JobOffer jobOffer = getJobOffer(jobOfferId);

        if (jobOffer == null) {
            throw new JobOfferNotFoundException();
        }

        Iterator<Rating> it = jobOffer.getRatings();

        if (!it.hasNext()) {
            throw new NORatingsException();
        }

        return it;
    }

    public Worker getMostActiveWorker() throws NoWorkerException {
        if (this.mostActiveWorker == null) {
            throw new NoWorkerException();
        }

        return this.mostActiveWorker;
    }

    public JobOffer getBestJobOffer() throws NOJobOffersException {
        if (bestJobOffer.isEmpty()) {
            throw new NOJobOffersException();
        }

        return bestJobOffer.elementAt(0);
    }



    private void updateBestJobOffer(JobOffer jobOffer) {
        bestJobOffer.delete(jobOffer);
        bestJobOffer.update(jobOffer);

    }


    /***********************************************************************************/
    /******************** AUX OPERATIONS  **********************************************/
    /***********************************************************************************/


    public Worker getWorker(String id) {
        return workers.get(id);
    }

    public Company getCompany(String id) {
        return companies.get(id);
    }

    public JobOffer getJobOffer(String jobOfferId) {
        return jobOffers.get(jobOfferId);
    }

    @Override
    public int numWorkers() {
        return this.workers.size();
    }

    @Override
    public int numCompanies() {
        return this.companies.size();
    }

    @Override
    public int numJobOffers() {
        return this.jobOffers.size();
    }

    @Override
    public int numPendingRequests() {
        return this.requests.size();
    }

    @Override
    public int numTotalRequests() {
        return jobOffers.size() + numPendingRequests() + numRejectedRequests;
    }

    public int numRejectedRequests() {
        return numRejectedRequests;
    }
}
