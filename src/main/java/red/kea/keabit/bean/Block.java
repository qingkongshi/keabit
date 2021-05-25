package red.kea.keabit.bean;

import lombok.Data;

import java.util.List;

/**
 * @author： KeA
 * @date： 2021-05-25 10:02:41
 * @version: 1.0
 * @describe: 区块结构
 */
@Data
public class Block {

    /**
     * 区块索引
     */
    private int index;

    /**
     * 当前区块的hash值,区块唯一标识
     */
    private String hash;

    /**
     * 生成区块的时间戳
     */

    private long timestamp;

    /**
     * 当前区块的交易集合
     */
    private List<Transaction> transactions;

    /**
     * 工作量证明，计算正确hash值的次数
     */
    private int nonce;

    /**
     * 前一个区块的hash值
     */
    private String previousHash;

    public Block() {

    }

    public Block(int index, String hash, long timestamp, List<Transaction> transactions, int nonce, String previousHash) {
        this.index = index;
        this.hash = hash;
        this.timestamp = timestamp;
        this.transactions = transactions;
        this.nonce = nonce;
        this.previousHash = previousHash;
    }
}
