package util;

import java.time.Instant;

public class EurekaStringUtils {


    public static String getEurekaInitCommsString(String ip,String appName,String UUID,String port,String lastDirtyTimeStamp){
        return getEurekaInitCommsString(ip,appName,UUID,port,"false","443",lastDirtyTimeStamp);
    }

    public static String getEurekaInitCommsString(String ip,String appName,String UUID,String port,String securePortEnabled,String securePort,String lastDirtyTimeStamp){
        String initCommsStr = ResourceFileUtils.readResourceFile("EurekaInitComms.json");
        return replace("%dirtyStamp",
                replace("\"%securePort\"",
                        replace("%securePortEnabled",
                                replace("\"%port\"",
                                    replace("%port",
                                         replace("%UUID",
                                            replace("%appName",
                                                replace("%ip",
                                                        initCommsStr,ip),appName),UUID)
                                            ,port),port),securePortEnabled),securePort),lastDirtyTimeStamp);
    }


    public static String getEurekaRegisterPeerReplicationList(String ip,String appName,String UUID,String port,String lastDirtyTimeStamp){
        return getEurekaRegisterPeerReplicationList(ip,appName,UUID,port,"false","443",lastDirtyTimeStamp);
    }

    public static String getEurekaRegisterPeerReplicationList(String ip,String appName,String UUID,String port,String securePortEnabled,String securePort,String lastDirtyTimeStamp){
        String initPeerStr = ResourceFileUtils.readResourceFile("EurekaRegisterPeerReplicationList.json");
        return replace("%dirtyStamp",
                replace("\"%securePort\"",
                        replace("%securePortEnabled",
                                replace("\"%port\"",
                                        replace("%port",
                                                replace("%UUID",
                                                        replace("%appName",
                                                                replace("%ip",
                                                                        initPeerStr,ip),appName),UUID)
                                                ,port),port),securePortEnabled),securePort),lastDirtyTimeStamp);
    }



    public static String getEurekaPeerReplication(String ip,String appName,String UUID,String lastDirtyTimeStamp){
        String peerReplactionStr = ResourceFileUtils.readResourceFile("EurekaPeerReplication.json");
        return replace("\"dirtyStamp\"",
                replace("%UUID",
                        replace("%appName",
                                replace("%ip",peerReplactionStr,ip),appName),UUID),lastDirtyTimeStamp);
    }



    private static String replace(String toBeReplaced,String eurekaJson,String replace){
        String newEurekaJson = eurekaJson.replace(toBeReplaced,replace);
        return newEurekaJson;
    }





}
