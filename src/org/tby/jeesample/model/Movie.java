package org.tby.jeesample.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;

@Entity
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(min = 1)
    private String title;

    public Movie() {
    }

    public Movie(String aTitle) {
        setTitle(aTitle);
    }

    public void setId(Long aId) {
        id = aId;
    }

    public Long getId() {
        return id;
    }

    public void setTitle(String aTitle) {
        title = aTitle;
    }

    public String getTitle() {
        return title;
    }
}
