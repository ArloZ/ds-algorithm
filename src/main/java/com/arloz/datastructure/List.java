package com.arloz.datastructure;

import com.arloz.utils.Console;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 线性表
 * 1. 数组
 * 2. 链表
 *
 * @author arloz 2021/4/13
 */
public class List {

    public static void main(String[] args) {
        arrays();

        link();
    }

    /**
     * 数组
     */
    public static void arrays() {
        int size = 10;
        // 创建数组
        Integer[] nums = new Integer[size];

        // 遍历数组
        for (int i = 0; i < size; i++) {
            nums[i] = i;
        }

        // 随机访问
        nums[3] = nums[3] * 10;
        Console.info(nums);
    }

    /**
     * 链表
     */
    public static void link() {
        int n = 10;

        // 创建链表
        LinkNode root = new LinkNode(null, 0);
        LinkNode pre = root;
        for (int i = 1; i < n; i++) {
            LinkNode node = new LinkNode(null, i);
            pre.next = node;
            pre = node;
        }

        // 插入：在第一个元素之后插入
        LinkNode newNode = new LinkNode(null, 999);
        newNode.next = root.next;
        root.next = newNode;

        // 删除：删除第一个元素
        root = root.next;

        // 遍历：从头到尾输出每一个元素
        LinkNode node = root;
        while (node != null) {
            Console.info(node.value);
            node = node.next;
        }
    }

    @Data
    @AllArgsConstructor
    public static class LinkNode {
        /**
         * 下一个节点
         */
        LinkNode next;

        /**
         * 节点值
         */
        Integer value;
    }

}
