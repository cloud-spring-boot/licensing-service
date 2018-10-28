package com.max.licensing.events;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface InboundOrgChangesCustomChannel {

    @Input("inboundOrgChanges")
    SubscribableChannel orgChannel();
}
