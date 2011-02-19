package com.tby.jeesample.presentation.wicket.application;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;

import com.tby.jeesample.presentation.wicket.pages.Today;

public class Application extends WebApplication {

    @Override
    public Class<? extends Page> getHomePage() {
        return Today.class;
    }

}
