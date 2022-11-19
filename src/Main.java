import java.util.List;
import java.util.Map;

public class Main {

    private static BlockChain blockChain;

    public static void main(String[] args) {

        createBlockChain();

        System.out.println(blockChain);

        int balance = blockChain.calculateBalance("b");
        System.out.println("Balance of address \"b\" is: " + balance); // Balance of address "b" is: -40

        List<String> blocksByTransactionVolume = blockChain.calculateTopTransactionVolume(3);
        System.out.println(blocksByTransactionVolume); // [Block #2 = 200, Block #1 = 130, Block #3 = 30]

        long uniqueAddresses = blockChain.uniqueAddresses();
        System.out.println("Unique addresses: " + uniqueAddresses); // Unique addresses: 5

        String richestAddress = blockChain.calculateMostValuableAddress();
        System.out.println("The richest address is: " + richestAddress); // The richest address is: a

        double averageTransactionAmount = blockChain.calculateAverageTxAmount(2);
        System.out.println("The average transaction amount is: " +  averageTransactionAmount); // The average transaction amount is: 32.0

        boolean blockChainValid = blockChain.verify();
        System.out.println("Block chain valid: " + (blockChainValid ? "Yes" : "No")); // Blockchain valid: Yes

        blockChain.blocks.get(1).transactionList.get(0).amount = 1000; // Tamper with the blockchain

        blockChainValid = blockChain.verify();
        System.out.println("Block chain valid: " + (blockChainValid ? "Yes" : "No")); // Blockchain valid: No
    }

    private static void createBlockChain() {
        blockChain = new BlockChain();

        Block b1 = new Block(2);
        b1.addTransaction(new Transaction("a", "b", 100));
        b1.addTransaction(new Transaction("c", "d", 10));
        b1.addTransaction(new Transaction("c", "a", 20));
        blockChain.addBlock(b1);

        Block b2 = new Block(1);
        b2.addTransaction(new Transaction("b", "a", 150));
        b2.addTransaction(new Transaction("a", "c", 40));
        b2.addTransaction(new Transaction("a", "c", 5));
        b2.addTransaction(new Transaction("a", "c", 5));
        b2.addTransaction(new Transaction("c", "e", 10));
        blockChain.addBlock(b2);

        Block b3 = new Block(2);
        b3.addTransaction(new Transaction("d", "c", 20));
        b3.addTransaction(new Transaction("c", "b", 10));
        blockChain.addBlock(b3);
    }
}
