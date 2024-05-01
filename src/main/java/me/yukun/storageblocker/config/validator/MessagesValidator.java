package me.yukun.storageblocker.config.validator;

import java.util.HashMap;
import java.util.Map;
import me.yukun.storageblocker.config.ConfigTypeEnum;
import me.yukun.storageblocker.config.FieldTypeEnum;
import org.bukkit.configuration.file.FileConfiguration;

public class MessagesValidator implements IValidator {

  private final Map<String, FieldTypeEnum> fields = new HashMap<>(7) {{
    put("Prefix", FieldTypeEnum.STRING);
    put("Blocked", FieldTypeEnum.STRING);
  }};

  @Override
  public void validate(FileConfiguration messages) throws ValidationException {
    validateFields(messages);
  }

  private void validateFields(FileConfiguration messages) throws ValidationException {
    for (String field : fields.keySet()) {
      FieldTypeEnum fieldType = fields.get(field);
      validateField(fieldType, messages, field);
    }
  }

  @Override
  public void validateStringField(FileConfiguration messages, String field)
      throws ValidationException {
    if (!messages.isString(field)) {
      throw new ValidationException(
          ValidationException.getErrorMessage(ConfigTypeEnum.MESSAGES, FieldTypeEnum.STRING,
              field));
    }
  }
}
