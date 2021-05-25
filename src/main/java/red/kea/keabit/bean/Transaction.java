package red.kea.keabit.bean;

import lombok.Data;

/**
 * @author： KeA
 * @date： 2021-05-25 10:06:08
 * @version: 1.0
 * @describe: 交易
 */
@Data
public class Transaction {

    /**
     * 交易唯一标识
     */
    private String id;
    /**
     * 交易发送方钱包地址
     */
    private String sender;
    /**
     * 交易接收方钱包地址
     */
    private String recipient;
    /**
     * 交易金额
     */
    private int amount;

    public Transaction() {
    }

    public Transaction(String id, String sender, String recipient, int amount) {
        this.id = id;
        this.sender = sender;
        this.recipient = recipient;
        this.amount = amount;
    }
}
