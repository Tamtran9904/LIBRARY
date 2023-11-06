package sparkminds.library.schedule;

import java.time.Instant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import sparkminds.library.repository.VerificationTokenRepository;

@Component
@Slf4j
@RequiredArgsConstructor
public class ScheduledTasks {

    private final VerificationTokenRepository verificationTokenRepository;

    @Scheduled(fixedRate = 3600000, initialDelay = 10000)
    public void scheduleTaskWithInitialDelay() {
        verificationTokenRepository.findAll()
            .stream()
            .filter(
                verificationToken -> verificationToken.getDateOfExpiry().compareTo(Instant.now())
                    < 0)
            .forEach(verificationTokenRepository::delete);

        log.info("Schedule delete verification token disable every 1 hour");
    }

}
