package uoc.ds.pr.model;

public class Rating {
    private JobOffer jobOffer;
    private int value;
    private String message;
    private Worker worker;

    public Rating(JobOffer jobOffer, int value, String message, Worker worker) {
        this.jobOffer = jobOffer;
        this.value = value;
        this.message = message;
        this.worker = worker;
    }

    public int getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }
}
