package one.microstream;

import org.eclipse.store.afs.aws.s3.types.S3Connector;
import org.eclipse.store.afs.blobstore.types.BlobStoreFileSystem;
import org.eclipse.store.afs.nio.types.NioFileSystem;
import org.eclipse.store.storage.embedded.types.EmbeddedStorageFoundation;
import org.eclipse.store.storage.embedded.types.EmbeddedStorageManager;
import org.eclipse.store.storage.types.Storage;
import org.eclipse.store.storage.types.StorageBackupSetup;
import org.eclipse.store.storage.types.StorageChannelCountProvider;
import org.eclipse.store.storage.types.StorageConfiguration;


public class DB
{
	public static EmbeddedStorageManager	storageManager;
	public final static DataRoot			root		= new DataRoot();
	private static String					bucketname	= "ms-training-backup-example";
	
	static
	{
		NioFileSystem storageFileSystem = NioFileSystem.New();
		BlobStoreFileSystem backupS3Filesystem = BlobStoreFileSystem.New(
			S3Connector.Caching(S3Utils.getS3Client()));
		
		// @formatter:off
		
		storageManager = EmbeddedStorageFoundation.New()
			.setConfiguration(
				StorageConfiguration.Builder()
					.setStorageFileProvider(
						Storage.FileProviderBuilder(storageFileSystem)
							.setDirectory(storageFileSystem.ensureDirectoryPath("foundationStorage"))
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
