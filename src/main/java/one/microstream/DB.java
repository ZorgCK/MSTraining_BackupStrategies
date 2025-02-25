package one.microstream;

import java.net.URL;
import java.util.Optional;

import org.eclipse.store.storage.embedded.configuration.types.EmbeddedStorageConfiguration;
import org.eclipse.store.storage.embedded.types.EmbeddedStorageManager;

import io.micronaut.core.io.ResourceResolver;
import io.micronaut.core.io.scan.ClassPathResourceLoader;
import jakarta.inject.Singleton;


@Singleton
public class DB
{
	public EmbeddedStorageManager	storageManager;
	public final DataRoot			root	= new DataRoot();
	
	public EmbeddedStorageManager getStorageManager()
	{
		if(this.storageManager == null)
		{
			ClassPathResourceLoader loader = new ResourceResolver().getLoader(ClassPathResourceLoader.class).get();
			Optional<URL> resource = loader.getResource("microstream.xml");
			
			// @formatter:off
			storageManager = EmbeddedStorageConfiguration.load(resource.get().getPath())
				.createEmbeddedStorageFoundation()
				.createEmbeddedStorageManager(root).start();
		}
		
		return this.storageManager;
	}
}
