package util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class StartupListener implements ServletContextListener {
    private MedicationReminderScheduler medicationReminderScheduler;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        DBUtil.createDatabaseAndTables();
        medicationReminderScheduler = new MedicationReminderScheduler();
        medicationReminderScheduler.start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (medicationReminderScheduler != null) {
            medicationReminderScheduler.stop();
        }
    }
}
