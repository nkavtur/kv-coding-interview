import java.util.HashMap;
import java.util.Map;

public class WAL {

  static final int DELETED = "deleted!!".hashCode();

  Map<String, Integer> map = new HashMap<>();
  private Index valuesIndex = new Index();

  public Integer get(String key) {
    return map.get(key);
  }

  public void set(String key, Integer newValue) {
    Integer oldValue = map.put(key, newValue);
    valuesIndex.add(newValue);

    if (oldValue != null) {
      valuesIndex.delete(oldValue);
    }
  }

  public void delete(String key) {
    Integer value = map.put(key, DELETED);
    valuesIndex.delete(value);
  }

  public int count(Integer value) {
    return valuesIndex.count(value);
  }

  public void clear() {
    this.map.clear();
  }

}
