package com.mgatelabs.lunar;

import com.mgatelabs.lunar.shell.RootAppSLV;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Michael Glen Fuller Jr on 10/23/2015.
 */
public class LunarRocket {

    public static void main(String[] args) throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");

        final Application app = context.getBean(Application.class);

        app.init();

        RootAppSLV rootSLV = new RootAppSLV(app);

        rootSLV.run();

        /*
        ProjectService ps = context.getBean(ProjectService.class);
        SessionFactoryImplementor factory = (SessionFactoryImplementor) context.getBean("sessionFactory");
        Session session = factory.openSession();

        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            Project proj = new Project();
            proj.setKey("mobilevrstation");
            proj.setTitle("Mobile VR Station");

            ps.saveProject(proj);

            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null) tx.rollback();
            throw e; // or display error message
        } finally {
            session.close();
        }
        */
    }

}
