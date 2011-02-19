package com.tby.jeesample.common;

import java.util.Comparator;

import com.tby.jeesample.model.Pomodoro;

public class PomodoroAddDateComparator implements Comparator<Pomodoro> {

    @Override
    public int compare(Pomodoro aP1, Pomodoro aP2) {
        return aP2.getAddDate().compareTo(aP1.getAddDate());
    }
}
