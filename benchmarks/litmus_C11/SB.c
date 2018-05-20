//C SB+onces

{ int x = 0; int y = 0;}
//{}


P0(volatile int* y, volatile int* x) {
  int r0;
  WRITE_ONCE(*x,1);
  r0 = READ_ONCE(*y);
}

P1(volatile int* y, volatile int* x) {
  int r0;
  WRITE_ONCE(*y,1);
  r0 = READ_ONCE(*x);
}

//exists (0:r0=0 /\ 1:r0=0)
exists (0:r0==0 && 1:r0==0)