package es.codeurjc.eolopark.model;
import java.util.List;

import jakarta.persistence.*;


@Entity
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //@Column(unique = true)
    private String name;

    private String encodedPassword;

    private boolean isPremium = false; //By default, users are not premium



    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles;
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EoloPark> eoloParks;

    public User() {
    }


    public User(String name, String encodedPassword, String... roles) {
        this.name = name;
        this.encodedPassword = encodedPassword;
        this.roles = List.of(roles);
    }


    public boolean isPremium() {
        return isPremium;
    }

    public void setPremium(boolean isPremium) {
        this.isPremium = isPremium;
    }

    public List<EoloPark> getEoloParks(){
        return eoloParks;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEncodedPassword() {
        return encodedPassword;
    }

    public void setEncodedPassword(String encodedPassword) {
        this.encodedPassword = encodedPassword;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }


    public static Object builder() {

        throw new UnsupportedOperationException("Unimplemented method 'builder'");
    }

    public void setId(long id) {
    }
}