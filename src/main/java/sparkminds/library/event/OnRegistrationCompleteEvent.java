package sparkminds.library.event;

import java.time.Clock;
import org.springframework.context.ApplicationEvent;
import sparkminds.library.entities.VerificationToken;

public class OnRegistrationCompleteEvent extends ApplicationEvent {

    private VerificationToken verificationToken;

    public OnRegistrationCompleteEvent(Object source, Clock clock) {
        super(source, clock);
    }
}
