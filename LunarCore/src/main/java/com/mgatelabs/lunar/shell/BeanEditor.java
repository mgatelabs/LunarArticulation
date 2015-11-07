package com.mgatelabs.lunar.shell;

import com.google.common.collect.Lists;
import com.mgatelabs.lunar.Application;
import com.mgatelabs.lunar.utils.AbstractAppSLV;
import com.mgatelabs.lunar.utils.ShellImpl;
import com.mgatelabs.lunar.utils.fields.EditableAnnotation;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by Michael Glen Fuller Jr on 10/30/2015.
 */
public class BeanEditor<T> extends AbstractAppSLV {

    private
    @NotNull
    Class<T> referenceClass;
    private
    @Nullable
    T pojo;
    private
    @NotNull
    final List<EditFieldLink> annotationList;
    private final boolean create;
    private boolean success;

    public BeanEditor(Class<T> refClass, @NotNull final ShellImpl shell, @NotNull final Application application) {
        this(refClass, true, null, shell, application);
    }

    public BeanEditor(Class<T> refClass, T pojo, @NotNull final ShellImpl shell, @NotNull final Application application) {
        this(refClass, false, pojo, shell, application);
    }

    private BeanEditor(Class<T> referenceClass, boolean create, @Nullable final T pojo, @NotNull final ShellImpl shell, @NotNull final Application application) {
        super(create ? "Create Bean" : "Edit Bean", "beanedit", shell, application);
        this.pojo = pojo;
        this.create = create;
        this.referenceClass = referenceClass;
        annotationList = Lists.newArrayList();
        success = false;
    }

    private void buildInfo() {
        for (Field f : this.referenceClass.getDeclaredFields()) {
            EditableAnnotation e = f.getAnnotation(EditableAnnotation.class);
            if (e != null) {
                annotationList.add(new EditFieldLink(f, e));
            }
        }
    }

    private static class EditFieldLink {
        private
        @NotNull
        final Field field;
        private
        @NotNull
        final EditableAnnotation editableAnnotation;

        public EditFieldLink(@NotNull final Field field, @NotNull final EditableAnnotation editableAnnotation) {
            this.field = field;
            this.editableAnnotation = editableAnnotation;
        }

        public Field getField() {
            return field;
        }

        public EditableAnnotation getEditableAnnotation() {
            return editableAnnotation;
        }
    }

    @Nullable
    public T getPojo() {
        return pojo;
    }

    @Override
    public void run() {
        super.run();
        buildInfo();
        final T middleEntity;
        if (create) {
            try {
                middleEntity = createRun();
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        } else {
            try {
                middleEntity = copyEntity();
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
        if (middleEntity != null) {
            editRun(middleEntity);
        }
    }

    public boolean isSuccess() {
        return success;
    }

    private T createRun() throws Exception {
        // Loop through fields, have them enter them
        // Make the new entity
        final T entity = referenceClass.newInstance();
        for (EditFieldLink editFieldLink : annotationList) {
            switch (editFieldLink.getEditableAnnotation().type()) {
                case STRING:
                case KEY:
                case LANGUAGE:
                    BeanUtils.setProperty(entity, editFieldLink.getField().getName(), promptForString(editFieldLink.getField().getName()));
                    break;
                case INT:
                case LONG:
                    BeanUtils.setProperty(entity, editFieldLink.getField().getName(), promptForInt(editFieldLink.getField().getName(), 0));
            }
        }
        return entity;
    }

    private T copyEntity() throws Exception {
        final T entity = referenceClass.newInstance();
        for (EditFieldLink editFieldLink : annotationList) {
            String val = BeanUtils.getProperty(pojo, editFieldLink.getField().getName());
            switch (editFieldLink.getEditableAnnotation().type()) {
                case STRING:
                case KEY:
                case LANGUAGE:
                case INT:
                case LONG:
                    BeanUtils.setProperty(entity, editFieldLink.getField().getName(), val);
            }
        }
        return entity;
    }

    private boolean saveEntity(T sourceEntity) {

        if (pojo == null) {
            pojo = sourceEntity;
            success = true;
            return true;
        }

        try {
            for (EditFieldLink editFieldLink : annotationList) {
                final String value = BeanUtils.getProperty(sourceEntity, editFieldLink.getField().getName());
                BeanUtils.setProperty(pojo, editFieldLink.getField().getName(), value);
            }
            success = true;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return success;
    }

    private void editRun(T entity) {

        // Verify

        restartLoop:
        while (true) {

            info("Bean Values:");


            int i = 0;
            for (EditFieldLink editFieldLink : annotationList) {
                try {
                    String value = BeanUtils.getProperty(entity, editFieldLink.getField().getName());

                    switch (editFieldLink.getEditableAnnotation().type()) {
                        case STRING:
                        case KEY: {
                            info((i + 1) + ") " + editFieldLink.getField().getName() + " = '" + value + "'");
                        } break;
                        case LONG:
                        case INT:
                            info((i + 1) + ") " + editFieldLink.getField().getName() + " = " + value + "");
                            break;
                        case LANGUAGE:

                            info((i + 1) + ") " + editFieldLink.getField().getName() + " = "+ getApp().getNameForLanguageId(value) +" (" + value + ")");

                            break;
                    }


                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                i++;
            }
            info("");

            String commandString = promptForString("(E)xit|(C)hange ROW Value|(S)ave");

            String[] commandRowValues = commandString.split(" ", 3);

            if (commandRowValues.length > 0) {
                String command = commandRowValues[0];
                if (command.length() > 0) {
                    char c = command.charAt(0);

                    switch (c) {
                        case 'e':
                        case 'E':
                            return;
                        case 's':
                        case 'S':
                            if (saveEntity(entity)) {
                                info("Entity Written");
                                return;
                            } else {
                                error("Save Failed");
                            }
                            break;
                        case 'c':
                        case 'C': {
                            if (commandRowValues.length > 1) {
                                int row = parseInt(commandRowValues[1], -1);
                                if (row < 1) {
                                    error("Invalid Row Number Format");
                                } else {
                                    row -= 1;
                                    if (row >= 0 && row < annotationList.size()) {
                                        final String value;
                                        if (commandRowValues.length > 2) {
                                            value = commandRowValues[2];
                                        } else {
                                            value = "";
                                        }
                                        EditFieldLink editFieldLink = annotationList.get(row);
                                        try {
                                            switch (editFieldLink.getEditableAnnotation().type()) {
                                                case LANGUAGE:
                                                    if (value.length() > 0) {
                                                        if (getApp().isValidLanguageIdentifier(value)) {
                                                            BeanUtils.setProperty(entity, editFieldLink.getField().getName(), value);
                                                        } else {
                                                            error("Unknown Language Identifier");
                                                        }
                                                    }
                                                case INT: {
                                                    int v = parseInt(value, editFieldLink.getEditableAnnotation().minValue() - 1);
                                                    if (v >= editFieldLink.getEditableAnnotation().minValue() && v <= editFieldLink.getEditableAnnotation().maxValue()) {
                                                        BeanUtils.setProperty(entity, editFieldLink.getField().getName(), v);
                                                    }
                                                }
                                                break;
                                                case LONG: {
                                                    long v = parseLong(value, editFieldLink.getEditableAnnotation().minValue() - 1);
                                                    if (v >= editFieldLink.getEditableAnnotation().minValue() && v <= editFieldLink.getEditableAnnotation().maxValue()) {
                                                        BeanUtils.setProperty(entity, editFieldLink.getField().getName(), v);
                                                    }
                                                }
                                                break;
                                                case STRING: {
                                                    if (value.length() <= editFieldLink.getEditableAnnotation().length()) {
                                                        BeanUtils.setProperty(entity, editFieldLink.getField().getName(), value);
                                                    } else {
                                                        error("Could not change string value, length would overflow");
                                                    }
                                                }
                                                break;
                                            }
                                        } catch (IllegalAccessException e) {
                                            e.printStackTrace();
                                        } catch (InvocationTargetException e) {
                                            e.printStackTrace();
                                        }
                                    } else {
                                        error("Invalid Row Number");
                                    }
                                }
                            }
                            continue restartLoop;
                        }
                    }

                }
            }
            error("Unknown Command");
        }

    }

    private int parseInt(@NotNull final String input, int onFail) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException ex) {
            return onFail;
        }
    }

    private long parseLong(@NotNull final String input, final long onFail) {
        try {
            return Long.parseLong(input);
        } catch (NumberFormatException ex) {
            return onFail;
        }
    }
}
