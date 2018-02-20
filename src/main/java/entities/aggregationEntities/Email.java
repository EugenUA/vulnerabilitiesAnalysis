package entities.aggregationEntities;
/**
 * Created by Eugen on 04.09.2017.
 */
public class Email {

    private String from;
    private String to;
    private String subject;
    private String content;
    private String receivedDate;

    private String htmlFormat;

    public Email(){ }

    public Email(String from, String to, String subject, String content, String receivedDate){
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.content = content;
        this.receivedDate = receivedDate;
    }


    public String getHtmlFormat() {
        return htmlFormat;
    }

    public void setHtmlFormat(String htmlFormat) {
        this.htmlFormat = htmlFormat;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(String receivedDate) {
        this.receivedDate = receivedDate;
    }

    @Override
    public String toString() {
        return "Email{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", subject='" + subject + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Email email = (Email) o;

        if (!from.equals(email.from)) return false;
        if (!to.equals(email.to)) return false;
        if (subject != null ? !subject.equals(email.subject) : email.subject != null) return false;
        return !(content != null ? !content.equals(email.content) : email.content != null);

    }

    @Override
    public int hashCode() {
        int result = from.hashCode();
        result = 31 * result + to.hashCode();
        result = 31 * result + (subject != null ? subject.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        return result;
    }
}
