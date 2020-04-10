package devicefarm;

import com.intuit.karate.Logger;
import com.intuit.karate.driver.Target;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.devicefarm.DeviceFarmClient;
import software.amazon.awssdk.services.devicefarm.model.CreateTestGridUrlRequest;
import software.amazon.awssdk.services.devicefarm.model.CreateTestGridUrlResponse;

/**
 *
 * @author pthomas3
 */
public class DeviceFarmTarget implements Target {
    
    private String arn;
    private String driverType = "chromedriver";
    private String browserName = "chrome";

    public DeviceFarmTarget(Map<String, Object> options) {
        arn = (String) options.get("arn");
        if (arn == null) {
            throw new RuntimeException("arn is null");
        }
        // update driver type and browserName if needed
    }

    @Override
    public Map<String, Object> start(Logger logger) {
        logger.info("making request to aws for browser: {}", browserName);
        DeviceFarmClient client = DeviceFarmClient.builder().region(Region.US_WEST_2).build();
        CreateTestGridUrlRequest request = CreateTestGridUrlRequest.builder()
                .expiresInSeconds(300)
                .projectArn(arn)
                .build();
        CreateTestGridUrlResponse response = client.createTestGridUrl(request);
        String webDriverUrl = response.url();
        logger.info("aws url provisioned: {}", webDriverUrl);
        Map<String, Object> map = new HashMap();
        map.put("type", driverType);
        map.put("start", false);
        map.put("webDriverUrl", webDriverUrl);
        // this is needed because it can take a minute or two for the "desktop" to be provisioned by aws
        map.put("httpConfig", Collections.singletonMap("readTimeout", 120000));
        // refer: https://docs.aws.amazon.com/devicefarm/latest/testgrid/techref-support.html     
        Map<String, Object> session = new HashMap();
        map.put("webDriverSession", session);
        Map<String, Object> capabilities = new HashMap();
        capabilities.put("browserName", browserName);
        session.put("capabilities", capabilities);
        // for some reason, both are needed for aws device farm
        session.put("desiredCapabilities", capabilities);        
        return map;
    }

    @Override
    public Map<String, Object> stop(Logger logger) {
        return Collections.EMPTY_MAP;
    }

}
