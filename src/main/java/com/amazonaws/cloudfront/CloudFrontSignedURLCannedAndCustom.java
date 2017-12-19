package com.amazonaws.cloudfront;

import java.io.File;
import java.util.Date;

import com.amazonaws.services.cloudfront.CloudFrontUrlSigner;
import com.amazonaws.services.cloudfront.util.SignerUtils.Protocol;
import com.amazonaws.util.DateUtils;

public class CloudFrontSignedURLCannedAndCustom {
  //Ref URL ::  http://docs.aws.amazon.com/AWSJavaSDK/latest/javadoc/com/amazonaws/services/cloudfront/CloudFrontUrlSigner.html
	public static void main(String[] args) {
		try {
			 Protocol protocol = Protocol.http;
			 String distributionDomain = "d1b2c3a4g5h6.cloudfront.net";
			 File privateKeyFile = new File("/path/to/cfcurlCloud/rsa-private-key.pem");
			 String s3ObjectKey = "a/b/images.jpeg";
			 String keyPairId = "APKAJCEOKRHC3XIVU5NA";
			 Date dateLessThan = DateUtils.parseISO8601Date("2012-11-14T22:20:00.000Z");
			 Date dateGreaterThan = DateUtils.parseISO8601Date("2011-11-14T22:20:00.000Z");
			 String ipRange = "192.168.0.1/24";

			 String url1 = CloudFrontUrlSigner.getSignedURLWithCannedPolicy(
			              protocol, distributionDomain, privateKeyFile,
			              s3ObjectKey, keyPairId, dateLessThan);

			 String url2 = CloudFrontUrlSigner.getSignedURLWithCustomPolicy(
			              protocol, distributionDomain, privateKeyFile,
			              s3ObjectKey, keyPairId, dateLessThan,
			              dateGreaterThan, ipRange);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}
