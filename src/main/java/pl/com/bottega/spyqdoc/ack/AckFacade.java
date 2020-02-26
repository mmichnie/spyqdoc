package pl.com.bottega.spyqdoc.ack;

import org.springframework.stereotype.Component;

@Component
public class AckFacade {

	private AckRepository repo;
	private UserProvider userProvider;

	public void handle(SignQackPayload qack) {
		AckQack ack = repo.findById(qack.getId()).orElseThrow(() -> new IllegalStateException("sth"));
		ack.sign(userProvider.isRecepient(), qack);
		repo.save(ack);
	}

	public void handle(SignQackOnBehalfPayload qack) {
		AckQack ack = repo.findById(qack.getId()).orElseThrow(() -> new IllegalStateException("sth"));
		ack.signOnBehalf(userProvider.isRecepientManager(), qack);
		repo.save(ack);
	}

}
