package com.tby.jeesample.common;

import java.util.Comparator;

import org.tby.jeesample.model.IEntity;

public class EntityIdComparator implements Comparator<IEntity> {

    @Override
    public int compare(IEntity e1, IEntity e2) {
        return e1.getId().compareTo(e2.getId());
    }
}
