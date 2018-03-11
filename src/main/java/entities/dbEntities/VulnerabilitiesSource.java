package entities.dbEntities;

/**
 * @brief The class for intern use in the program. Saves sources and last successful polling time of HTML and RSS sources
 * @details Class is not visible to users. Only the program operates with entries.
 * @date 20.02.2018
 * @author Gruzdev Eugen
 */

public class VulnerabilitiesSource {

    private int id;
    private String source_name; /* Ex: Secunia */
    private String source_type; /* HTML or RSS */
    private String link;
    private String last_access;
    private String notes;

    public VulnerabilitiesSource(){}

    public VulnerabilitiesSource(int id, String source_name, String source_type, String link, String last_access, String notes) {
        this.id = id;
        this.source_name = source_name;
        this.source_type = source_type;
        this.link = link;
        this.last_access = last_access;
        this.notes = notes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSource_name() {
        return source_name;
    }

    public void setSource_name(String source_name) {
        this.source_name = source_name;
    }

    public String getSource_type() {
        return source_type;
    }

    public void setSource_type(String source_type) {
        this.source_type = source_type;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLast_access() {
        return last_access;
    }

    public void setLast_access(String last_access) {
        this.last_access = last_access;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "VulnerabilitiesSource{" +
                "id=" + id +
                ", source_name='" + source_name + '\'' +
                ", source_type='" + source_type + '\'' +
                ", link='" + link + '\'' +
                ", last_access='" + last_access + '\'' +
                ", notes='" + notes + '\'' +
                '}';
    }
}
