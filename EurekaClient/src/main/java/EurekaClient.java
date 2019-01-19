import exception.*;
import model.EurekaParameters;
import model.EurekaServer;
import util.HttpUtils;
import util.IpUtils;
import util.ResourceFileUtils;

import java.io.IOException;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class EurekaClient {

    private static EurekaClient eurekaClient;

    private ExecutorService executorService;
    private List<EurekaClientRunner> eurekaClientRunners;

    private EurekaClient(){
        this.eurekaClientRunners = new ArrayList<>();
    }


    public static void Start() throws EurekaMissingAppNameParameterException, EurekaInvalidParameterException, EurekaMissingSecurePortEnabledParameterException, EurekaMissingPortParameterException, EurekaMissingUseLocalIpParameterException, EurekaInvalidServerException {
        String parameterFile = ResourceFileUtils.readExternalResourceFile("EurekaParameters");
        if(parameterFile == null || parameterFile.isEmpty()){
            throw new EurekaMissingAppNameParameterException();
        }
        String[] lines = parameterFile.split(System.lineSeparator());

        Map<String,String> parameterMap = new HashMap<>();
        parameterMap.put("appName",null);
        parameterMap.put("port",null);
        parameterMap.put("securePortEnabled",null);
        parameterMap.put("securePort",null);
        parameterMap.put("useLocalIp",null);
        for(String line:lines){
            parseLine(line,parameterMap);
        }
        validateParameterMap(parameterMap);
        String useLocalIp = parameterMap.get("useLocalIp");
        String ip = null;
        if("true".equals(useLocalIp))
            ip = IpUtils.getLocalIp();
        else
            ip = IpUtils.getExternalIp();

        String uuid = UUID.randomUUID().toString();
        long lastDirtyTimeStamp = Instant.now().getEpochSecond();
        boolean isSecurePortEnabled;
        if("true".equals(parameterMap.get("securePortEnabled")))
            isSecurePortEnabled = true;
        else
            isSecurePortEnabled = false;

        EurekaParameters parameters = new EurekaParameters(ip,parameterMap.get("appName"),uuid,parameterMap.get("port"),isSecurePortEnabled,parameterMap.get("securePort"),lastDirtyTimeStamp);
        String eurekaServersFile = ResourceFileUtils.readExternalResourceFile("EurekaServers");
        String[] eurekaServers = eurekaServersFile.split(",");


        for(String server : eurekaServers){
            String[] ipAndPort = server.split(":");
            if(ipAndPort.length==1 || ipAndPort.length==0)
                throw new EurekaInvalidServerException(server);
            Instance().eurekaClientRunners.add(new EurekaClientRunner(parameters,
                    new EurekaServer(
                            ipAndPort[0], ipAndPort[1].trim(),
                            ip + ":" + parameterMap.get("appName") + ":" + uuid,
                            parameterMap.get("appName"), Long.toString(lastDirtyTimeStamp))));
        }

        Instance().executorService = Executors.newFixedThreadPool(Instance().eurekaClientRunners.size());
        for(EurekaClientRunner runner: Instance().eurekaClientRunners){
            Instance().executorService.submit(runner);
        }
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            EmergencyStop();
        }));
    }



    public static void Stop(){
        for (EurekaClientRunner runner : Instance().eurekaClientRunners) {
            runner.setRun(false);
        }
    }

    public static void EmergencyStop(){
        Stop();
        Instance().executorService.shutdown();
        try {
            if (!Instance().executorService.awaitTermination(5, TimeUnit.SECONDS)) {
                Instance().executorService.shutdownNow();
                if (!Instance().executorService.awaitTermination(5, TimeUnit.SECONDS))
                    System.err.println("Pool did not terminate");
            }
        } catch (InterruptedException ie) {
            Instance().executorService.shutdownNow();
        }
    }

    /**
     * Gets a json String from each Eureka server containing the registered applications in each Eureka server instance.
     * @return
     */
    public static List<String> GetRegistedEurekaApps() throws IOException {
        List<String> registeredAppsJson = new ArrayList<>();
        for(EurekaClientRunner runner : Instance().eurekaClientRunners){
            registeredAppsJson.add(HttpUtils.get(runner.getEurekaServer().getGetApplicationsUrl()));
        }
        return registeredAppsJson;
    }



    private static void validateParameterMap(Map<String,String> parameterMap) throws EurekaMissingAppNameParameterException, EurekaMissingPortParameterException, EurekaMissingSecurePortEnabledParameterException, EurekaMissingUseLocalIpParameterException {
        for(Map.Entry<String,String> set : parameterMap.entrySet()){
            //a general exception would suffice, but....
            if(set.getValue()==null){
                throwMissingParameterException(set.getKey());
            }
        }
    }

    private static void throwMissingParameterException(String paramater) throws EurekaMissingAppNameParameterException, EurekaMissingPortParameterException, EurekaMissingSecurePortEnabledParameterException, EurekaMissingUseLocalIpParameterException {
        switch (paramater){
            case "appName":
                throw new EurekaMissingAppNameParameterException();
            case "port":
                throw new EurekaMissingPortParameterException();
            case "securePortEnabled":
                throw new EurekaMissingSecurePortEnabledParameterException();
            case "securePort":
                throw  new EurekaMissingPortParameterException();
            case "useLocalIp":
                throw new EurekaMissingUseLocalIpParameterException();
        }
    }

    private static void parseLine(String line,Map<String,String> parameterMap) throws EurekaInvalidParameterException {
        String[] param = line.split(",");
        if(param.length!=2 &&  validateString(param[1])){
            throw new EurekaInvalidParameterException(param[0]);
        }

        parameterMap.replace(param[0],param[1]);
    }

    private static boolean validateString(String str){
        return str!=null && str.isEmpty();
    }

    private static EurekaClient Instance(){
        if(eurekaClient==null){
           eurekaClient = new EurekaClient();
        }
        return eurekaClient;
    }

}
