package me.yukun.storageblocker.config;

public enum FieldTypeEnum {
  STRING("String value"),
  STRINGLIST("String list"),
  SECTION("Section");

  private final String name;

  FieldTypeEnum(String name) {
    this.name = name;
  }

  public String toString() {
    return this.name;
  }
}
