package org.tby.jeesample.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
public class ToDoList {

    private Long mId;

    private Date mActionDate;

    private Set<ToDo> mToDo = new HashSet<ToDo>();

    private SystemUser mOwner;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return mId;
    }

    public void setId(Long aId) {
        mId = aId;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    public Date getActionDate() {
        return mActionDate;
    }

    public void setActionDate(Date aActionDate) {
        mActionDate = aActionDate;
    }

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    public Set<ToDo> getToDo() {
        return mToDo;
    }

    public void setToDo(Set<ToDo> aToDo) {
        mToDo = aToDo;
    }

    public void addToDo(Set<ToDo> aToDo) {
        for (ToDo newElement : aToDo) {
            mToDo.add(newElement);
        }
    }

    public void addToDo(ToDo aToDo) {
        mToDo.add(aToDo);
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @NotNull
    public SystemUser getOwner() {
        return mOwner;
    }

    public void setOwner(SystemUser aOwner) {
        this.mOwner = aOwner;
    }
}
