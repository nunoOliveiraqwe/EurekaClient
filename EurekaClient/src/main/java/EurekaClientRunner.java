import model.EurekaParameters;
import model.EurekaServer;
import util.EurekaStringUtils;
import util.HttpUtils;

import java.io.IOException;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicBoolean;


public class EurekaClientRunner implements Runnable {


    private AtomicBoolean run;
    private EurekaParameters eurekaParameters;
    private String eurekaInitComms;
    private String eurekaRegisterPeerReplication;
    private String eurekaPeerReplication;
    private EurekaServer eurekaServer;

    public EurekaClientRunner(EurekaParameters eurekaParameters, EurekaServer eurekaServer){
        this.eurekaParameters = eurekaParameters;
        this.eurekaServer = eurekaServer;
        this.run = new AtomicBoolean(true);

        this.eurekaInitComms = EurekaStringUtils.getEurekaInitCommsString(eurekaParameters.getIp(),
                eurekaParameters.getAppName(),
                eurekaParameters.getUUID(),
                eurekaParameters.getPort(),
                eurekaParameters.getSecurePortEnabled(),
                eurekaParameters.getSecurePort(),
                eurekaParameters.getLastDirtyTimeStamp());

        this.eurekaRegisterPeerReplication = EurekaStringUtils.getEurekaRegisterPeerReplicationList(eurekaParameters.getIp(),
                eurekaParameters.getAppName(),
                eurekaParameters.getUUID(),
                eurekaParameters.getPort(),
                eurekaParameters.getSecurePortEnabled(),
                eurekaParameters.getSecurePort(),
                eurekaParameters.getLastDirtyTimeStamp());

        this.eurekaPeerReplication = EurekaStringUtils.getEurekaPeerReplication(eurekaParameters.getIp(),
                eurekaParameters.getAppName(),
                eurekaParameters.getUUID(),
                eurekaParameters.getLastDirtyTimeStamp());
    }


    public void setRun(boolean run) {
        this.run.set(run);
    }

    public EurekaServer getEurekaServer() {
        return eurekaServer;
    }

    @Override
    public void run() {
        try {
            HttpUtils.postJson(this.eurekaInitComms.replace("%timeStamp",Long.toString(Instant.now().getEpochSecond())),
            this.eurekaServer.getInitUrl());
            HttpUtils.postJson(this.eurekaRegisterPeerReplication.replace("%timeStamp",Long.toString(Instant.now().getEpochSecond())),
                    this.eurekaServer.getPeerReplicationUrl());
        } catch (IOException e) {
            //TODO Log and throw exception
            this.run.set(false);
        }
        while (this.run.get()){
            try {
                Thread.sleep(30*1000);
                HttpUtils.put(this.eurekaServer.getPutUrl());
                HttpUtils.postJson(this.eurekaPeerReplication,this.eurekaServer.getPeerReplicationUrl());
            } catch (InterruptedException e) {
                this.run.set(false);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
        try {
            HttpUtils.delete(this.eurekaServer.getDeleteUrl());
        } catch (IOException e) {
            //TODO introduce Logger
            System.err.println(e.getMessage());
        }
    }




}
