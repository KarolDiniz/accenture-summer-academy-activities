package acc.br.consumer.dtos;

public record UserRegisteredPayload (String fullName, String emailAddress, int confirmationCode) {

}

