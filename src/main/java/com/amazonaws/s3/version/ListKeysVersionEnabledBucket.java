package com.amazonaws.s3.version;

import java.io.IOException;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.s3.GeneratePreSignedUrl;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ListVersionsRequest;
import com.amazonaws.services.s3.model.S3VersionSummary;
import com.amazonaws.services.s3.model.VersionListing;

public class ListKeysVersionEnabledBucket {
	private static String bucketName = "chalapathisvssr";
	
	public static void main(String[] args) throws IOException {
        AmazonS3 s3client = new AmazonS3Client(new PropertiesCredentials(
				GeneratePreSignedUrl.class.getClassLoader()
				  .getResourceAsStream("aws.properties")));
        try {
            System.out.println("Listing objects");
            
            ListVersionsRequest request = new ListVersionsRequest()
                .withBucketName(bucketName)
                .withMaxResults(2);
                // you can specify .withPrefix to obtain version list for a specific object or objects with 
                // the specified key prefix.
   
            VersionListing versionListing;            
            do {
                versionListing = s3client.listVersions(request);
                for (S3VersionSummary objectSummary : 
                	versionListing.getVersionSummaries()) {
                    System.out.println(" - " + objectSummary.getKey() + "  " +
                            "(size = " + objectSummary.getSize() + ")" +
                    		"(versionID= " + objectSummary.getVersionId() + ")");

                }
                request.setKeyMarker(versionListing.getNextKeyMarker());
                request.setVersionIdMarker(versionListing.getNextVersionIdMarker());
            } while (versionListing.isTruncated());
         } catch (AmazonServiceException ase) {
            System.out.println("Caught an AmazonServiceException, " +
            		"which means your request made it " +
                    "to Amazon S3, but was rejected with an error response " +
                    "for some reason.");
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException, " +
            		"which means the client encountered " +
                    "an internal error while trying to communicate" +
                    " with S3, " +
                    "such as not being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
        }
    }
}
