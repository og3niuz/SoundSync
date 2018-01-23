package neptune.soundsync.messages;

import javazoom.jl.player.Player;
import nedhyett.crimson.logging.CrimsonLog;
import nedhyett.crimson.networking.tier4.*;
import nedhyett.crimson.utility.GenericUtils;
import neptune.soundsync.SoundSync;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;

/**
 * Created by ned on 11/11/2015.
 */
public class StartMessage extends Tier4Packet {

    public int startTime;

    @Deprecated
    @NetworkConstructor
    public StartMessage() {

    }

    public StartMessage(int startTime) {
        this.startTime = startTime;
    }

    @Override
    public void processPacketServer(Tier4Client c, Tier4ServerPipeline p) {

    }

    @Override
    public void processPacketClient(Tier4ClientPipeline p) {
        System.out.println("STARTING...");
        Thread t = new Thread() {
            @Override
            public void run() {
                boolean sweg = true;
                while(sweg){
                    try {
                        if (GenericUtils.getSecondTime() >= startTime + SoundSync.diff) {
                            CrimsonLog.info("STARTED");
                            sweg = false;
                            BufferedInputStream bis = new BufferedInputStream(new ByteArrayInputStream(SoundSync.soundData));
                            Player player = new Player(bis);
                            new Thread() {
                                public void run() {
                                    try { player.play(); }
                                    catch (Exception e) { System.out.println(e); }
                                }
                            }.start();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        t.start();
    }

    @Override
    public boolean canProcessOnSide(Side side) {
        return side == Side.CLIENT;
    }
}
