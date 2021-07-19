public class Runner {

  public static void main(String[] args) {
    DataStore kv = new DataStore();

    // part 1
//    kv.set("a", 10);
//    kv.set("b", 10);
//    System.out.println(kv.count(10));
//    System.out.println(kv.count(20));
//
//    kv.delete("a");
//    System.out.println(kv.count(10));
//
//    kv.set("b", 30);
//    System.out.println(kv.count(10));

    // part2
//    kv.begin();
//    kv.set("a", 10);
//    kv.set("b", 20);
//    System.out.println(kv.get("a"));
//
//    kv.begin();
//    kv.set("a", 20);
//    System.out.println(kv.get("a"));
//
//    kv.rollback();
//    System.out.println(kv.get("a"));
//
//    kv.rollback();
//    System.out.println(kv.get("a"));

    // part 3
    kv.begin();
    kv.set("a", 30);
    kv.begin();
    kv.set("a", 40);
    kv.commit();

    kv.begin();
    kv.set("a", 50);
    kv.commit();

    System.out.println(kv.get("a"));
  }
}
