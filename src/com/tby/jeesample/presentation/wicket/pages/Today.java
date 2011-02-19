package com.tby.jeesample.presentation.wicket.pages;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;

public class Today extends WebPage {

    public Today() {
        add(new Label("title", "Today"));
    }

}
