package com.mq.binary_process.factory;

/**
 * @Author: wenliujie
 * @Description:
 * @Date: Created in 11:02 PM 2018/12/21
 * @Modified By:
 */
public enum SerialTypeEnum {

  DEFAULT(0, "JSON");


  private int code;

  private String value;

  SerialTypeEnum(int code, String value) {
    this.code = code;
    this.value = value;
  }

  public static SerialTypeEnum get(int code) {
    for (SerialTypeEnum typeEnum : SerialTypeEnum.values()) {
      if (code == typeEnum.code) {
        return typeEnum;
      }
    }
    return DEFAULT;
  }

  public int getCode() {
    return code;
  }

  public String getValue() {
    return value;
  }
}
