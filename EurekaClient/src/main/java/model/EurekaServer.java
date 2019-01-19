package model;

public class EurekaServer {

    private String ip;
    private String port;
    private String initUrl;
    private String peerReplicationUrl;
    private String putUrl;
    private String deleteUrl;
    private String getApplicationsUrl;

    public EurekaServer(String ip,String port,String instanceId,String appName,String lastDirtyTimeStamp){
        this.ip = ip;
        this.port = port;
        if(!"80".equals(this.port)){
            this.initUrl = "http://"+this.ip+":"+this.port+"/eureka/apps/"+appName;
            this.peerReplicationUrl = "http://"+this.ip+":"+this.port+"/eureka/peeerreplication/batch/";
            this.putUrl = "http://"+this.ip+":"+this.port+"/eureka/apps/"+appName+"/"+instanceId+"?status=UP&lastDirtyTimestamp="+lastDirtyTimeStamp;
            this.deleteUrl = "http://"+this.ip+":"+this.port+"/eureka/apps/"+appName+"/"+instanceId;
            this.getApplicationsUrl = "http://"+this.ip+":"+this.port+"/eureka/apps/";
        }
        else{
            this.initUrl = "http://"+this.ip+"/eureka/apps/"+appName;
            this.peerReplicationUrl = "http://"+this.ip+"/eureka/peeerreplication/batch/";
            this.putUrl = "http://"+this.ip+"/eureka/apps/"+appName+"/"+instanceId+"?status=UP&lastDirtyTimestamp="+lastDirtyTimeStamp;
            this.deleteUrl = "http://"+this.ip+"/eureka/apps/"+appName+"/"+instanceId;
            this.getApplicationsUrl = "http://"+this.ip+":"+"/eureka/apps/";
        }
    }


    public String getInitUrl() {
        return initUrl;
    }

    public String getPeerReplicationUrl() {
        return peerReplicationUrl;
    }

    public String getPutUrl() {
        return putUrl;
    }

    public String getDeleteUrl() {
        return deleteUrl;
    }

    public String getGetApplicationsUrl() {
        return getApplicationsUrl;
    }
}
