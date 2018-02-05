//void P9 (int a) {} //nested declarator

void P() {
    while (cond1) {
        if (cond2) {
            a = 2;
            continue;
            //break;
            dead_code;
        }
        else {
            code_false;
        }
        //a = 1;
    }
}

/*void P1(spinlock_t *sl, long long *x0, int& x1, char c) {
//    while (1) {
//        x++;
//        y--;
//    }
    y = x > 100500;
    while (x == 1) {
        x = 1.1;
        while (1.5) ;
        if (x == 2) {
            while (x == 3) {
                a = 4;
            }
            continue;
        }
        else {
            b = 5;
            break;
        }
    }
    d = 7;

}
*/


//    lll int r0;
//    void* a, b, c ,d, e;
//	if (sl == 1)
//	    if (sl2 == 2)
//	        {
//	            return;
//	        }
//    {
//        a[2].print(*x1, x1->x3);
//        a[i] = 3;
//    }
//	lbl int r1;
//	while ( (a == 3 && b == 3) || x) {
//        sp( &a);
//    }
//    do {
//    	function_1_1(sl);
//    } while (true);
//
//	if (x) goto lbl;
//
//	function_1_2(*x0, 1);
//	r1 = function_1_3(*x1);
//	function_1_4(sl);

/*P2 {
	int r2 ;//= function_2_1(sl);
	function_2_2(*x0, 1);
}

int** P3(type_3 *sl, int *x0, int *x1)
{
	int r3;
	function_3_1(sl);
}
*/

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