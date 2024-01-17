package uoc.ds.pr;

import uoc.ds.pr.exceptions.CompanyNotFoundException;

import java.time.LocalDate;

import static uoc.ds.pr.util.DateUtils.createLocalDate;


public class FactoryCTTCCompaniesJobs {


    public static CTTCompaniesJobsPR2 getCTTCompaniesJobs() throws Exception {
        CTTCompaniesJobsPR2 cttCompaniesJobs;
        cttCompaniesJobs = new CTTCompaniesJobsPR2Impl();


        ////
        //// WORKER
        ////
        cttCompaniesJobs.addWorker("workerId1", "Maria", "Simó", createLocalDate("03-01-1974"), CTTCompaniesJobs.Qualification.DOCTORATE);
        cttCompaniesJobs.addWorker("workerId2", "Àlex", "Lluna ", createLocalDate("18-03-1981"), CTTCompaniesJobs.Qualification.UNIVERSITY);
        cttCompaniesJobs.addWorker("workerId3", "Pepet", "Ferra", createLocalDate("08-05-2000"), CTTCompaniesJobs.Qualification.MASTER);
        cttCompaniesJobs.addWorker("workerId4", "Joana", "Quilez", createLocalDate("26-08-2001"), CTTCompaniesJobs.Qualification.MASTER);
        cttCompaniesJobs.addWorker("workerId5", "Armand", "Morata", createLocalDate("13-09-2003"), CTTCompaniesJobs.Qualification.DOCTORATE);
        cttCompaniesJobs.addWorker("workerId6", "Rut", "Paramio", createLocalDate("23-10-1992"), CTTCompaniesJobs.Qualification.DOCTORATE);
        cttCompaniesJobs.addWorker("workerId7", "Miriam", "Navarro", createLocalDate("13-05-1995"), CTTCompaniesJobs.Qualification.DOCTORATE);
        cttCompaniesJobs.addWorker("workerId8", "Pedro", "Tirrano", createLocalDate("16-08-1997"), CTTCompaniesJobs.Qualification.DOCTORATE);
        cttCompaniesJobs.addWorker("workerId9", "Pedro", "Barón", createLocalDate("18-07-1999"), CTTCompaniesJobs.Qualification.DOCTORATE);
        cttCompaniesJobs.addWorker("workerId10", "Emily", "Jones", createLocalDate("08-01-1976"), CTTCompaniesJobs.Qualification.DOCTORATE);

        ////
        //// COMPANIES
        ////
        cttCompaniesJobs.addCompany("companyId1", "Company A", "The description (A)");
        cttCompaniesJobs.addCompany("companyId2", "Company B", "The description (B)");
        cttCompaniesJobs.addCompany("companyId3", "Company C", "The description (C)");
        cttCompaniesJobs.addCompany("companyId4", "Company D", "The description (D)");
        cttCompaniesJobs.addCompany("companyId5", "Company E", "The description (E)");
        cttCompaniesJobs.addCompany("companyId6", "Company F", "The description (F)");
        cttCompaniesJobs.addCompany("companyId7", "Company G", "The description (G)");

        ////
        //// REQUESTS
        ////
        cttCompaniesJobs.addRequest("requestId1", "jobOfferIdA1", "companyId1",
                "The description (request 1)", CTTCompaniesJobs.Qualification.DOCTORATE,
                5, createLocalDate("01-11-2023"),createLocalDate("10-12-2023"));

        cttCompaniesJobs.addRequest("requestId2", "jobOfferIdA2", "companyId1",
                "The description (request 2)", CTTCompaniesJobs.Qualification.HIGH_SCHOOL,
                3, createLocalDate("12-11-2023"),createLocalDate("08-12-2023"));

        cttCompaniesJobs.addRequest("requestId3", "jobOfferIdA3", "companyId1",
                "The description (request 3)", CTTCompaniesJobs.Qualification.HIGH_SCHOOL,
                3, createLocalDate("01-01-2023"),createLocalDate("31-12-2023"));

        cttCompaniesJobs.addRequest("requestId4", "jobOfferIdB1", "companyId2",
                "The description (request 4)", CTTCompaniesJobs.Qualification.HIGH_SCHOOL,
                4, createLocalDate("01-01-2023"),createLocalDate("30-12-2023"));

        cttCompaniesJobs.addRequest("requestId5", "jobOfferIdB2", "companyId2",
                "The description (request 5)", CTTCompaniesJobs.Qualification.HIGH_SCHOOL,
                3, createLocalDate("01-01-2023"),createLocalDate("15-01-2023"));


        return cttCompaniesJobs;
    }



}