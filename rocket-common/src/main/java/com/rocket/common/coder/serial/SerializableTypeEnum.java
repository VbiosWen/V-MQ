package com.rocket.common.coder.serial;

/**
 * @Author: wenliujie
 * @Description:
 * @Date: Created in 11:08 PM 2018/12/20
 * @Modified By:
 */
public enum SerializableTypeEnum {
  ERROR(0,"unknown type","UNKNOWN"),
  JSON(1,"json type","JSON"),
  V_SERIAL(2,"v serial","V");


  private int code;

  private String desc;

  private String value;

  public static SerializableTypeEnum get(int code){
    for(SerializableTypeEnum typeEnum:SerializableTypeEnum.values()){
      if(typeEnum.code==code){
        return typeEnum;
      }
    }
    return ERROR;
  }
  SerializableTypeEnum(int code, String desc, String value) {
    this.code = code;
    this.desc = desc;
    this.value = value;
  }

  public int getCode() {
    return code;
  }

  public String getDesc() {
    return desc;
  }

  public String getValue() {
    return value;
  }
}
