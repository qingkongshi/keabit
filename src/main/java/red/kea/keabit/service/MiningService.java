package red.kea.keabit.service;

import red.kea.keabit.bean.Block;
import red.kea.keabit.bean.Transaction;
import red.kea.keabit.utils.Sha256;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author： KeA
 * @date： 2021-05-25 10:27:20
 * @version: 1.0
 * @describe:
 */
public class MiningService {

    /**
     * 挖矿
     *
     * @param blockchain 整个区块链
     * @param txs        需记账交易记录
     * @param address    矿工钱包地址
     * @return
     */
    private static void mineBlock(List<Block> blockchain, List<Transaction> txs, String address) {
        //加入系统奖励的交易，默认挖矿奖励10个比特币
        Transaction sysTx = new Transaction(UUID.randomUUID().toString(), "", address, 10);
        txs.add(sysTx);
        //获取当前区块链里的最后一个区块
        Block latestBlock = blockchain.get(blockchain.size() - 1);
        //随机数
        int nonce = 1;
        String hash = "";
        while (true) {
            System.out.println(latestBlock.getHash() + txs.toString() + nonce);
            hash = Sha256.getSHA256(latestBlock.getHash() + txs.toString() + nonce);
            if (hash.startsWith("0000")) {
                System.out.println("=====计算结果正确，计算次数为：" + nonce + ",hash:" + hash);
                break;
            }
            nonce++;
            System.out.println("计算错误，hash:" + hash);
        }

        //解出难题，可以构造新区块并加入进区块链里
        Block newBlock = new Block(latestBlock.getIndex() + 1, hash, System.currentTimeMillis(), txs, nonce, latestBlock.getHash());
        blockchain.add(newBlock);
        System.out.println("挖矿后的区块链：" + blockchain.toString());
    }


    /**
     * 查询余额
     *
     * @param blockchain
     * @param address
     * @return
     */
    public static int getWalletBalance(List<Block> blockchain, String address) {
        int balance = 0;
        for (Block block : blockchain) {
            List<Transaction> transactions = block.getTransactions();
            for (Transaction transaction : transactions) {
                if (address.equals(transaction.getRecipient())) {
                    balance += transaction.getAmount();
                }
                if (address.equals(transaction.getSender())) {
                    balance -= transaction.getAmount();
                }
            }
        }
        return balance;
    }


    public static void main(String[] args) {
        //创建一个空的区块链
        List<Block> blockchain = new ArrayList<>();
        //生成创世区块
        Block block = new Block(1,"1", System.currentTimeMillis(), new ArrayList<Transaction>(), 1, "1");
        //加入创世区块到区块链里
        blockchain.add(block);
        System.out.println(blockchain.toString());

        // 发送方钱包地址
        String sender = "sender_wallet";
        //接收方钱包地址
        String recipient = "recipient_wallet";

        //创建一个空的交易集合
        List<Transaction> txs = new ArrayList<>();
        //挖矿
        mineBlock(blockchain, txs, sender);
        System.out.println(sender + "钱包的余额为：" + getWalletBalance(blockchain, sender));

        //创建一个空的交易集合
        List<Transaction> txs1 = new ArrayList<>();
        //已发生但未记账的交易记录，发送者给接收者转账3个比特币
        Transaction tx1 = new Transaction(UUID.randomUUID().toString(), sender, recipient, 3);
        //已发生但未记账的交易记录，发送者给接收者转账1个比特币
        Transaction tx2 = new Transaction(UUID.randomUUID().toString(), sender, recipient, 1);
        txs1.add(tx1);
        txs1.add(tx2);
        //挖矿
        mineBlock(blockchain, txs1, sender);
        System.out.println(sender + "钱包的余额为：" + getWalletBalance(blockchain, sender));
        System.out.println(recipient + "钱包的余额为：" + getWalletBalance(blockchain, recipient));
    }
}
