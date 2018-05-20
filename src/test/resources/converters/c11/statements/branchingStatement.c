//parker
{
    int cond = 0, parkCounter = 0;
}

void t0(int& cond, int& parkCounter) {
    int c = cond.load(_rx);
    while (c == 0) {
      counter = parkCounter.load(_rx);
      int a = 0;
      parkCounter.store(_rx,a);
      c = cond.load(_rx);
    }
}

void t1(int& cond, int& parkCounter) {
    int b = 1;
    cond.store(_rx,b);
    parkCounter.store(_rx,b);
}

exists	(cond == 0 && parkCounter == 1)

/*{
//    x=3;
}

void t0(int &x, int &y) {
    while (true) {
      int a = (0 + 1 * (3 / 2) % 1); //a <- 1;
      x.store(_rx, a);
      int chk = y.load(_rx);
      while (chk != 0) {
        chk = y.load(_rx);
      }
      a = 0;
      x.store(_rx, a);
    }
}

void t1(int &x, int &y) {
//    int b;
    while (true) {
      int chk = x.load(_rx);
      while (chk != 0) {
        chk = x.load(_rx);
      }
      int b = 1; //b <- 1;
      y.store(_rx,b);
      chk = x.load(_rx);
      b = 0; //b <- 0;
      if (chk == 0) {
          y.store(_rx, b);
      }
      else {
          y.store(_rx, b);
      }
    }
}

exists ( y == 3  || (x == 2 && t1:b == 1) )

//void method() {
//
//    int *x, &y;
//    if (x > 1) {
//        if (x > 2) {
//            x = 200;
//        }
//        else if (x > 3) {
//            x = 300;
//        }
//    }
//    else {
//        x = 100;
//    }
//    x = 1;
//
//    //if (1 > 0) {
//    //    if (*x == 1) {
//    //        *y = 2;
//    //    }
//    //    else  {
//    //        y  = 3;
//    //     }
//    //     x=100;
//    //}
//    //else {
//    //    y = 4;
//    // }
//    //*x =  1 + 4;
//}

*/