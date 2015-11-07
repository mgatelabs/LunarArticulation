package com.mgatelabs.lunar.model.dao;

import com.mgatelabs.lunar.model.entities.Insertion;
import com.sun.istack.internal.NotNull;
import org.springframework.stereotype.Repository;

/**
 * Created by Michael Glen Fuller Jr on 10/25/2015.
 */
@Repository("insertionDao")
public class InsertionDaoImpl extends AbstractDao implements InsertionDao {
    @Override
    public void saveInsertion(@NotNull Insertion insertion) {
        persist(insertion);
    }
}
