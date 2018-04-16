{
    (*x).a[3] = 3;
    x=3;
    t0:a = y;
}

void t0(int &x, int &y) {
    while (true) {
      int a = 1; //a <- 1;
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

exists ( y == 3  && t2:b == 3)

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