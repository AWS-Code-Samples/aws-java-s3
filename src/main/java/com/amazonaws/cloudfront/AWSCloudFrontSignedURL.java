package com.amazonaws.cloudfront;

import java.io.File;
import java.io.IOException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;

import com.amazonaws.services.cloudfront.CloudFrontUrlSigner;
import com.amazonaws.services.cloudfront.util.SignerUtils.Protocol;

public class AWSCloudFrontSignedURL {
// reference url ::: https://aws.amazon.com/blogs/developer/accessing-private-content-in-amazon-cloudfront/
	public static void main(String[] args) {
		// the DNS name of your CloudFront distribution, or a registered alias
		String distributionDomainName = null;   
		// the private key you created in the AWS Management Console
		File cloudFrontPrivateKeyFile = null;
		// the unique ID assigned to your CloudFront key pair in the console    
		String cloudFrontKeyPairId = null;   
		Date expirationDate = new Date(System.currentTimeMillis() + 60 * 1000);

		try {
			String signedUrl = CloudFrontUrlSigner.getSignedURLWithCannedPolicy(
			           Protocol.https, 
			           distributionDomainName, 
			           cloudFrontPrivateKeyFile,   
			           "", // the resource path to our content
			           cloudFrontKeyPairId, 
			           expirationDate);
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
