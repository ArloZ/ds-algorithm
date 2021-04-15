package com.arloz.datastructure;

import java.util.Arrays;

import com.arloz.utils.Console;

/**
 * 散列表
 *
 * @author arloz 2021/4/15
 */
public class Table {
    /**
     * 散列表存储的元素节点
     */
    private static class Node {
        /**
         * 具体的元素值
         */
        private Integer value;

        /**
         * 当hash冲突时，转换为链表存储时，指向下一个节点
         */
        private Node next;

        /**
         * 元素的key，在遇到hash冲突时，需要进行key的比较，因此要求key实现的equals方法
         */
        private String key;

        Node(Integer value, String key) {
            this.value = value;
            this.key = key;
        }

        /**
         * 是否相同的key
         *
         * @param key
         * @return
         */
        public boolean equalsKey(String key) {
            return this.key.equals(key);
        }
    }

    /**
     * 存储散列表元素的数组
     */
    private Node[] values;

    /**
     * 当前散列表的大小
     */
    private int size;

    /**
     * 元素的数量
     */
    private int count;

    /**
     * 扩容阈值
     */
    private float sizeRate;

    Table(int capacity) {
        this.size = capacity;
        values = new Node[capacity];
        sizeRate = 0.8f;
    }

    /**
     * 插入元素
     *
     * @param key
     * @param value
     */
    public void put(String key, Integer value) {
        if (key == null) {
            throw new NullPointerException();
        }

        // 超过扩容阈值时，执行一次扩容操作
        if (count * 1.0 / size > sizeRate || count >= size) {
            resize();
        }

        int idx = hash(key);
        // 元素不存在
        if (values[idx] == null) {
            values[idx] = new Node(value, key);
        } else {
            Node existNode = findInLinkListByKey(values[idx], key);
            if (existNode == null) {
                // hash冲突，节点不存在，则追加到链表的末尾
                appendLinkList(values[idx], key, value);
            } else {
                // 已存在的key，更新值
                existNode.value = value;
            }
        }

        // 元素个数统计
        count++;
    }

    /**
     * 通过key查询元素
     *
     * @param key
     * @return
     */
    public Integer get(String key) {
        if (key == null) {
            return null;
        }

        int idx = hash(key);
        if (values[idx] == null) {
            return null;
        }

        Node node = findInLinkListByKey(values[idx], key);
        return node == null ? null : node.value;
    }

    /**
     * 根据指定的key删除元素
     *
     * @param key
     */
    public void remove(String key) {
        if (key == null) {
            return;
        }

        int idx = hash(key);
        if (values[idx] == null) {
            return;
        }

        Node result = removeFromLinkList(values[idx], key);
        values[idx] = result;
        count--;
    }

    /**
     * 通过key在链表中查询元素
     *
     * @param root
     * @param key
     * @return
     */
    protected Node findInLinkListByKey(Node root, String key) {
        if (root == null) {
            return null;
        }

        while (root != null) {
            if (root.equalsKey(key)) {
                return root;
            }
            root = root.next;
        }

        return null;
    }

    /**
     * 将元素追加到链表的结尾
     *
     * @param root
     * @param key
     * @param value
     */
    protected void appendLinkList(Node root, String key, Integer value) {
        while (root != null) {
            if (root.next == null) {
                root.next = new Node(value, key);
                break;
            }

            root = root.next;
        }
    }

    /**
     * 从链表中移除指定的元素
     *
     * @param root
     * @param key
     * @return
     */
    protected Node removeFromLinkList(Node root, String key) {
        if (root == null) {
            return null;
        }
        Node preHead = new Node(-1, "");
        preHead.next = root;
        Node pos = preHead;
        while (pos.next != null) {
            if (pos.next.equalsKey(key)) {
                pos.next = pos.next.next;
                continue;
            }
            pos = pos.next;
        }

        return preHead.next;
    }

    /**
     * 获取当前的大小
     *
     * @return
     */
    public int getSize() {
        return count;
    }

    /**
     * 扩容
     */
    protected void resize() {
        size *= 2;
        Node[] newValues = new Node[size];
        for (Node value : values) {
            Node node = value;
            while (node != null) {
                int idx = hash(node.key);
                if (newValues[idx] == null) {
                    newValues[idx] = node;
                } else {
                    appendLinkList(newValues[idx], node.key, node.value);
                }
                node = node.next;
            }
        }
        values = newValues;
    }

    protected int hash(String key) {
        return key.hashCode() % size;
    }

    public static void main(String[] args) {
        Table table = new Table(5);

        table.put("key1", 1);
        table.put("key2", 2);
        table.put("key3", 3);
        table.put("key4", 4);
        table.put("key5", 5);
        table.put("key6", 6);
        table.put("key7", 7);
        table.put("key8", 8);
        table.put("key9", 9);

        Console.info(Arrays.asList(table.size, table.count, table.get("key1"), table.get("key6"), table.get("k")));
    }
}
