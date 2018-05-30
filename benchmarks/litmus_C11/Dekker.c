{ int flag0 = 0, flag1 = 0, turn = 0; }

void t0() {
    while (true) {
      int a = 1;
      int b = 0;
      flag0.store(memory_order_relaxed,a);
      f1 = flag1.load(memory_order_relaxed);
      while (f1 == 1) {
        t1 = turn.load(memory_order_relaxed);
        if (t1 != 0) {
          flag0.store(memory_order_relaxed, b);
          t1 = turn.load(memory_order_relaxed);
          while (t1 != 0) {
            t1 = turn.load(memory_order_relaxed);
          }
          flag0.store(memory_order_relaxed, a);
        }
      }
    }
}

void t1() {
    while (true) {
      int c = 1;
      int d = 0;
      flag1.store(memory_order_relaxed,c);
      f2 = flag0.load(memory_order_relaxed);
      while (f2 == 1) {
        t2 = turn.load(memory_order_relaxed);
        if (t2 != 1) {
          flag1.store(memory_order_relaxed,d);
          t2 = turn.load(memory_order_relaxed);
          while (t2 != 1) {
            t2 = turn.load(memory_order_relaxed);
          }
          flag1.store(memory_order_relaxed, c);
        }
      }
    }
}

exists (turn == 10)