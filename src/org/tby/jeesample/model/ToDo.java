package org.tby.jeesample.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

@Entity
public class ToDo {

    private Long mId;

    private String mDescription;

    private int mEstimate;

    private Boolean mFinished;

    private Set<Pomodoro> mPomodoro = new HashSet<Pomodoro>();

    private User mOwner;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return mId;
    }

    public void setId(Long aId) {
        mId = aId;
    }

    @NotNull
    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String aDescription) {
        mDescription = aDescription;
    }

    public int getEstimate() {
        return mEstimate;
    }

    public void setEstimate(int aEstimate) {
        mEstimate = aEstimate;
    }

    @NotNull
    public Boolean getFinished() {
        return mFinished;
    }

    public void setFinished(Boolean aFinished) {
        mFinished = aFinished;
    }

    @OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    public Set<Pomodoro> getPomodoro() {
        return mPomodoro;
    }

    public void setPomodoro(Set<Pomodoro> aPomodoro) {
        mPomodoro = aPomodoro;
    }

    public void addPomodoro(Set<Pomodoro> aPomodoro) {
        for (Pomodoro newElement : aPomodoro) {
            mPomodoro.add(newElement);
        }
    }

    public void addPomodoro(Pomodoro aPomodoro) {
        mPomodoro.add(aPomodoro);
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @NotNull
    public User getOwner() {
        return mOwner;
    }

    public void setOwner(User aOwner) {
        mOwner = aOwner;
    }
}
