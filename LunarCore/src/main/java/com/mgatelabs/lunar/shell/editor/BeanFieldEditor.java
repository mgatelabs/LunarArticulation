package com.mgatelabs.lunar.shell.editor;

import com.mgatelabs.lunar.Application;
import com.mgatelabs.lunar.utils.AbstractAppSLV;
import com.mgatelabs.lunar.utils.ShellImpl;
import com.mgatelabs.lunar.utils.ValueUtils;
import com.mgatelabs.lunar.utils.fields.EditableAnnotation;
import com.sun.istack.internal.NotNull;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by Michael Glen Fuller Jr on 10/31/2015.
 */
public class BeanFieldEditor<T> extends AbstractAppSLV {

    private
    @NotNull
    final Field field;
    private
    @NotNull
    final T pojo;
    private
    @NotNull
    final EditableAnnotation anno;


    public BeanFieldEditor(@NotNull ShellImpl shell, @NotNull Application app, Field field, T pojo, @NotNull final EditableAnnotation anno) {
        super("Edit: " + field.getName(), field.getName(), shell, app);
        this.field = field;
        this.pojo = pojo;
        this.anno = anno;
    }

    @Override
    public void run() {
        super.run();

        try {

            switch (anno.type()) {
                case STRING: {
                    int option = promptForOptions("CANCEL", "SET", "BLANK");

                    if (option <= 0) {
                        return;
                    }
                    String val = option == 2 ? "" : promptForString("String");
                    if (val.length() <= anno.length()) {
                        BeanUtils.setProperty(pojo, field.getName(), val);
                    } else {
                        shellExited("Invalid Length");
                    }
                }
                break;
                case INT:
                case LONG: {
                    int option = promptForOptions("CANCEL", "SET");
                    if (option <= 0) {
                        return;
                    }
                    int val = promptForInt("Integer", anno.minValue() - 1);
                    if (val >= anno.minValue() && val <= anno.maxValue()) {
                        BeanUtils.setProperty(pojo, field.getName(), val);
                    }
                }
                break;
                case KEY: {
                    int option = promptForOptions("CANCEL", "SET");
                    if (option <= 0) {
                        return;
                    }
                    String key = promptForString("New Language Key");
                    if (ValueUtils.isValidKey(key, anno.length())) {
                        BeanUtils.setProperty(pojo, field.getName(), key);
                    } else {
                        shellExited("Invalid Language");
                    }
                }
                break;
                case LANGUAGE: {
                    int option = promptForOptions("CANCEL", "SET");
                    if (option <= 0) {
                        return;
                    }
                    String lang = promptForString("New Language Key");
                    if (getApp().isValidLanguageIdentifier(lang)) {
                        BeanUtils.setProperty(pojo, field.getName(), lang);
                    } else {
                        shellExited("Invalid Language");
                    }
                }
                break;
            }

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
