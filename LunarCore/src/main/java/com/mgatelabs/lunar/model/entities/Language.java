package com.mgatelabs.lunar.model.entities;

import com.sun.istack.internal.NotNull;

import javax.persistence.*;
import java.util.Comparator;

/**
 * Created by Michael Glen Fuller Jr on 10/24/2015.
 */
@Entity
@Table(name="languages")
public class Language implements Comparator<Language> {

    private String key;
    private String title;
    private LanguageType type;

    public Language() {
    }

    public Language(@NotNull final String key, @NotNull final String title) {
        this(key,title, LanguageType.PACKAGED);
    }

    public Language(@NotNull final String key, @NotNull final String title, @NotNull final LanguageType type) {
        this.key = key;
        this.title = title;
        this.type = type;
    }

    @Id
    @Column(length = 6, unique = true)
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Column(name = "title", length = 32, unique = true)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name="type", length = 10)
    @Enumerated(EnumType.STRING)
    public LanguageType getType() {
        return type;
    }

    public void setType(LanguageType type) {
        this.type = type;
    }

    @Override
    public int compare(Language o1, Language o2) {
        return o1.getKey().compareTo(o2.getKey());
    }

    @Override
    public int hashCode() {
        return getKey().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Language) {
            Language obj2 = (Language) obj;
            return getKey().equals(obj2.getKey());
        }
        return false;
    }
}
