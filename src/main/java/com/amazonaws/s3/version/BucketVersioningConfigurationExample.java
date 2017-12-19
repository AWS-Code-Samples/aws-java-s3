package com.amazonaws.s3.version;

import java.io.IOException;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.BucketVersioningConfiguration;
import com.amazonaws.services.s3.model.SetBucketVersioningConfigurationRequest;

public class BucketVersioningConfigurationExample {
    public static String bucketName = "chalapathisvssr"; 
    public static AmazonS3Client s3Client;

    public static void main(String[] args) throws IOException {
        s3Client = new AmazonS3Client(new ProfileCredentialsProvider());
        s3Client.setRegion(Region.getRegion(Regions.US_WEST_2));
        try {

            // 1. Enable versioning on the bucket.
        	BucketVersioningConfiguration configuration = 
        			new BucketVersioningConfiguration().withStatus("Enabled");
            
			SetBucketVersioningConfigurationRequest setBucketVersioningConfigurationRequest = 
					new SetBucketVersioningConfigurationRequest(bucketName,configuration);
			
			s3Client.setBucketVersioningConfiguration(setBucketVersioningConfigurationRequest);
			
			// 2. Get bucket versioning configuration information.
			BucketVersioningConfiguration conf = s3Client.getBucketVersioningConfiguration(bucketName);
			 System.out.println("bucket versioning configuration status:    " + conf.getStatus());

        } catch (AmazonS3Exception amazonS3Exception) {
            System.out.format("An Amazon S3 error occurred. Exception: %s", amazonS3Exception.toString());
        } catch (Exception ex) {
            System.out.format("Exception: %s", ex.toString());
        }        
    }
}
