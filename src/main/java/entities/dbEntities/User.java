package entities.dbEntities;

/**
 * Created by Eugen on 22.08.2017.
 */
public class User {

    private Integer id;
    private String name;
    private String password;
    private String email;
    private Boolean isDeleted;

    public User(Integer id, String name, String password, String email, Boolean isDeleted){
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.isDeleted = isDeleted;
    }

    public User(){}


    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getEmail() {
        return email;

    }

    @Override
    public String toString(){
        return "User {" +
                " id = " + id +
                " name = " + name +
                " password = " + password +
                " email = " + email +
                " isDeleted = " + isDeleted +
                "}";
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj){
            return true;
        }
        if((obj == null) || (obj.getClass() != this.getClass())) {
            return false;
        }
        User that = (User) obj;
        if(this.getId()==null || that.getId()==null){
            return false;
        }
        return that.getId().equals(this.getId());
    }
}
