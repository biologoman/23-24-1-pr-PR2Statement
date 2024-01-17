package uoc.ds.pr;

import edu.uoc.ds.traversal.Iterator;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import uoc.ds.pr.exceptions.*;
import uoc.ds.pr.model.*;


import static uoc.ds.pr.util.DateUtils.createLocalDate;

public class CTTCompaniesJobsPR1Test {

    protected CTTCompaniesJobsPR2 cttCompaniesJobs;

    @Before
    public void setUp() throws Exception {
        this.cttCompaniesJobs = FactoryCTTCCompaniesJobs.getCTTCompaniesJobs();
    }

    @After
    public void tearDown() {
        this.cttCompaniesJobs = null;
    }


    public void initialState() {
        Assert.assertEquals(10, this.cttCompaniesJobs.numWorkers());
        Assert.assertEquals(7, this.cttCompaniesJobs.numCompanies());
        Assert.assertEquals(5, this.cttCompaniesJobs.numPendingRequests());
    }

    @Test
    public void addWorkerTest() {

        // GIVEN:
        initialState();
        //

        this.cttCompaniesJobs.addWorker("workerId1000", "Robert", "López", createLocalDate("22-01-1992"), CTTCompaniesJobs.Qualification.MASTER);
        Worker worker1000 = this.cttCompaniesJobs.getWorker("workerId1000");
        Assert.assertEquals("Robert", worker1000.getName());
        Assert.assertEquals("López", worker1000.getSurname());
        Assert.assertEquals(createLocalDate("22-01-1992"), worker1000.getDateOfBirth());
        Assert.assertEquals(CTTCompaniesJobs.Qualification.MASTER, worker1000.getQualification());

        Assert.assertEquals(11, this.cttCompaniesJobs.numWorkers());

        this.cttCompaniesJobs.addWorker("workerId999", "XXXX", "YYYY", createLocalDate("27-12-1967"), CTTCompaniesJobs.Qualification.HIGH_SCHOOL);
        Worker worker999 = this.cttCompaniesJobs.getWorker("workerId999");
        Assert.assertEquals("XXXX", worker999.getName());
        Assert.assertEquals("YYYY", worker999.getSurname());
        Assert.assertEquals(createLocalDate("27-12-1967"), worker999.getDateOfBirth());
        Assert.assertEquals(CTTCompaniesJobs.Qualification.HIGH_SCHOOL, worker999.getQualification());

        Assert.assertEquals(12, this.cttCompaniesJobs.numWorkers());

        this.cttCompaniesJobs.addWorker("workerId999", "Josep", "Ràmia", createLocalDate("21-12-1968"), CTTCompaniesJobs.Qualification.DOCTORATE);
        worker999 = this.cttCompaniesJobs.getWorker("workerId999");
        Assert.assertEquals("Josep", worker999.getName());
        Assert.assertEquals("Ràmia", worker999.getSurname());
        Assert.assertEquals(createLocalDate("21-12-1968"), worker999.getDateOfBirth());
        Assert.assertEquals(CTTCompaniesJobs.Qualification.DOCTORATE, worker999.getQualification());

        Assert.assertEquals(12, this.cttCompaniesJobs.numWorkers());

    }

    @Test
    public void addCompanyTest() {

        // GIVEN:
        initialState();
        //

        this.cttCompaniesJobs.addCompany("companyId1000", "Company Z", "the description (Z)");
        Company company1000 = this.cttCompaniesJobs.getCompany("companyId1000");
        Assert.assertEquals("Company Z", company1000.getName());
        Assert.assertEquals("the description (Z)", company1000.getDescription());

        Assert.assertEquals(8, this.cttCompaniesJobs.numCompanies());

        this.cttCompaniesJobs.addCompany("companyiD999", "XXXX", "YYYY");
        Company companyXXX = this.cttCompaniesJobs.getCompany("companyiD999");
        Assert.assertEquals("XXXX", companyXXX.getName());
        Assert.assertEquals("YYYY", companyXXX.getDescription());

        Assert.assertEquals(9, this.cttCompaniesJobs.numCompanies());

        this.cttCompaniesJobs.addCompany("companyiD999", "Company W", "the description (W)");
        companyXXX = this.cttCompaniesJobs.getCompany("companyiD999");
        Assert.assertEquals("Company W", companyXXX.getName());
        Assert.assertEquals("the description (W)", companyXXX.getDescription());

        Assert.assertEquals(9, this.cttCompaniesJobs.numCompanies());

    }


    @Test
    public void addRequestTest() throws DSException {
        // GIVEN:
        initialState();
        //
        Assert.assertThrows(CompanyNotFoundException.class, () ->
                cttCompaniesJobs.addRequest("requestId5", "jobOfferIdB2", "companyIdXXXX",
                        "The description (request 5)", CTTCompaniesJobs.Qualification.HIGH_SCHOOL,
                        3, createLocalDate("25-11-2023"),createLocalDate("31-12-2023")));

        cttCompaniesJobs.addRequest("requestId5", "jobOfferIdB2", "companyId2",
                "The description (request 5)", CTTCompaniesJobs.Qualification.HIGH_SCHOOL,
                3, createLocalDate("25-11-2023"),createLocalDate("31-12-2023"));

        Assert.assertEquals(6, cttCompaniesJobs.numPendingRequests());


    }

    @Test
    public void updateRequestTest() throws DSException {
        addRequestTest();

        Request request1 = cttCompaniesJobs.updateRequest(CTTCompaniesJobs.Status.ENABLED,
                createLocalDate("10-11-2023"), "OK (id1)");

        Assert.assertEquals("jobOfferIdA1", request1.getJobOffer().getJobOfferId());
        Assert.assertEquals("companyId1", request1.getJobOffer().getCompany().getId());
        Assert.assertEquals(createLocalDate("01-11-2023"), request1.getJobOffer().getStartDate());
        Assert.assertEquals(createLocalDate("10-12-2023"), request1.getJobOffer().getEndDate());
        Assert.assertEquals(39, request1.getJobOffer().getWorkingDays());

        Assert.assertEquals(CTTCompaniesJobs.Status.ENABLED, request1.getStatus());
        Assert.assertEquals(createLocalDate("10-11-2023"), request1.getDateStatus());
        Assert.assertEquals("OK (id1)", request1.getDescriptionStatus());

        Request request2 = cttCompaniesJobs.updateRequest(CTTCompaniesJobs.Status.ENABLED,
                createLocalDate("11-11-2023"), "OK (id2)");

        Assert.assertEquals("jobOfferIdA2", request2.getJobOffer().getJobOfferId());
        Assert.assertEquals("companyId1", request2.getJobOffer().getCompany().getId());
        Assert.assertEquals(createLocalDate("12-11-2023"), request2.getJobOffer().getStartDate());
        Assert.assertEquals(createLocalDate("08-12-2023"), request2.getJobOffer().getEndDate());
        Assert.assertEquals(26, request2.getJobOffer().getWorkingDays());

        Assert.assertEquals(CTTCompaniesJobs.Status.ENABLED, request2.getStatus());
        Assert.assertEquals(createLocalDate("11-11-2023"), request2.getDateStatus());
        Assert.assertEquals("OK (id2)", request2.getDescriptionStatus());

        Request request3 = cttCompaniesJobs.updateRequest(CTTCompaniesJobs.Status.ENABLED,
                createLocalDate("12-11-2023"), "OK (id3)");
        Assert.assertEquals("companyId1", request3.getJobOffer().getCompany().getId());
        Assert.assertEquals(createLocalDate("01-01-2023"), request3.getJobOffer().getStartDate());
        Assert.assertEquals(createLocalDate("31-12-2023"), request3.getJobOffer().getEndDate());
        Assert.assertEquals(364, request3.getJobOffer().getWorkingDays());
        Assert.assertEquals("jobOfferIdA3", request3.getJobOffer().getJobOfferId());
        Assert.assertEquals(CTTCompaniesJobs.Status.ENABLED, request3.getStatus());
        Assert.assertEquals(createLocalDate("12-11-2023"), request3.getDateStatus());
        Assert.assertEquals("OK (id3)", request3.getDescriptionStatus());

    }

    @Test
    public void signUpJobOfferTest() throws DSException {
        updateRequestTest();

        Assert.assertThrows(JobOfferNotFoundException.class, () ->
                cttCompaniesJobs.signUpJobOffer("workerId1", "jobOfferIA1XXX"));

        Assert.assertThrows(WorkerNotFoundException.class, () ->
                cttCompaniesJobs.signUpJobOffer("workerIdXXX", "jobOfferIdA1"));

        Worker workerId1 = cttCompaniesJobs.getWorker("workerId1");
        Assert.assertEquals(CTTCompaniesJobs.Qualification.DOCTORATE, workerId1.getQualification());

        JobOffer jobOfferIA1 = cttCompaniesJobs.getJobOffer("jobOfferIdA1");
        Assert.assertEquals(CTTCompaniesJobs.Qualification.DOCTORATE, jobOfferIA1.getMinQualification());

        CTTCompaniesJobs.Response response1 = cttCompaniesJobs.signUpJobOffer("workerId1", "jobOfferIdA1");
        Assert.assertEquals(CTTCompaniesJobs.Response.ACCEPTED, response1);
        Assert.assertEquals(1, jobOfferIA1.getNumWorkers());

        Assert.assertThrows(WorkerAlreadyEnrolledException.class, () ->
            cttCompaniesJobs.signUpJobOffer("workerId1", "jobOfferIdA1"));

        Worker workerId2 = cttCompaniesJobs.getWorker("workerId2");
        Assert.assertEquals(CTTCompaniesJobs.Qualification.UNIVERSITY, workerId2.getQualification());

        CTTCompaniesJobs.Response response2 = cttCompaniesJobs.signUpJobOffer("workerId2", "jobOfferIdA1");
        Assert.assertEquals(CTTCompaniesJobs.Response.REJECTED, response2);
        Assert.assertEquals(1, jobOfferIA1.getNumWorkers());

        CTTCompaniesJobs.Response response3 = cttCompaniesJobs.signUpJobOffer("workerId2", "jobOfferIdA1");
        Assert.assertEquals(CTTCompaniesJobs.Response.REJECTED, response3);
        Assert.assertEquals(1, jobOfferIA1.getNumWorkers());

        CTTCompaniesJobs.Response response4 = cttCompaniesJobs.signUpJobOffer("workerId3", "jobOfferIdA1");
        Assert.assertEquals(CTTCompaniesJobs.Response.REJECTED, response4);
        Assert.assertEquals(1, jobOfferIA1.getNumWorkers());

        CTTCompaniesJobs.Response response5 = cttCompaniesJobs.signUpJobOffer("workerId4", "jobOfferIdA1");
        Assert.assertEquals(CTTCompaniesJobs.Response.REJECTED, response5);
        Assert.assertEquals(1, jobOfferIA1.getNumWorkers());

        CTTCompaniesJobs.Response response6 = cttCompaniesJobs.signUpJobOffer("workerId5", "jobOfferIdA1");
        Assert.assertEquals(CTTCompaniesJobs.Response.ACCEPTED, response6);
        Assert.assertEquals(2, jobOfferIA1.getNumWorkers());


        CTTCompaniesJobs.Response response7 = cttCompaniesJobs.signUpJobOffer("workerId6", "jobOfferIdA1");
        Assert.assertEquals(CTTCompaniesJobs.Response.ACCEPTED, response7);
        Assert.assertEquals(3, jobOfferIA1.getNumWorkers());

        CTTCompaniesJobs.Response response8 = cttCompaniesJobs.signUpJobOffer("workerId7", "jobOfferIdA1");
        Assert.assertEquals(CTTCompaniesJobs.Response.ACCEPTED, response8);
        Assert.assertEquals(4, jobOfferIA1.getNumWorkers());

        CTTCompaniesJobs.Response response9 = cttCompaniesJobs.signUpJobOffer("workerId8", "jobOfferIdA1");
        Assert.assertEquals(CTTCompaniesJobs.Response.ACCEPTED, response9);
        Assert.assertEquals(5, jobOfferIA1.getNumWorkers());
        Assert.assertEquals(0, jobOfferIA1.getNumSubstitutes());

        CTTCompaniesJobs.Response response10 = cttCompaniesJobs.signUpJobOffer("workerId9", "jobOfferIdA1");
        Assert.assertEquals(CTTCompaniesJobs.Response.SUBSTITUTE, response10);
        Assert.assertEquals(5, jobOfferIA1.getNumWorkers());
        Assert.assertEquals(1, jobOfferIA1.getNumSubstitutes());

        Assert.assertThrows(WorkerAlreadyEnrolledException.class, () ->
                 cttCompaniesJobs.signUpJobOffer("workerId9", "jobOfferIdA1"));

        CTTCompaniesJobs.Response response11 = cttCompaniesJobs.signUpJobOffer("workerId10", "jobOfferIdA1");
        Assert.assertEquals(CTTCompaniesJobs.Response.SUBSTITUTE, response11);
        Assert.assertEquals(5, jobOfferIA1.getNumWorkers());
        Assert.assertEquals(2, jobOfferIA1.getNumSubstitutes());

        Iterator<Enrollment> it = jobOfferIA1.substitutes();
        Enrollment enrollment1 = it.next();
        Assert.assertEquals("workerId9", enrollment1.getWorker().getId());

        Enrollment enrollment2 = it.next();
        Assert.assertEquals("workerId10", enrollment2.getWorker().getId());
        Assert.assertFalse(it.hasNext());

        CTTCompaniesJobs.Response response12 = cttCompaniesJobs.signUpJobOffer("workerId2", "jobOfferIdA2");
        Assert.assertEquals(CTTCompaniesJobs.Response.ACCEPTED, response12);

        JobOffer jobOfferIdA2 = cttCompaniesJobs.getJobOffer("jobOfferIdA2");

        Assert.assertEquals(1, jobOfferIdA2.getNumWorkers());
        Assert.assertEquals(0, jobOfferIdA2.getNumSubstitutes());

        CTTCompaniesJobs.Response response13 = cttCompaniesJobs.signUpJobOffer("workerId1", "jobOfferIdA3");
        Assert.assertEquals(CTTCompaniesJobs.Response.ACCEPTED, response13);

        JobOffer jobOfferIdA3 = cttCompaniesJobs.getJobOffer("jobOfferIdA3");

        Assert.assertEquals(1, jobOfferIdA3.getNumWorkers());
        Assert.assertEquals(0, jobOfferIdA3.getNumSubstitutes());

        CTTCompaniesJobs.Response response14 = cttCompaniesJobs.signUpJobOffer("workerId5", "jobOfferIdA3");
        Assert.assertEquals(CTTCompaniesJobs.Response.ACCEPTED, response14);

        Assert.assertEquals(2, jobOfferIdA3.getNumWorkers());
        Assert.assertEquals(0, jobOfferIdA3.getNumSubstitutes());

    }

    @Test
    public void getPercentageRejectedRequestTest() throws DSException {
        updateRequestTest();

        Assert.assertEquals(0, cttCompaniesJobs.getPercentageRejectedRequests(),0);

        Request request4 = cttCompaniesJobs.updateRequest(CTTCompaniesJobs.Status.DISABLED,
                createLocalDate("12-11-2023"), "OK (id4)");

        Assert.assertEquals("jobOfferIdB1", request4.getJobOffer().getJobOfferId());
        Assert.assertEquals(CTTCompaniesJobs.Status.DISABLED, request4.getStatus());
        Assert.assertEquals(createLocalDate("12-11-2023"), request4.getDateStatus());
        Assert.assertEquals("OK (id4)", request4.getDescriptionStatus());

        Assert.assertEquals(2, cttCompaniesJobs.numPendingRequests(),0);
        Assert.assertEquals(3, cttCompaniesJobs.numJobOffers(),0);
        Assert.assertEquals(1, cttCompaniesJobs.numRejectedRequests(),0);

        Assert.assertEquals(6, cttCompaniesJobs.numTotalRequests(),0);

        Assert.assertEquals(0.16, cttCompaniesJobs.getPercentageRejectedRequests(),0.2);

        Request request5 = cttCompaniesJobs.updateRequest(CTTCompaniesJobs.Status.DISABLED,
                createLocalDate("13-11-2023"), "OK (id5)");

        Assert.assertEquals("jobOfferIdB1", request4.getJobOffer().getJobOfferId());
        Assert.assertEquals(CTTCompaniesJobs.Status.DISABLED, request4.getStatus());
        Assert.assertEquals(createLocalDate("12-11-2023"), request4.getDateStatus());
        Assert.assertEquals("OK (id4)", request4.getDescriptionStatus());

        Assert.assertEquals(1, cttCompaniesJobs.numPendingRequests(),0);
        Assert.assertEquals(3, cttCompaniesJobs.numJobOffers(),0);
        Assert.assertEquals(2, cttCompaniesJobs.numRejectedRequests(),0);

        Assert.assertEquals(6, cttCompaniesJobs.numTotalRequests(),0);

        Assert.assertEquals(0.33, cttCompaniesJobs.getPercentageRejectedRequests(),0.2);

    }

    @Test
    public void getJobOffersByCompanyTest() throws DSException {
        updateRequestTest();

        Assert.assertThrows(NOJobOffersException.class, () ->
                cttCompaniesJobs.getJobOffersByCompany("companyId2"));


        Iterator<JobOffer> it1 = cttCompaniesJobs.getJobOffersByCompany("companyId1");

        JobOffer jobOffer1 = it1.next();
        Assert.assertEquals("jobOfferIdA1", jobOffer1.getJobOfferId());

        JobOffer jobOffer2 = it1.next();
        Assert.assertEquals("jobOfferIdA2", jobOffer2.getJobOfferId());

        JobOffer jobOffer3 = it1.next();
        Assert.assertEquals("jobOfferIdA3", jobOffer3.getJobOfferId());

        Assert.assertFalse(it1.hasNext());
    }

    @Test
    public void getAllJobOffersTest() throws DSException {

        Assert.assertThrows(NOJobOffersException.class, () ->
                cttCompaniesJobs.getAllJobOffers());

        updateRequestTest();

        Iterator<JobOffer> it = cttCompaniesJobs.getAllJobOffers();
        JobOffer jobOffer1 = it.next();
        Assert.assertEquals("jobOfferIdA1", jobOffer1.getJobOfferId());

        JobOffer jobOffer2 = it.next();
        Assert.assertEquals("jobOfferIdA2", jobOffer2.getJobOfferId());

        JobOffer jobOffer3 = it.next();
        Assert.assertEquals("jobOfferIdA3", jobOffer3.getJobOfferId());

        Assert.assertFalse(it.hasNext());

    }

    @Test
    public void getJobOffersByWorker() throws DSException {
        Assert.assertThrows(NOJobOffersException.class, () ->
                cttCompaniesJobs.getJobOffersByWorker("workerId1"));

        signUpJobOfferTest();

        Iterator<JobOffer> it = cttCompaniesJobs.getJobOffersByWorker("workerId1");

        JobOffer jobOffer1 = it.next();
        Assert.assertEquals("jobOfferIdA1", jobOffer1.getJobOfferId());

        JobOffer jobOffer2 = it.next();
        Assert.assertEquals("jobOfferIdA3", jobOffer2.getJobOfferId());

        Assert.assertFalse(it.hasNext());

        CTTCompaniesJobs.Response response = cttCompaniesJobs.signUpJobOffer("workerId1", "jobOfferIdA2");
        Assert.assertEquals(CTTCompaniesJobs.Response.ACCEPTED, response);

        JobOffer jobOfferIdA3 = cttCompaniesJobs.getJobOffer("jobOfferIdA2");

        Assert.assertEquals(2, jobOfferIdA3.getNumWorkers());
        Assert.assertEquals(0, jobOfferIdA3.getNumSubstitutes());

        it = cttCompaniesJobs.getJobOffersByWorker("workerId1");

        jobOffer1 = it.next();
        Assert.assertEquals("jobOfferIdA1", jobOffer1.getJobOfferId());

        jobOffer2 = it.next();
        Assert.assertEquals("jobOfferIdA3", jobOffer2.getJobOfferId());

        JobOffer jobOffer3 = it.next();
        Assert.assertEquals("jobOfferIdA3", jobOffer2.getJobOfferId());

        Assert.assertFalse(it.hasNext());
    }

    @Test
    public void addRatingTest() throws DSException {
        signUpJobOfferTest();

        Assert.assertThrows(WorkerNotFoundException.class, () ->
                cttCompaniesJobs.addRating("workerIdXXX", "jobOfferIdA1", 10, "Great"));

        Assert.assertThrows(JobOfferNotFoundException.class, () ->
                cttCompaniesJobs.addRating("workerId1", "jobOfferIdXXXX", 10, "Great"));

        Assert.assertThrows(WorkerNOEnrolledException.class, () ->
                cttCompaniesJobs.addRating("workerId3", "jobOfferIdA3", 10, "Great"));

        cttCompaniesJobs.addRating("workerId1", "jobOfferIdA1", 10, "Great");

        cttCompaniesJobs.addRating("workerId5", "jobOfferIdA1", 8, "Good Experience");

    }


    @Test
    public void getRatingsByJobOfferTest() throws DSException {
        addRatingTest();

        Assert.assertThrows(JobOfferNotFoundException.class, () ->
                cttCompaniesJobs.getRatingsByJobOffer("jobOfferIdXXXX"));

        Assert.assertThrows(NORatingsException.class, () ->
                cttCompaniesJobs.getRatingsByJobOffer("jobOfferIdA3"));

        Iterator<Rating> it = cttCompaniesJobs.getRatingsByJobOffer("jobOfferIdA1");

        Rating rating1 = it.next();
        Assert.assertEquals(10, rating1.getValue());
        Assert.assertEquals("Great", rating1.getMessage());

        Rating rating2 = it.next();
        Assert.assertEquals(8, rating2.getValue());
        Assert.assertEquals("Good Experience", rating2.getMessage());

    }

    @Test
    public void getMostActiveWorkerTest() throws DSException {
        Assert.assertThrows(NoWorkerException.class, () ->
                cttCompaniesJobs.getMostActiveWorker());

        signUpJobOfferTest();

        Worker mostActiveUser = cttCompaniesJobs.getMostActiveWorker();
        Assert.assertEquals("workerId1", mostActiveUser.getId());
        Assert.assertEquals(403, mostActiveUser.getWorkingDays());

        Iterator<JobOffer> it = cttCompaniesJobs.getJobOffersByWorker("workerId1");
        JobOffer jobOffer1 = it.next();
        Assert.assertEquals("jobOfferIdA1", jobOffer1.getJobOfferId());
        Assert.assertEquals(39, jobOffer1.getWorkingDays());

        JobOffer jobOffer2 = it.next();
        Assert.assertEquals("jobOfferIdA3", jobOffer2.getJobOfferId());
        Assert.assertEquals(364, jobOffer2.getWorkingDays());

        Assert.assertFalse(it.hasNext());
    }


    @Test
    public void getBestJobOfferTest() throws DSException {
        Assert.assertThrows(NOJobOffersException.class, () ->
                cttCompaniesJobs.getBestJobOffer());

        addRatingTest();

        JobOffer bestJobOffer = cttCompaniesJobs.getBestJobOffer();
        Assert.assertEquals("jobOfferIdA1", bestJobOffer.getJobOfferId());
        Assert.assertEquals(9, bestJobOffer.getTotalRating(), 0);

        cttCompaniesJobs.addRating("workerId1", "jobOfferIdA3", 10, "Great");
        cttCompaniesJobs.addRating("workerId5", "jobOfferIdA3", 10, "Good Experience");

        bestJobOffer = cttCompaniesJobs.getBestJobOffer();
        Assert.assertEquals("jobOfferIdA3", bestJobOffer.getJobOfferId());
        Assert.assertEquals(10, bestJobOffer.getTotalRating(), 0);

    }

}