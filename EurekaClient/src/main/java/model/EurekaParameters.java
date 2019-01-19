package model;

public class EurekaParameters {


    private String ip;
    private String appName;
    private String UUID;
    private String port;
    private String securePortEnabled;
    private String securePort;
    private String lastDirtyTimeStamp;
    private String instanceId;


    public EurekaParameters(String ip, String appName, String UUID, String port, boolean securePortEnabled, String securePort, long lastDirtyTimeStamp) {
        this.ip = ip;
        this.appName = appName;
        this.UUID = UUID;
        this.port = port;
        if(securePortEnabled)
            this.securePortEnabled = "true";
        else
            this.securePortEnabled = "false";
        this.securePort = securePort;
        this.lastDirtyTimeStamp = Long.toString(lastDirtyTimeStamp);
        this.instanceId = this.ip+":"+this.appName+":"+this.UUID;
    }

    public String getIp() {
        return ip;
    }

    public String getAppName() {
        return appName;
    }

    public String getUUID() {
        return UUID;
    }

    public String getPort() {
        return port;
    }

    public String getSecurePortEnabled() {
        return securePortEnabled;
    }

    public String getSecurePort() {
        return securePort;
    }

    public String getLastDirtyTimeStamp() {
        return lastDirtyTimeStamp;
    }
}
