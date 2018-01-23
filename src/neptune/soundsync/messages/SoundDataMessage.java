package neptune.soundsync.messages;

import nedhyett.crimson.logging.CrimsonLog;
import nedhyett.crimson.networking.tier4.*;
import neptune.soundsync.SoundSync;

/**
 * Created by ned on 11/11/2015.
 */
public class SoundDataMessage extends Tier4Packet {

    public byte[] data;

    @Deprecated
    @NetworkConstructor
    public SoundDataMessage() {

    }

    public SoundDataMessage(byte[] data){
        this.data = data;
    }

    @Override
    public void processPacketServer(Tier4Client c, Tier4ServerPipeline p) {

    }

    @Override
    public void processPacketClient(Tier4ClientPipeline p) {
        SoundSync.soundData = data;
        CrimsonLog.info("%s", SoundSync.soundData.length);
        CrimsonLog.info("Obtained Sound Data!");
    }

    @Override
    public boolean canProcessOnSide(Side side) {
        return side == Side.CLIENT;
    }

}
