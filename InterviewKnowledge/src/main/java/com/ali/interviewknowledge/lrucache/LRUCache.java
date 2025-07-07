package com.ali.interviewknowledge.lrucache;

import java.util.HashMap;
import java.util.Map;

/**
 * 核心：双向链表+哈希表实现
 * 维护：表头是最近使用的，超出容量删除即维护最近不咋使用的就被删除掉
 * @author wfhstart
 * @create 2025-04-01
 */
public class LRUCache {
    private class Node {
        int key, value;
        Node pre, next;

        public Node(int key, int value){
            this.key = key;
            this.value = value;
        }
    }

    private final int capacity;
    private final Node dummy = new Node(-1, -1);

    private final Map<Integer, Node> keyToNode = new HashMap<>();

    public LRUCache(int capacity) {
        this.capacity = capacity;
        dummy.next = dummy;
        dummy.pre = dummy;
    }

    /**
     * 获取key对应的value
     * @param key
     * @return
     */
    public int get(int key) {
        Node node = keyToNode.get(key);
        return node == null ? -1 : node.value;
    }

    /**
     * 设置key对应的value
     * @param key
     * @param value
     */
    public void put(int key, int value) {
        Node node = getNode(key);
        if (node != null) { // 有这本书
            node.value = value; // 更新 value
            return;
        }
        Node newNode = new Node(key, value); // 新书
        pushFront(newNode); // 放在最上面
        if (keyToNode.size() > capacity) { // 书太多了
            Node backNode = dummy.pre;
            keyToNode.remove(backNode.key);
            removeNode(backNode); // 去掉最后一本书
        }
    }
    // 获取 key 对应的节点，同时把该节点移到链表头部
    private Node getNode(int key) {
        if (!keyToNode.containsKey(key)) { // 没有这本书
            return null;
        }
        Node node = keyToNode.get(key); // 有这本书
        removeNode(node); // 把这本书抽出来
        pushFront(node); // 放在最上面
        return node;
    }
    // 删除一个节点（抽出一本书）
    private void removeNode(Node x) {
        x.pre.next = x.next;
        x.next.pre = x.pre;
    }
    // 在链表头添加一个节点（把一本书放在最上面）
    private void pushFront(Node x) {
        x.next =  dummy.next;
        x.pre = dummy;
        x.next.pre = x;
        x.pre.next = x;
    }

}
