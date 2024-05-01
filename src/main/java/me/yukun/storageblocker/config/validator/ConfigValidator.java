package me.yukun.storageblocker.config.validator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import me.yukun.storageblocker.config.ConfigTypeEnum;
import me.yukun.storageblocker.config.FieldTypeEnum;
import me.yukun.storageblocker.util.ValidationExceptionBiConsumer;
import org.bukkit.Material;
import org.bukkit.Registry;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.inventory.InventoryType;

public class ConfigValidator implements IValidator {

  private final Set<String> found = new HashSet<>();
  private static final ValidationExceptionBiConsumer<FileConfiguration, String> NO_VALIDATION = (fileConfiguration, s) -> {
  };
  private final Map<String, ValidationExceptionBiConsumer<FileConfiguration, String>> fieldValidatorMap = new HashMap<>() {{
    put("ItemType", (config, field) -> validateItemTypeField(config, field));
    put("Name", (config, field) -> validateNameField(config, field));
    put("Lore", NO_VALIDATION);
    put("Enchantments", (config, field) -> validateEnchantmentsField(config, field));
    put("InventoryTypes", (config, field) -> validateInventoryTypesField(config, field));
  }};

  @Override
  public void validate(FileConfiguration config) throws ValidationException {
    validateSections(config);
    validateFields(config);
  }

  private void validateSections(FileConfiguration config) throws ValidationException {
    for (String section : Objects.requireNonNull(config.getConfigurationSection(""))
        .getKeys(false)) {
      if (found.contains(section)) {
        throw new ValidationException(
            ValidationException.getErrorMessage(ConfigTypeEnum.CONFIG, FieldTypeEnum.SECTION,
                section));
      }
      found.add(section);
    }
  }

  private void validateFields(FileConfiguration config) throws ValidationException {
    for (String section : found) {
      for (String field : Objects.requireNonNull(config.getConfigurationSection(section))
          .getKeys(false)) {
        if (!fieldValidatorMap.containsKey(field)) {
          throw new ValidationException(
              ValidationException.getErrorMessage(ConfigTypeEnum.CONFIG, FieldTypeEnum.SECTION,
                  section));
        }
        fieldValidatorMap.get(field).apply(config, section);
      }
    }
  }

  private void validateItemTypeField(FileConfiguration config, String key)
      throws ValidationException {
    key += ".ItemType";
    validateStringField(config, key);
    if (!config.isString(key)) {
      throw new ValidationException(
          ValidationException.getErrorMessage(ConfigTypeEnum.CONFIG, FieldTypeEnum.STRING, key));
    }
    //noinspection ConstantConditions
    if (Material.getMaterial(config.getString(key)) == null) {
      throw new ValidationException(
          ValidationException.getErrorMessage(ConfigTypeEnum.CONFIG, FieldTypeEnum.STRING, key));
    }
  }

  private void validateNameField(FileConfiguration config, String key)
      throws ValidationException {
    key += ".Name";
    validateStringField(config, key);
  }

  private void validateEnchantmentsField(FileConfiguration config, String key)
      throws ValidationException {
    key += ".Enchantments";
    for (String stringEnchant : config.getStringList(key)) {
      String[] stringEnchantSplit = stringEnchant.split(":");
      if (Registry.ENCHANTMENT.match(stringEnchantSplit[0]) == null) {
        throw new ValidationException(
            ValidationException.getErrorMessage(ConfigTypeEnum.CONFIG, FieldTypeEnum.STRINGLIST,
                key));
      }
      if (!isInt(stringEnchantSplit[1])) {
        throw new ValidationException(
            ValidationException.getErrorMessage(ConfigTypeEnum.CONFIG, FieldTypeEnum.STRINGLIST,
                key));
      }
    }
  }

  private void validateInventoryTypesField(FileConfiguration config, String key)
      throws ValidationException {
    key += ".InventoryTypes";
    for (String inventoryType : config.getStringList(key)) {
      try {
        InventoryType.valueOf(inventoryType);
      } catch (IllegalArgumentException e) {
        throw new ValidationException(
            ValidationException.getErrorMessage(ConfigTypeEnum.CONFIG, FieldTypeEnum.STRINGLIST,
                key));
      }
    }
  }

  @Override
  public void validateStringField(FileConfiguration config, String field)
      throws ValidationException {
    if (!config.isString(field)) {
      throw new ValidationException(
          ValidationException.getErrorMessage(ConfigTypeEnum.CONFIG, FieldTypeEnum.STRING, field));
    }
  }

  private static boolean isInt(String s) {
    try {
      Integer.parseInt(s);
    } catch (NumberFormatException nfe) {
      return false;
    }
    return true;
  }
}
