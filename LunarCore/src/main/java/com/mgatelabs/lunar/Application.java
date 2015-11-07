package com.mgatelabs.lunar;

import com.google.common.collect.Sets;
import com.mgatelabs.lunar.model.entities.Language;
import com.mgatelabs.lunar.model.entities.LanguageType;
import com.mgatelabs.lunar.model.service.*;
import com.mgatelabs.lunar.utils.Closer;
import com.mgatelabs.lunar.utils.CommitTransaction;
import com.sun.istack.internal.NotNull;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

/**
 * This class will host all other services in a centralized location
 *
 * Created by Michael Glen Fuller Jr on 10/24/2015.
 */
public class Application {

    private @NotNull final Set<Language> allLanguages;

    @Autowired
    @NotNull
    private ProjectService projectService;

    @NotNull
    public ProjectService getProjectService() {
        return projectService;
    }

    @Autowired
    @NotNull
    private LanguageService languageService;

    @NotNull
    public LanguageService getLanguageService() {
        return languageService;
    }

    @Autowired
    @NotNull
    private InsertionService insertionService;

    @NotNull
    public InsertionService getInsertionService() {
        return insertionService;
    }

    @Autowired
    @NotNull
    private ProjectKeyService projectKeyService;

    @NotNull
    public ProjectKeyService getProjectKeyService() {
        return projectKeyService;
    }

    @Autowired
    @NotNull
    private ProjectKeyTextService projectKeyTextService;

    @NotNull
    public ProjectKeyTextService getProjectKeyTextService() {
        return projectKeyTextService;
    }

    @Autowired
    @NotNull
    private ProjectSourceService projectSourceService;

    @NotNull
    public ProjectSourceService getProjectSourceService() {
        return projectSourceService;
    }

    @Autowired
    @NotNull
    private ProjectFileNameService projectFileNameService;

    public ProjectFileNameService getProjectFileNameService() {
        return projectFileNameService;
    }

    @Autowired
    @NotNull
    private ProjectLanguageService projectLanguageService;

    @NotNull
    public ProjectLanguageService getProjectLanguageService() {
        return projectLanguageService;
    }

    @Autowired
    @NotNull
    private ProjectVersionService projectVersionService;

    public ProjectVersionService getProjectVersionService() {
        return projectVersionService;
    }

    @NotNull
    private final SessionFactoryImplementor sessionFactory;

    @NotNull
    public SessionFactoryImplementor getSessionFactory() {
        return sessionFactory;
    }

    public Application(SessionFactoryImplementor sessionFactory) {
        this.sessionFactory = sessionFactory;
        allLanguages = Sets.newHashSet();
    }

    public void init() throws Exception {
        // Check languages
        addDefaultLanguages();

    }

    private void addDefaultLanguages() throws Exception {

        Set<Language> knownLanguages = Sets.newHashSet();

        knownLanguages.add(new Language("Base", "Base", LanguageType.BASE));

        knownLanguages.add(new Language("zh", "Chinese"));
        knownLanguages.add(new Language("en", "English"));
        knownLanguages.add(new Language("fr", "French"));
        knownLanguages.add(new Language("de", "German"));
        knownLanguages.add(new Language("ja", "Japanese"));
        knownLanguages.add(new Language("ko", "Korean"));
        knownLanguages.add(new Language("nl", "Dutch"));
        knownLanguages.add(new Language("pl", "Polish"));
        knownLanguages.add(new Language("pt", "Portuguese"));
        knownLanguages.add(new Language("ru", "Russian"));
        knownLanguages.add(new Language("es", "Spanish"));

        final Session session = getSessionFactory().openSession();
        Transaction tx = null;
        try {
            final List<Language> storedLanguages = languageService.listAllLanguages();

            allLanguages.addAll(storedLanguages);

            knownLanguages.removeAll(storedLanguages);
            if (knownLanguages.size() == 0) {
                return;
            }
            tx = session.beginTransaction();
            for (Language language : knownLanguages) {
                languageService.saveLanguage(language);
            }
            allLanguages.addAll(knownLanguages);
            tx.commit();
        } catch (Exception ex) {
            if (tx != null) tx.rollback();
            throw ex;
        } finally {
            session.close();
        }

    }

    public boolean runCommitTransactionSilent(CommitTransaction transaction) {
        try {
            return runCommitTransaction(transaction);
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean runCommitTransaction(CommitTransaction transaction) throws Exception {
        final Session session = getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            if (!transaction.commit(this)) {
                throw new Exception("Transaction method reported a failure");
            }
            tx.commit();
            return true;
        } catch (Exception ex) {
            if (tx != null) tx.rollback();
            throw ex;
        } finally {
            session.close();
        }
    }

    public boolean runReadTransactionSilent(CommitTransaction transaction) {
        try {
            return runReadTransaction(transaction);
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean runReadTransaction(CommitTransaction transaction) throws Exception {
        final Session session = getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            if (!transaction.commit(this)) {
                throw new Exception("Session method reported a failure");
            }
            tx.rollback();
            return true;
        } catch (Exception ex) {
            if (tx != null) tx.rollback();
            throw ex;
        } finally {
            session.close();
        }
    }

    public boolean isValidLanguageIdentifier(String input) {
        for (Language language: allLanguages) {
            if (language.getKey().equalsIgnoreCase(input)) {
                return true;
            }
        }
        return false;
    }

    public String getNameForLanguageId(String input) {
        for (Language language: allLanguages) {
            if (language.getKey().equalsIgnoreCase(input)) {
                return language.getTitle();
            }
        }
        return "Unknown";
    }
}
