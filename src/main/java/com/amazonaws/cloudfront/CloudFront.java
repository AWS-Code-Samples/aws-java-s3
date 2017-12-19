package com.amazonaws.cloudfront;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.cloudfront.AmazonCloudFrontClient;
import com.amazonaws.services.cloudfront.model.Aliases;
import com.amazonaws.services.cloudfront.model.CacheBehaviors;
import com.amazonaws.services.cloudfront.model.CookiePreference;
import com.amazonaws.services.cloudfront.model.CreateDistributionRequest;
import com.amazonaws.services.cloudfront.model.CreateDistributionResult;
import com.amazonaws.services.cloudfront.model.DefaultCacheBehavior;
import com.amazonaws.services.cloudfront.model.DistributionConfig;
import com.amazonaws.services.cloudfront.model.ForwardedValues;
import com.amazonaws.services.cloudfront.model.GetDistributionRequest;
import com.amazonaws.services.cloudfront.model.GetDistributionResult;
import com.amazonaws.services.cloudfront.model.LoggingConfig;
import com.amazonaws.services.cloudfront.model.Origin;
import com.amazonaws.services.cloudfront.model.Origins;
import com.amazonaws.services.cloudfront.model.PriceClass;
import com.amazonaws.services.cloudfront.model.S3OriginConfig;
import com.amazonaws.services.cloudfront.model.TrustedSigners;
import com.amazonaws.services.cloudfront.model.ViewerProtocolPolicy;

public class CloudFront {
	private String endPoint ;
	private Region region ;
	private AWSCredentials credentials;
	// Cloud Front	
    private String cloudFrontDesc = "this is description";
    private String cloudFrontS3Origin= "aws-tutorials";
    private long cloudFrontMinTTL=36000;
	private String accessKey = "AKIAI2EBDM4A7M7G6RXQ";
	private String secretKey = "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" ;
	public void createCloudFront(){
		try {
			credentials  = new BasicAWSCredentials(accessKey, secretKey);
			// end point for singapore 
			endPoint = "https://rds.ap-southeast-1.amazonaws.com";
			// regions for singapore
			region = Region.getRegion(Regions.AP_SOUTHEAST_1);
			
		    
				System.out.println("Create CloudFront Distribution For Download");
				AmazonCloudFrontClient cloudfront = new AmazonCloudFrontClient(credentials);
				cloudfront.setEndpoint(endPoint);
				cloudfront.setRegion(region);

				DistributionConfig dc = new DistributionConfig();
				dc.withCallerReference(System.currentTimeMillis() + "");
				dc.withAliases(new Aliases().withQuantity(0));
				dc.withDefaultRootObject("");
				dc.withOrigins(new Origins().withItems(
						new Origin().withId(cloudFrontS3Origin).withDomainName(cloudFrontS3Origin+ ".s3.amazonaws.com").withS3OriginConfig(new S3OriginConfig().withOriginAccessIdentity("")))
						.withQuantity(1));
				dc.withDefaultCacheBehavior(new DefaultCacheBehavior()
						.withTargetOriginId(cloudFrontS3Origin)
						.withForwardedValues(new ForwardedValues().withQueryString(false).withCookies(new CookiePreference().withForward("none")))
						.withTrustedSigners(new TrustedSigners().withQuantity(0).withEnabled(false))
						.withViewerProtocolPolicy(ViewerProtocolPolicy.AllowAll)
						.withMinTTL(cloudFrontMinTTL));
				dc.withCacheBehaviors(new CacheBehaviors().withQuantity(0));
				dc.withComment(cloudFrontDesc);
				dc.withLogging(new LoggingConfig().withEnabled(false).withBucket("").withPrefix("").withIncludeCookies(false));
				dc.withEnabled(true);
				dc.withPriceClass(PriceClass.PriceClass_All);

				CreateDistributionRequest cdr = new CreateDistributionRequest().withDistributionConfig(dc);

				CreateDistributionResult distribution = cloudfront.createDistribution(cdr);

				boolean isWait = true;
				while (isWait) {
					Thread.sleep(5000);
					GetDistributionResult gdr = cloudfront.getDistribution(new GetDistributionRequest(distribution.getDistribution().getId()));
					String status = gdr.getDistribution().getStatus();
					System.out.println("Status :" + status);
					if (status.equals("Deployed")) {
						isWait = false;
						System.out.println("Domain Name :" + gdr.getDistribution().getDomainName());
					}
				}

			

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
}
