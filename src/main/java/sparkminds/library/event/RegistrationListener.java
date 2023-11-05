package sparkminds.library.event;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {

    }
}
