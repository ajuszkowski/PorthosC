{ int flag0, flag1, turn; }

void t0() {
    while (1 == 1) {
      int a = 1;
      int b = 0;
      flag0.store(_rx,a);
      f1 = flag1.load(_rx);
      while (f1 == 1) {
        t1 = turn.load(_rx);
        if (t1 != 0) {
          flag0.store(_rx,b);
          t1 = turn.load(_rx);
          while (t1 != 0) {
            t1 = turn.load(_rx);
          }
          flag0.store(_rx,a);
        }
      }
    }
}

void t1() {
    while (1 == 1) {
      int c = 1;
      int d = 0;
      flag1.store(_rx,c);
      f2 = flag0.load(_rx);
      while (f2 == 1) {
        t2 = turn.load(_rx);
        if (t2 != 1) {
          flag1.store(_rx,d);
          t2 = turn.load(_rx);
          while (t2 != 1) {
            t2 = turn.load(_rx);
          }
          flag1.store(_rx,c);
        }
      }
    }
}

exists (turn==10)