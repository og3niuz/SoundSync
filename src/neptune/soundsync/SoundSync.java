package neptune.soundsync;

import nedhyett.crimson.eventreactor.ConsoleReactor;
import nedhyett.crimson.eventreactor.EventSubscribe;
import nedhyett.crimson.logging.CrimsonLog;
import nedhyett.crimson.networking.tier4.Tier4ClientPipeline;
import nedhyett.crimson.networking.tier4.Tier4ServerPipeline;
import nedhyett.crimson.toolbox.FileToolbox;
import nedhyett.crimson.utility.GenericUtils;
import neptune.soundsync.messages.SoundDataMessage;
import neptune.soundsync.messages.StartMessage;

/**
 * Created by ned on 11/11/2015.
 */
public class SoundSync {

    public static byte[] soundData;
    public static int diff = 0;

    public static Tier4ServerPipeline serverPipeline;
    public static Tier4ClientPipeline clientPipeline;
    public static ConsoleReactor consoleReactor;

    public static void main(String[] args) throws Exception {
        CrimsonLog.initialise("SoundSync");
        if(args.length >= 1) {
            CrimsonLog.info("Searching for host...");
            clientPipeline = new Tier4ClientPipeline(args[0], 6969);
            CrimsonLog.info("Connecting...");
            if(clientPipeline.connect()){
                CrimsonLog.info("Success...");
            } else {
                CrimsonLog.fatal("Failure.");
            }

        } else {
            CrimsonLog.info("Starting Server...");
            consoleReactor = new ConsoleReactor("ConsoleReactor");
            consoleReactor.register(new SoundSync());
            serverPipeline = new Tier4ServerPipeline(6969);
            serverPipeline.connect();
            serverPipeline.registerConnectionListener(new UserListener());

        }
        while(true) {
            GenericUtils.wait(1);
        }
    }

    @EventSubscribe
    public void command(ConsoleReactor.ConsoleLineEvent evt){
        if(evt.line.equals("start")) {
            serverPipeline.broadcast(new StartMessage(GenericUtils.getSecondTime() + 10));
        } else {
            serverPipeline.broadcast(new SoundDataMessage(new FileToolbox(evt.line).load().getData()));
        }
    }

}
