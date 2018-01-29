void P9 (int a) {} //nested declarator

void P0(spinlock_t *sl, long long *x0, int& x1, char c) {
	if (sl == 1)
	    if (sl2 == 2)
	        {
	            return;
	        }
    {
        a[2].print(*x1, x1->x3);
        a[i] = 3;
    }
	lbl int r1;
	while ( (a == 3 && b == 3) || x) {
        sp( &a);
    }
    do {
    	spin_lock(sl);
    } while (true);

	if (x) goto lbl;

	WRITE_ONCE(*x0, 1);
	r1 = READ_ONCE(*x1);
	spin_unlock(sl);
}

P0 {
	int r1;
	spin_lock(sl);
	WRITE_ONCE(*x0, 1);
	r1 = READ_ONCE(*x1);
	spin_unlock(sl);
}

int** P1(spinlock_t *sl, int *x0, int *x1)
{
	int r1;

	spin_lock(sl);
	WRITE_ONCE(*x1, 1);
	r1 = READ_ONCE(*x0);
	spin_unlock(sl);
}
/*
//exists(y == 22.3 \/ ( 0 :y == 1 /\ 2:x == 3:y /\ 4:x == 5:y \/ 100:a==0 )  );
//exists (r1=0 /\ 1:r1=0)

/*


P0(spinlock_t *sl, int *x0, int *x1)
{
	int r1;

	spin_lock(sl);
	WRITE_ONCE(*x0, 1);
	r1 = READ_ONCE(*x1);
	spin_unlock(sl);
}

P1(spinlock_t *sl, int *x0, int *x1)
{
	int r1;

	spin_lock(sl);
	WRITE_ONCE(*x1, 1);
	r1 = READ_ONCE(*x0);
	spin_unlock(sl);
}
*/

/*
void P0() {
    c1 = 1;
    c1 = 1L;
    c1 = 0b1;
    c2 = 0x99;
    c2 = 0xF;
    c2 = 03;
    a3 = 11;
    b = 1.2;
}

void P1 (int a, float& v) {
    if (a == 23) { v  = 3; }
}

P1 (int a, float& v) {
    if (a == 23) { v  = 3; }
}

struct s {
    int   x;
    float y;
    char  *z;
} tee;
//And the following statement will declare a similar union named u and an instance of it named n:
union u {
    int   x;
    float y;
    char  *z;
} n;


exists(y == 1 /\ (1:x == 2) /\ 2:x == 3);
//exists(y == 22.3 \/ (0:y == 1 /\ 1:z==0 ) \/ (2:a == 2 || 3:b == 3) && c == 4);
//exists(y == 22.3 \/ (0:y == 1 /\ 2:x == 3:y /\ 4:x == 5:y \/ 100:a==0 )  );
*/