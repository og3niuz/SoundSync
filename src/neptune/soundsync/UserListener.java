package neptune.soundsync;

import nedhyett.crimson.eventreactor.EventSubscribe;
import nedhyett.crimson.networking.tier4.event.ClientConnectedEvent;
import nedhyett.crimson.utility.GenericUtils;
import neptune.soundsync.messages.TimeSyncMessage;

/**
 * Created by ned on 11/11/2015.
 */
public class UserListener {

    @EventSubscribe
    public void handle(ClientConnectedEvent evt){
        evt.client.sendPacket(new TimeSyncMessage(GenericUtils.getSecondTime()));
    }

}
