import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

public class Transaction {
    final String hash;
    final String from;
    final String to;
    int amount;

    public Transaction(String from, String to, int amount) {
        this.from = from;
        this.to = to;
        this.amount = amount;
        this.hash = calculateHash();
    }

    String calculateHash() {
        ByteArrayOutputStream messageStream = buildMessage();
        String hash = createHash(messageStream);
        return hash;
    }

    private ByteArrayOutputStream buildMessage() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            out.write(from.getBytes());
            out.write(to.getBytes());
            out.write(amount);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return out;
    }

    private static String createHash(ByteArrayOutputStream out) {
        try {
            byte[] digest = MessageDigest.getInstance("SHA-256").digest(out.toByteArray());
            return HexFormat.of().formatHex(digest);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "hash='" + hash + '\'' +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", amount=" + amount +
                '}';
    }
}
