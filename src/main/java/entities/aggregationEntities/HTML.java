package entities.aggregationEntities;

/**
 * Created by Eugen on 10.09.2017.
 */
public class HTML {

    private String origin;
    private String title;
    private String link;
    private String description;
    private String pubDate;

    public HTML(){}

    public HTML(String origin, String title, String link, String description, String pubDate) {
        this.origin = origin;
        this.title = title;
        this.link = link;
        this.description = description;
        this.pubDate = pubDate;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HTML htmlAlert = (HTML) o;

        if (!origin.equals(htmlAlert.origin)) return false;
        if (!title.equals(htmlAlert.title)) return false;
        if (!link.equals(htmlAlert.link)) return false;
        if (!description.equals(htmlAlert.description)) return false;
        return pubDate.equals(htmlAlert.pubDate);

    }

    @Override
    public int hashCode() {
        int result = origin.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + link.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + pubDate.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "HTMLAlert{" +
                "origin='" + origin + '\'' +
                ", title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", description='" + description + '\'' +
                ", pubDate='" + pubDate + '\'' +
                '}';
    }
}
