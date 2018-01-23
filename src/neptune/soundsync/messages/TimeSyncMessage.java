package neptune.soundsync.messages;

import nedhyett.crimson.logging.CrimsonLog;
import nedhyett.crimson.networking.tier4.*;
import nedhyett.crimson.utility.GenericUtils;
import neptune.soundsync.SoundSync;

/**
 * Created by ned on 11/11/2015.
 */
public class TimeSyncMessage extends Tier4Packet {

    public int time;

    @Deprecated
    @NetworkConstructor
    public TimeSyncMessage() {

    }

    public TimeSyncMessage(int time) {
        this.time = time;
    }

    @Override
    public void processPacketServer(Tier4Client c, Tier4ServerPipeline p) {
        CrimsonLog.info("Client time difference is " + time);
        c.data.setData("delay", time);
    }

    @Override
    public void processPacketClient(Tier4ClientPipeline p) {
        int nt = GenericUtils.getSecondTime();
        p.sendPacket(new TimeSyncMessage(time - nt));
        CrimsonLog.info(time + " : " + nt + " : " + (time - nt));
        CrimsonLog.info("Time difference is " + (time - nt));
        SoundSync.diff = time - nt;
    }

    @Override
    public boolean canProcessOnSide(Side side) {
        return true;
    }

}
