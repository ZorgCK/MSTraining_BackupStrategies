package one.microstream.quartz;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;


public class QuartzUtils
{
	public static Scheduler storageBackupScheduler;
	
	public static void initializeAndStartStorageBackupScheduler() throws SchedulerException
	{
		QuartzUtils.storageBackupScheduler = StdSchedulerFactory.getDefaultScheduler();
		final JobDetail job = JobBuilder.newJob(TaskStorageFullBackup.class).build();
		
		// Test Trigger - every 2 Minute
		final Trigger trigger = TriggerBuilder.newTrigger().startNow().withSchedule(
			CronScheduleBuilder.cronSchedule("0 */1 * ? * *")).build();
		
		QuartzUtils.storageBackupScheduler.scheduleJob(job, trigger);
		QuartzUtils.storageBackupScheduler.start();
	}
}
