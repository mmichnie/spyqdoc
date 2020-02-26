package pl.com.bottega.spyqdoc.ack;

import lombok.Data;

@Data
public class SignQackOnBehalfPayload {

	Long id;
	private String reason;

}
