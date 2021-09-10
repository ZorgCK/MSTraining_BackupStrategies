package one.microstream.quartz;

import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.micronaut.context.event.ShutdownEvent;
import io.micronaut.context.event.StartupEvent;
import io.micronaut.runtime.event.annotation.EventListener;
import jakarta.inject.Singleton;


@Singleton
public class MicronautEventListener
{
	private static final Logger LOG = LoggerFactory.getLogger(MicronautEventListener.class);
	
	@EventListener
	public void onStartupEvent(StartupEvent event)
	{
		try
		{
			QuartzUtils.initializeAndStartStorageBackupScheduler();
			LOG.info("StorageBackupScheduler successfully started");
		}
		catch(SchedulerException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@EventListener
	public void onShutdownEvent(ShutdownEvent event)
	{
		System.out.println("Couper storageserver wurde beendet");
		
		try
		{
			if(QuartzUtils.storageBackupScheduler != null)
			{
				QuartzUtils.storageBackupScheduler.shutdown(true);
			}
		}
		catch(final SchedulerException e)
		{
			LOG.error("Shutting down Backup scheduler failed.", e);
		}
	}
}
