package one.microstream;

import java.net.URL;
import java.util.Optional;

import io.micronaut.core.io.ResourceResolver;
import io.micronaut.core.io.scan.ClassPathResourceLoader;
import one.microstream.afs.aws.s3.types.S3Connector;
import one.microstream.afs.blobstore.types.BlobStoreFileSystem;
import one.microstream.afs.nio.types.NioFileSystem;
import one.microstream.storage.embedded.types.EmbeddedStorageFoundation;
import one.microstream.storage.embedded.types.EmbeddedStorageManager;
import one.microstream.storage.types.Storage;
import one.microstream.storage.types.StorageBackupSetup;
import one.microstream.storage.types.StorageChannelCountProvider;
import one.microstream.storage.types.StorageConfiguration;


public class DB
{
	public static EmbeddedStorageManager	storageManager;
	public final static DataRoot			root		= new DataRoot();
	private static String					bucketname	= "ms-training-backup-example";
	
	static
	{
		ClassPathResourceLoader loader = new ResourceResolver().getLoader(ClassPathResourceLoader.class).get();
		Optional<URL> resource = loader.getResource("microstream.xml");
		
		BlobStoreFileSystem backupS3Filesystem = BlobStoreFileSystem.New(
			S3Connector.Caching(S3Utils.getS3Client()));
		
		// @formatter:off
		
		NioFileSystem          fileSystem     = NioFileSystem.New();
		storageManager = EmbeddedStorageFoundation.New()
			.setConfiguration(
				StorageConfiguration.Builder()
					.setStorageFileProvider(
						Storage.FileProviderBuilder(fileSystem)
							.setDirectory(fileSystem.ensureDirectoryPath("foundationStorage"))
							.createFileProvider()
					)
					.setBackupSetup(
						StorageBackupSetup.New(backupS3Filesystem.ensureDirectoryPath(bucketname)))
					.setChannelCountProvider(StorageChannelCountProvider.New(4))
					.createConfiguration()
			)
			.setRoot(root)
			.createEmbeddedStorageManager().start();
	}
}
