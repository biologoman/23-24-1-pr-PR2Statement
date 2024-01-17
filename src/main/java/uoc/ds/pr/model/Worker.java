package uoc.ds.pr.model;

import edu.uoc.ds.adt.helpers.Position;
import edu.uoc.ds.adt.sequential.LinkedList;
import edu.uoc.ds.adt.sequential.List;
import edu.uoc.ds.traversal.Iterator;
import edu.uoc.ds.traversal.Traversal;
import uoc.ds.pr.CTTCompaniesJobs;
import uoc.ds.pr.CTTCompaniesJobsPR2;
import uoc.ds.pr.util.LevelHelper;

import java.time.LocalDate;

public class Worker implements Comparable<Worker>{

    private String id;
    private String name;
    private String surname;
    private LocalDate dateOfBirth;
    private CTTCompaniesJobs.Qualification qualification;

    private List<JobOffer> jobOffers;
    private int workingDays;


    public Worker(String id, String name, String surname,
                  LocalDate dateOfBirth, CTTCompaniesJobs.Qualification qualification) {
        this.setId(id);
        this.setName(name);
        this.setSurname(surname);
        this.setDateOfBirth(dateOfBirth);
        this.setQualification(qualification);
        this.jobOffers = new LinkedList<>();
        this.workingDays = 0;
    }


    public void update(String name, String surname,
                       LocalDate dateOfBirth, CTTCompaniesJobs.Qualification qualification) {
        this.setName(name);
        this.setSurname(surname);
        this.setDateOfBirth(dateOfBirth);
        this.setQualification(qualification);
    }

    private void setQualification(CTTCompaniesJobs.Qualification qualification) {
        this.qualification = qualification;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public CTTCompaniesJobs.Qualification getQualification() {
        return qualification;
    }


    public void addJobOffer(JobOffer jobOffer) {
        this.workingDays+=jobOffer.getWorkingDays();
        this.jobOffers.insertEnd(jobOffer);
    }

    public Iterator<JobOffer> getJobOffers() {
        return jobOffers.values();
    }

    public boolean isInJobOffer(JobOffer jobOffer) {
        Iterator<Enrollment> it = jobOffer.enrollments();
        return isInJobOffer(it);
    }

    public boolean isInJobOfferAsSubstitute(JobOffer jobOffer) {
        Iterator<Enrollment> it = jobOffer.substitutes();
        return isInJobOffer(it);
    }



    public boolean isInJobOffer(Iterator<Enrollment> it){
        boolean found = false;

        Enrollment enrollment = null;
        while (!found && it.hasNext()){
            enrollment = it.next();
            found = enrollment.getWorker().getId().equals(this.id);
        }
        return found;
    }

    public int getWorkingDays() {
        return this.workingDays;
    }

    //PR2
    @Override
    public int compareTo(Worker w) {
        return Integer.compare(this.getLevel().getValue(), w.getLevel().getValue());
    }

    public CTTCompaniesJobsPR2.Level getLevel() {
        return LevelHelper.getLevel((getWorkingDays()*8));
    }

}
