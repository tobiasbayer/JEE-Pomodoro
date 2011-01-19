package org.tby.jeesample.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
public class Pomodoro {

    private Long mId;

    private Date mAddDate;

    private int mExternalInterrupts;

    private Boolean mVoid;

    private int mInternalInterrupts;

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
    public Date getAddDate() {
        return mAddDate;
    }

    public void setAddDate(Date aAddDate) {
        mAddDate = aAddDate;
    }

    public int getExternalInterrupts() {
        return mExternalInterrupts;
    }

    public void setExternalInterrupts(int aExternalInterrupts) {
        mExternalInterrupts = aExternalInterrupts;
    }

    public Boolean getVoid() {
        return mVoid;
    }

    public void setVoid(Boolean aVoid) {
        mVoid = aVoid;
    }

    public int getInternalInterrupts() {
        return mInternalInterrupts;
    }

    public void setInternalInterrupts(int aInternalInterrupts) {
        mInternalInterrupts = aInternalInterrupts;
    }
}
