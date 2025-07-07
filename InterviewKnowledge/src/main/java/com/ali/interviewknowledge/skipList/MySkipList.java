package com.ali.interviewknowledge.skipList;

import java.util.Arrays;
import java.util.Random;

/**
 * @author wfhstart
 * @create 2025-02-15
 */
public class MySkipList {

    // 设置最大层数为16
    private static final int MAX_LEVEL = 16;

    // 当前层数
    private int level;

    // 头节点
    private SkipListNode header;

    private Random random;

    public MySkipList() {
        this.level = 0;
        this.header = new SkipListNode(Integer.MIN_VALUE, MAX_LEVEL);
        this.random = new Random();
    }

    /**
     * 理论来讲，一级索引中元素个数应该占原始数据的 50%，二级索引中元素个数占 25%，三级索引12.5% ，一直到最顶层。
     * 因为这里每一层的晋升概率是 50%。对于每一个新插入的节点，都需要调用 randomLevel 生成一个合理的层数。
     * 该 randomLevel 方法会随机生成 1~MAX_LEVEL 之间的数，且 ：
     * 50%的概率返回 1
     * 25%的概率返回 2
     * 12.5%的概率返回 3 ...
    */
    private int randomLevel() {
        int level = 0;
        while (random.nextDouble() < 0.5 && level < MAX_LEVEL) {
            level++;
        }
        return level;
    }

    /**
     * 插入节点
     * @param value
     */
    public void insert(int value) {
        // 创建一个update数组，用于记录每层索引中小于当前value的最大值,该节点后就是待插入位置
        SkipListNode[] update = new SkipListNode[MAX_LEVEL + 1];
        // 初始化为头节点
        SkipListNode current = this.header;

        // 从最高层开始查找插入位置，记录每一层的更新节点，用update数组记录每层索引中小于当前value的最大值
        // 外层for循环控制从上到下遍历
        for (int i = level; i >= 0; i--) {
            // 内存while循环控制从左到右遍历
            while (current.forward[i] != null && current.forward[i].value < value) {
                current = current.forward[i];
            }
            update[i] = current;
        }

        // 生成新节点的随机层数
        int lvl = randomLevel();

        // 如果新节点的层数大于当前索引的最大层数，则更新索引的最大层数
        if (lvl > level) {
            // 对于差出来的层数中，更新数组默认指向头节点
            for (int i = level + 1; i <= lvl; i++) {
                update[i] = header;
            }
            level = lvl;
        }

        SkipListNode newNode = new SkipListNode(value, lvl);

        for (int i = 0; i <= lvl; i++) {
            // 更当前节点newNode的后继节点为前驱节点的后继节点
            newNode.forward[i] = update[i].forward[i];
            // 更新前驱节点的后继节点为当前节点newNode
            update[i].forward[i] = newNode;
        }

    }

    /**
     * 查找节点
     * @param value
     * @return
     */
    public SkipListNode search(int value) {
        // 初始化为头节点
        SkipListNode current = this.header;
        // 找到小于value的最大值
        // 外层for循环控制从上到下遍历
        for (int i = level; i >= 0; i--) {
            // 内层while循环控制从左到右遍历
            while (current.forward[i] != null && current.forward[i].value < value) {
                current = current.forward[i];
            }
        }

        // 如果current节点的后继节点值等于value则直接返回
        if (current.forward[0] != null && current.forward[0].value == value) {
            return current.forward[0];
        } else {
            return null;
        }
    }

    /**
     * 删除节点
     * @param value
     * @return
     */
    public void delete(int value) {
        // 创建一个update数组，用于记录每层索引中小于当前value的最大值,该节点后就是待删除位置
        SkipListNode[] update = new SkipListNode[MAX_LEVEL + 1];
        // 初始化为头节点
        SkipListNode current = this.header;
        // 外层for循环控制从上到下遍历
        for (int i = level; i >= 0; i--) {
            // 内层while循环控制从左到右遍历
            while (current.forward[i] != null && current.forward[i].value < value) {
                current = current.forward[i];
            }
            update[i] = current;
        }
        // 查看原始层节点的后继节点是否等于value，若等于则说明存在要删除的节点
        if (current.forward[0] != null && current.forward[0].value == value) {
            // 从最高层索引层查看后继节点是否等于value，若等于则将当前节点的后继节点指向value节点的后继节点
            for (int i = level; i >= 0; i--) {
                // 如果当前节点的后继节点等于value，则将当前节点的后继节点指向value节点的后继节点
                if (update[i].forward[i] != null && update[i].forward[i].value == value) {
                    update[i].forward[i] = update[i].forward[i].forward[i];
                }
            }
            // 从最高级开始查看是否有一级索引为空，若为空则层级减1
            while (level > 0 && header.forward[level] == null) {
                level--;
            }
        }
    }

    public void printAll() {
        SkipListNode current = this.header;
        // 基于最底层的非索引层进行遍历，只要后继节点不为空，则速速出当前节点，并移动到后继节点
        while (current.forward[0] != null) {
            System.out.println(current.forward[0]);
            current = current.forward[0];
        }
    }


    public static class SkipListNode{
        // 存储真的值
        public int value;

        // forward数组，用于记录原始链表节点的后继节点和多级索引的后继节点指向。
        public SkipListNode[] forward;

        public SkipListNode(int value, int level) {
            this.value = value;
            forward = new SkipListNode[level + 1];
        }

        // 重写toString方法方便打印
        @Override
        public String toString() {
            return "SkipListNode{" +
                    "value=" + value +
                    "}";
        }
    }

    public static void main(String[] args) {
        MySkipList skipList = new MySkipList();
        for (int i = 0; i < 24; i++) {
            skipList.insert(i);
        }

        System.out.println("**********输出添加结果**********");
        skipList.printAll();

        MySkipList.SkipListNode node = skipList.search(22);
        System.out.println("**********查询结果:" + node +" **********");

//        skipList.delete(22);
//        System.out.println("**********删除结果**********");
//        skipList.printAll();

        skipList.insert(21);
        skipList.insert(12);
        skipList.printAll();
    }
}
