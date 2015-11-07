package com.mgatelabs.lunar.model.service;

import com.mgatelabs.lunar.model.entities.Insertion;
import com.sun.istack.internal.NotNull;

/**
 * Created by Michael Glen Fuller Jr on 10/25/2015.
 */
public interface InsertionService {
    void saveInsertion(@NotNull final Insertion insertion);
}
