//C SB+onces

{ int x = 0; int y = 0;}

//
//P0(volatile int* y, volatile int* x) {
//  int r0;
//  WRITE_ONCE(*x,1);
//  r0 = READ_ONCE(*y);
//}
//
//P1(volatile int* y, volatile int* x) {
//  int r0;
//  WRITE_ONCE(*y,1);
//  r0 = READ_ONCE(*x);
//}

//exists (P0:r0==111 && P1:r0==222)
exists(x == 1 && y == 2)