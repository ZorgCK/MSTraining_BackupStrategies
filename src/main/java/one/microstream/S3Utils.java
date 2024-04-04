package one.microstream;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;


public class S3Utils
{
	public static S3Client getS3Client()
	{
//		System.setProperty("aws.accessKeyId", System.getenv().get("AWS_ACCESS_KEY_ID"));
//		System.setProperty("aws.secretAccessKey", System.getenv().get("AWS_SECRET_ACCESS_KEY"));
		
		S3Client client =
			S3Client.builder().region(Region.EU_CENTRAL_1).build();
		
		return client;
	}
}
