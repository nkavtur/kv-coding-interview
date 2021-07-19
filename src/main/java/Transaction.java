import java.util.concurrent.atomic.AtomicInteger;

public class Transaction {

  private static AtomicInteger counter = new AtomicInteger();

  Integer id = counter.incrementAndGet();
  Transaction next;
  Transaction prev;

  Transaction tail = this;
  WAL wal = new WAL();

  public void beginNext() {
    Transaction current = this;
    while (current.next != null) {
      prev = current;
      current = current.next;
      current.prev = prev;
    }

    current.next = new Transaction();
    current.next.prev = current;
    this.tail = current.next;
  }

  public void rollback() {
    if (tail == this) {
      this.wal.clear();
    } else {
      Transaction toRollback = this.tail;
      toRollback.wal.clear();

      Transaction newTail = toRollback.prev;
      this.tail = newTail;
      this.tail.next = null;
    }

  }

  public void commit(DataStore kv) {
    if (tail == this) {

      flush(tail.wal, kv);

    } else {

      Transaction current = this;
      while (current != null) {
        flush(current.wal, kv);
        current = current.next;
      }
    }
  }

  private void flush(WAL wal, DataStore kv) {
    for (var entry : wal.map.entrySet()) {
      String key = entry.getKey();
      Integer value = entry.getValue();

      if (value == WAL.DELETED) {
        kv.delete(key);
      } else {
        kv.setNoTransaction(key, value);
      }
    }
  }

  public void set(String key, Integer newValue) {
    this.tail.wal.set(key, newValue);
  }

  public void delete(String key) {
    this.tail.wal.delete(key);
  }


  public Integer get(String key) {
    Transaction current = tail;
    while (current != null) {

      if (current.wal.get(key) != null) {
        return current.wal.get(key);
      }

      current = current.prev;
    }

    return null;
  }
}
