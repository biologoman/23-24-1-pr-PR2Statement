package uoc.ds.pr.model;

import edu.uoc.ds.adt.sequential.LinkedList;
import edu.uoc.ds.adt.sequential.List;
import edu.uoc.ds.traversal.Iterator;

public class Company {
    private String id;
    private String name;
    private String description;
    private List<JobOffer> jobOffers;

    public Company(String id, String name, String description) {
        this.setId(id);
        this.setName(name);
        this.setDescription(description);
        this.jobOffers = new LinkedList<>();
    }

    public void update(String name, String description) {
        this.setName(name);
        this.setDescription(description);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addJobOffer(JobOffer jobOffer) {
        this.jobOffers.insertEnd(jobOffer);
    }

    public Iterator<JobOffer> getJobOffers() {
        return this.jobOffers.values();
    }
}
