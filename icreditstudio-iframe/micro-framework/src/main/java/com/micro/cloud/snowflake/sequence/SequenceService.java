//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.micro.cloud.snowflake.sequence;

public interface SequenceService {

  String nextStringValue(String category);

  Long nextValue(String category);

  Long nextValue(String category, Long maxValue);
}
