package one.microstream.quartz;

import org.eclipse.store.afs.nio.types.NioFileSystem;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import one.microstream.DB;


public class TaskStorageFullBackup implements Job
{
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException
	{
		DB.storageManager.issueFullBackup(NioFileSystem.New().ensureDirectoryPath("fullbackup/data"));
		
		System.out.println("Backup successfully done");
	}
}
