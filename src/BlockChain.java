import java.util.*;
import java.util.stream.Collectors;

public class BlockChain {
  List<Block> blocks = new ArrayList<>();

  public BlockChain() {
    Block genesis = new Block(2);
    blocks.add(genesis);
  }

  @Override
  public String toString() {
    return "BlockChain{" + "blocks=" + blocks + '}';
  }

  public Block getLatestBlock() {
    return blocks.get(blocks.size() - 1);
  }

  public void addBlock(Block nextBlock) {
    nextBlock.prevHash = this.getLatestBlock().hash;
    nextBlock.hash = nextBlock.calculateHash();
    this.blocks.add(nextBlock);
  }

  /**
   * The balance for an address is total amount of transactions where address is receiver (to)
   * minus
   * the total amount where address is sender (from)
   *
   * @param address the address to calculate balance for
   * @return the current balance
   * <p>
   * persons.stream().map(p -> { return p.getName() });
   */
  public int calculateBalance(String address) {

    int sumIn = blocks.stream().flatMap(b -> b.transactionList.stream())
                      .filter(t -> address.equals(t.to)).mapToInt(t -> t.amount).sum();

    int sumOut = blocks.stream().flatMap(b -> b.transactionList.stream())
                       .filter(t -> address.equals(t.from)).mapToInt(t -> t.amount).sum();

    return sumIn - sumOut;
  }

  /**
   * List blocks by total transaction volume - i.e. ignoring to/from (sum of amount).
   * Sorted by highest volume first.
   * The string returned should be formatted as Block #x where x is the index of the block in
   * the chain
   *
   * @param limit how many entries to return
   * @return formatted list of blocks with the highest volume
   */
  public List<String> calculateTopTransactionVolume(int limit) {

    return blocks.stream().map(b -> Arrays.asList(blocks.indexOf(b),
                                                  b.transactionList.stream().mapToInt(t -> t.amount)
                                                                   .sum()))
                 .sorted(Comparator.comparingInt(l -> -l.get(1))).limit(limit)
                 .map(l -> String.format("Block #%d = %d", l.get(0), l.get(1)))
                 .collect(Collectors.toList());
  }

  public long uniqueAddresses() {
    Set<String> unique =
    blocks.stream().flatMap(b -> b.transactionList.stream())
            .map(t -> t.to)
            .collect(Collectors.toSet());

    Set<String> uniqueOut =
            blocks.stream().flatMap(b -> b.transactionList.stream())
                  .map(t -> t.from)
                  .collect(Collectors.toSet());

    unique.addAll(uniqueOut);

    return unique.size();
  }

  public String calculateMostValuableAddress() {
    return "";
  }

  /**
   * Calculate average tx. amount for blocks with difficulty >= 2
   *
   * @return The average transaction amount
   */
  public double calculateAverageTxAmount(int minDifficultyLevel) {
    return 0.0;
  }

  /**
   * 1. The {@link Block#prevHash} of each block must be the same as the {@link Block#hash}of
   * the previous block in
   * the chain - except the first (genesis) block (it has no previous block).
   * 2. The hash field of every transaction must be verified - it must be equal to the value
   * returned by the
   * {@link Transaction#calculateHash()} method.
   *
   * @return true if the blockchain is considered valid, otherwise false
   */
  public boolean verify() {
    return true;
  }
}