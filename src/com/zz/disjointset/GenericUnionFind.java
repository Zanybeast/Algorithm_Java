package com.zz.disjointset;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @ClassName GenericUnionFind
 * @Description TODO
 * @Author carl
 * @Date 2020/11/18 23:21
 * @Version 1.0
 **/
public class GenericUnionFind<E> {
    private Map<E, Node<E>> nodes = new HashMap<>();

    public void makeSet(E e) {
        if (nodes.containsKey(e)) return;
        nodes.put(e, new Node<>(e));
    }

    private Node<E> findNode(E e) {
        Node<E> node = nodes.get(e);
        if (node == null) return null;
        while (!Objects.equals(node.element, node.parent.element)) {
            node.parent = node.parent.parent;
            node = node.parent;
        }
        return node;
    }

    public E find(E e) {
        Node<E> node = findNode(e);
        return node == null ? null : node.element;
    }

    public boolean isSame(E e1, E e2) {
        return Objects.equals(find(e1), find(e2));
    }

    public void union(E e1, E e2) {
        Node<E> p1 = findNode(e1);
        Node<E> p2 = findNode(e2);
        if (p1 == null || p2 == null) return;
        if (Objects.equals(p1.element, p2.element)) return;

        if (p1.rank > p2.rank) {
            p2.parent = p1;
        } else if (p1.rank < p2.rank) {
            p1.parent = p2;
        } else {
            p1.parent = p2;
            p2.rank += 1;
        }
    }

    private static class Node<E> {
        E element;
        Node<E> parent = this;
        int rank = 1;

        Node(E element) {
            this.element = element;
        }
    }
}
