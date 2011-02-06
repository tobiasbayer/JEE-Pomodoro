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

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private Date addDate;

    private int externalInterrupts;

    private boolean voidPomodoro;

    private int internalInterrupts;

    private boolean finished;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    public int getExternalInterrupts() {
        return externalInterrupts;
    }

    public void setExternalInterrupts(int externalInterrupts) {
        this.externalInterrupts = externalInterrupts;
    }

    public int getInternalInterrupts() {
        return internalInterrupts;
    }

    public void setInternalInterrupts(int internalInterrupts) {
        this.internalInterrupts = internalInterrupts;
    }

    public boolean isVoidPomodoro() {
        return voidPomodoro;
    }

    public void setVoidPomodoro(boolean voidPomodoro) {
        this.voidPomodoro = voidPomodoro;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public boolean isFinished() {
        return finished;
    }

}
