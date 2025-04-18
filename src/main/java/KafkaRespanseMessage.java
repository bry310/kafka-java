import java.util.Arrays;

public record KafkaRespanseMessage(byte[] message_size, byte[] correlation_id ){
}
