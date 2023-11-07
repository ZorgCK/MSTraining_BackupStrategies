package one.microstream;

import software.amazon.awssdk.auth.credentials.SystemPropertyCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;


public class S3Utils
{
	public static S3Client getS3Client()
	{
		System.setProperty("aws.accessKeyId", "");
		System.setProperty("aws.secretAccessKey", "");
		
		S3Client client =
			S3Client.builder().region(Region.EU_CENTRAL_1).credentialsProvider(
				SystemPropertyCredentialsProvider.create()).build();
		
		return client;
	}
}
