import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Index {

  private Map<Integer, AtomicInteger> counts = new HashMap<>();

  public void add(Integer value) {
    AtomicInteger valueCounts = counts.getOrDefault(value, new AtomicInteger(0));
    valueCounts.incrementAndGet();
    counts.put(value, valueCounts);
  }

  public int count(Integer value) {
    return counts.getOrDefault(value, new AtomicInteger(0)).intValue();
  }

  public void delete(Integer value) {
    AtomicInteger valueCounts = counts.getOrDefault(value, new AtomicInteger(0));

    if (valueCounts.get() <= 0) {
      return;
    }

    valueCounts.decrementAndGet();
  }
}
