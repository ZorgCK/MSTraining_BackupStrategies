package one.microstream;

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
