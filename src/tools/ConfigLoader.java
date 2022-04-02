package tools;



import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;



public class ConfigLoader
{
  public static void load(Class<?> configClass, String file)
  {
    try
    {
      Properties props = new Properties();
      try (FileInputStream propStream = new FileInputStream(file)) { props.load(propStream); }

      for (Field field : configClass.getDeclaredFields())
        if (Modifier.isStatic(field.getModifiers()))
				{
					Object value = getValue(props, field.getName(), field.getType());

					if (value == null)
						continue;

          field.set(null, value);
				}
    }
    catch (Exception e)
    {
      throw new RuntimeException("Error loading configuration: " + e, e);
    }
  }

  private static Object getValue(Properties props, String name, Class<?> type)
  {
    String value = props.getProperty(name);

    if (value == null)
     	return null;

    if (type == String.class)
      return value;

    if (type == boolean.class)
      return Boolean.parseBoolean(value);

    if (type == int.class)
      return Integer.parseInt(value);
    
    if (type == float.class)
      return Float.parseFloat(value);

    throw new IllegalArgumentException("Unknown configuration value type: " + type.getName());
  }
}
