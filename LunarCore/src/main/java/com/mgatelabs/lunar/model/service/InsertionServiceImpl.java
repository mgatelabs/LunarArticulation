package com.mgatelabs.lunar.model.service;

import com.mgatelabs.lunar.model.dao.InsertionDao;
import com.mgatelabs.lunar.model.entities.Insertion;
import com.sun.istack.internal.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Michael Glen Fuller Jr on 10/25/2015.
 */
@Service("insertionService")
@Transactional
public class InsertionServiceImpl implements InsertionService {

    @Autowired
    private InsertionDao dao;

    @Override
    public void saveInsertion(@NotNull Insertion insertion) {
        dao.saveInsertion(insertion);
    }
}
