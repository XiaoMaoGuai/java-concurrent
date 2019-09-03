package com.tianhe.java.concurrent;

import java.io.Serializable;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Wrap a highly efficient thread-safe set with concurrentHashMap
 * @author: he.tian 公众号天河聊架构
 * @time: 2018-11-27 14:56
 */
public class ConcurrentHashSet<E> extends AbstractSet<E> implements Set<E>,Serializable{

    private static final long serialVersionUID = 4929799018360047384L;

    private static final Object obj = new Object();

    private final ConcurrentMap<E,Object> map;

    public ConcurrentHashSet(){
        map = new ConcurrentHashMap<E,Object>();
    }

    public ConcurrentHashSet(int initialCapacity){
        map = new ConcurrentHashMap<E,Object>(initialCapacity);
    }

    @Override
    public Iterator<E> iterator() {
        return map.keySet().iterator();
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return map.containsKey(o);
    }

    @Override
    public boolean add(E e) {
        return map.put(e,obj) == null;
    }

    @Override
    public boolean remove(Object o) {
        return map.remove(o) == obj;
    }

    @Override
    public void clear() {
        map.clear();
    }
}
