import java.util.HashMap;
import java.util.Map;

public class DataStore {

  private final Map<String, Integer> map = new HashMap<>();

  private Index valuesIndex = new Index();
  private Transaction transaction;

  public Integer get(String key) {
    if (transaction == null) {
      return map.get(key);
    } else {
      return transaction.get(key);
    }
  }

  public void set(String key, Integer newValue) {
    if (transaction == null) {

      Integer oldValue = map.put(key, newValue);
      valuesIndex.add(newValue);

      if (oldValue != null) {
        valuesIndex.delete(oldValue);
      }
    } else {
      transaction.set(key, newValue);
    }
  }

  public void setNoTransaction(String key, Integer newValue) {
    Integer oldValue = map.put(key, newValue);
    valuesIndex.add(newValue);

    if (oldValue != null) {
      valuesIndex.delete(oldValue);
    }
  }

  public void delete(String key) {
    if (transaction == null) {
      Integer value = map.remove(key);
      valuesIndex.delete(value);
    } else {
      transaction.delete(key);
    }
  }

  public int count(Integer value) {
    return valuesIndex.count(value);
  }

  public void begin() {
    if (transaction == null) {
      transaction = new Transaction();
    } else {
      transaction.beginNext();
    }
  }

  public void rollback() {
    if (transaction == null) {
      System.out.println("No transaction to rollback!");
    } else {
      transaction.rollback();
    }
  }

  public void commit() {
    if (transaction == null) {
      System.out.println("No transaction to commit!");
    } else {
      transaction.commit(this);
      transaction = null;
    }
  }
}
