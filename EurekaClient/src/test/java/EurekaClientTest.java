import exception.*;
import org.junit.Assert;

import java.io.IOException;
import java.util.List;

public class EurekaClientTest {

    @org.junit.Test
    public void start() throws IOException {
        try {
            EurekaClient.Start();
            System.out.println("Eureka Client Started");
        } catch (EurekaMissingAppNameParameterException e) {
            e.printStackTrace();
        } catch (EurekaInvalidParameterException e) {
            e.printStackTrace();
        } catch (EurekaMissingSecurePortEnabledParameterException e) {
            e.printStackTrace();
        } catch (EurekaMissingPortParameterException e) {
            e.printStackTrace();
        } catch (EurekaMissingUseLocalIpParameterException e) {
            e.printStackTrace();
        } catch (EurekaInvalidServerException e) {
            e.printStackTrace();
        }
        List<String> apps = EurekaClient.GetRegistedEurekaApps();
        Assert.assertEquals(1,apps.size());
        EurekaClient.EmergencyStop();
    }

}
