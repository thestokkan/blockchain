import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HexFormat;
import java.util.List;

public class Block {
    String hash;
    String prevHash;

    Date createDate = new Date();

    List<Transaction> transactionList = new ArrayList<>();

    final int difficulty;

    public Block(int difficulty) {
        this.difficulty = difficulty;
        this.hash = calculateHash();
    }

    @Override
    public String toString() {
        return "Block{" +
                "hash='" + hash + '\'' +
                ", prevHash='" + prevHash + '\'' +
                ", transactionList=" + transactionList +
                '}';
    }

    public void addTransaction(Transaction transaction) {
        transactionList.add(transaction);
        this.hash = calculateHash(); // Recalculate hash
    }

    public String calculateHash() {
        byte[] digest;
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        for (Transaction transaction : transactionList) {
            try {
                os.write(transaction.calculateHash().getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        os.write(Long.valueOf(createDate.getTime()).byteValue());

        try {
            digest = MessageDigest.getInstance("SHA-256")
                    .digest(os.toByteArray());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return HexFormat.of().formatHex(digest);
    }
}
