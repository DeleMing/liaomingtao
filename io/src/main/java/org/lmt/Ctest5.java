package org.lmt;

import com.alibaba.fastjson.JSON;

import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author: LiaoMingtao
 * @date: 2021/9/15
 */
public class Ctest5 {

    public static void main(String[] args) {
        Stack<String> stack = new Stack<>();
        stack.push("a");
        stack.push("b");
        stack.push("c");
        stack.push("d");
        for (String s : stack) {
            System.out.println(s);
        }
        System.out.println("队列");
        Queue<Integer> q = new LinkedBlockingQueue<Integer>();
        //初始化队列
        for (int i = 0; i < 5; i++) {
            q.offer(i);
        }
        System.out.println("-------1-----");
        //集合方式遍历，元素不会被移除
        for (Integer x : q) {
            System.out.println(x);
        }
        System.out.println("-------2-----");
        //队列方式遍历，元素逐个被移除
        while (q.peek() != null) {
            System.out.println(q.poll());
        }
        // System.out.println(JSON.toJSONString(stack));
        // System.out.println(stack.peek());
        // System.out.println(stack.pop());
        // System.out.println(stack.pop());
        // System.out.println(stack.pop());
        // System.out.println(stack.pop());
        // System.out.println(JSON.toJSONString(stack));
    }
}
