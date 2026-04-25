package api.generators;

import com.github.curiousoddman.rgxgen.RgxGen;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

public class RandomModelGenerator {

  private static final Random random = new Random();


  @SuppressWarnings("unchecked")
  public static <T> T generateWithDefaults(Class<T> clazz) {
    try {
      Method builderMethod = clazz.getDeclaredMethod("builder");
      Object builder = builderMethod.invoke(null);
      Method buildMethod = builder.getClass().getDeclaredMethod("build");
      T instance = (T) buildMethod.invoke(builder);
      for (Field field : getAllFields(clazz)) {
        GeneratingRule rule = field.getAnnotation(GeneratingRule.class);
        if (rule != null) {
          field.setAccessible(true);
          field.set(instance, generateFromRegex(rule.regex(), field.getType()));
        }
      }
      return instance;
    } catch (Exception e) {
      throw new RuntimeException("Failed to generate entity", e);
    }
  }

  public static <T> T generate(Class<T> clazz, String... fieldNames) {
    try {
      T instance = clazz.getDeclaredConstructor().newInstance();
      Set<String> requested = new HashSet<>(Arrays.asList(fieldNames));
      for (Field field : getAllFields(clazz)) {
        field.setAccessible(true);
        if (!requested.contains(field.getName())) {
          if (!field.getType().isPrimitive()) {
            field.set(instance, null);
          }
          continue;
        }
        GeneratingRule rule = field.getAnnotation(GeneratingRule.class);
        if (rule != null) {
          field.set(instance, generateFromRegex(rule.regex(), field.getType()));
        }
      }
      return instance;
    } catch (Exception e) {
      throw new RuntimeException("Failed to generate entity", e);
    }
  }

  public static <T> T generate(Class<T> clazz) {
    try {
      T instance = clazz.getDeclaredConstructor().newInstance();
      for (Field field : getAllFields(clazz)) {
        field.setAccessible(true);

        Object value;
        GeneratingRule rule = field.getAnnotation(GeneratingRule.class);
        if (rule != null) {
          value = generateFromRegex(rule.regex(), field.getType());
        } else {
          value = generateRandomValue(field);
        }
        field.set(instance, value);
      }
      return instance;
    } catch (Exception e) {
      throw new RuntimeException("Failed to generate entity", e);
    }
  }

  private static List<Field> getAllFields(Class<?> clazz) {
    List<Field> fields = new ArrayList<>();
    while (clazz != null && clazz != Object.class) {
      fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
      clazz = clazz.getSuperclass();
    }
    return fields;
  }

  private static Object generateRandomValue(Field field) {
    Class<?> type = field.getType();
    if (type.equals(String.class)) {
      return UUID.randomUUID().toString().substring(0, 8);
    } else if (type.equals(Integer.class) || type.equals(int.class)) {
      return random.nextInt(1000);
    } else if (type.equals(Long.class) || type.equals(long.class)) {
      return random.nextLong();
    } else if (type.equals(Double.class) || type.equals(double.class)) {
      return random.nextDouble() * 100;
    } else if (type.equals(Boolean.class) || type.equals(boolean.class)) {
      return random.nextBoolean();
    } else if (type.equals(List.class)) {
      return generateRandomList(field);
    } else if (type.equals(Date.class)) {
      return new Date(System.currentTimeMillis() - random.nextInt(1000000000));
    } else {
      // Вложенный объект
      return generate(type);
    }
  }

  private static Object generateFromRegex(String regex, Class<?> type) {
    RgxGen rgxGen = new RgxGen(regex);
    String result = rgxGen.generate();
    if (type.equals(Integer.class) || type.equals(int.class)) {
      return Integer.parseInt(result);
    } else if (type.equals(Long.class) || type.equals(long.class)) {
      return Long.parseLong(result);
    } else {
      return result;
    }
  }

  private static List<String> generateRandomList(Field field) {
    Type genericType = field.getGenericType();
    if (genericType instanceof ParameterizedType) {
      ParameterizedType pt = (ParameterizedType) genericType;
      Type actualType = pt.getActualTypeArguments()[0];
      if (actualType == String.class) {
        return List.of(UUID.randomUUID().toString().substring(0, 5),
                UUID.randomUUID().toString().substring(0, 5));
      }
    }
    return Collections.emptyList();
  }
}
