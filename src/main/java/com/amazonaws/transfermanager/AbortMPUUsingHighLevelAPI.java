package com.amazonaws.transfermanager;

import java.util.Date;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.s3.GeneratePreSignedUrl;
import com.amazonaws.services.s3.transfer.TransferManager;

public class AbortMPUUsingHighLevelAPI {

    public static void main(String[] args) throws Exception {
        String existingBucketName = "chalapathisvssr";
        
        TransferManager tm = new TransferManager(new PropertiesCredentials(
				GeneratePreSignedUrl.class.getClassLoader()
				  .getResourceAsStream("aws.properties")));        

        int sevenDays = 1000 * 60 * 60 * 24 *1;
		Date oneWeekAgo = new Date(System.currentTimeMillis() - sevenDays);
        
        try {
        	tm.abortMultipartUploads(existingBucketName, oneWeekAgo);
        } catch (AmazonClientException amazonClientException) {
        	System.out.println("Unable to upload file, upload was aborted.");
        	amazonClientException.printStackTrace();
        }
    }
}