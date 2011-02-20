package com.tby.jeesample.presentation.wicket.application;

import org.apache.wicket.Page;
import org.jboss.weld.wicket.WeldApplication;

import com.tby.jeesample.presentation.wicket.pages.Today;

public class Application extends WeldApplication {

    @Override
    public Class<? extends Page> getHomePage() {
        return Today.class;
    }
}
