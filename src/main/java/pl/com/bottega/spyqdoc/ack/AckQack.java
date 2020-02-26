package pl.com.bottega.spyqdoc.ack;

import java.time.LocalDate;

import org.springframework.util.StringUtils;

import lombok.Data;

@Data
public class AckQack {

	private Long id;
	private LocalDate dueDate;
	private boolean isSigned;

	void sign(boolean isRecepient, SignQackPayload qack) {
		validateDueDate();
		validateUser(isRecepient);
		this.isSigned = true;
	}

	void signOnBehalf(boolean isRecepientManager, SignQackOnBehalfPayload qack) {
		validateUser(isRecepientManager);
		validateIsSigned();
		validateDueDateForManager(qack.getReason());
		this.isSigned = true;
	}

	private void validateDueDateForManager(String reason) {
		if (LocalDate.now().isAfter(dueDate) && StringUtils.isEmpty(reason)) {
			throw new RuntimeException("Document cannot be signed ");
		}
	}

	private void validateIsSigned() {
		if (isSigned) {
			throw new RuntimeException("Document already signed");
		}
	}

	private void validateUser(boolean isRecepient) {
		if (!isRecepient) {
			throw new RuntimeException("User does not have permission");
		}
	}

	private void validateDueDate() {
		if (!LocalDate.now().isBefore(dueDate)) {
			throw new RuntimeException("Due Date is expired");
		}
	}
}
