package com.micro.cloud.common.core;

/**
 * Key Value 的键值对
 *
 * @author roy
 */
public class KeyValue<K, V> {

  private K key;
  private V value;

  public K getKey() {
    return key;
  }

  public void setKey(K key) {
    this.key = key;
  }

  public V getValue() {
    return value;
  }

  public void setValue(V value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return "KeyValue{" + "key=" + key + ", value=" + value + '}';
  }
}
