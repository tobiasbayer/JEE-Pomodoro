package org.tby.jeesample.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class User {

    private Long mId;

    private String mFullName;

    private String mPassword;

    private String mLogin;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return mId;
    }

    public void setId(Long aId) {
        mId = aId;
    }

    @NotNull
    public String getFullName() {
        return mFullName;
    }

    public void setFullName(String aFullName) {
        mFullName = aFullName;
    }

    @NotNull
    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String aPassword) {
        mPassword = aPassword;
    }

    @NotNull
    public String getLogin() {
        return mLogin;
    }

    public void setLogin(String aLogin) {
        mLogin = aLogin;
    }
}
