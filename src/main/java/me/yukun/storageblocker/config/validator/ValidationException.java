package me.yukun.storageblocker.config.validator;

import me.yukun.storageblocker.config.ConfigTypeEnum;
import me.yukun.storageblocker.config.FieldTypeEnum;

public class ValidationException extends Exception {

  public ValidationException(String errorMessage) {
    super(errorMessage);
  }

  public static String getErrorMessage(ConfigTypeEnum configType, FieldTypeEnum fieldType,
      String item) {
    return "Error in " + configType + ": " + fieldType + " not found at " + item + ".";
  }
}
