//{
//    int x = 1, *y;
//}

void test(int &x, int &y, int &z) {
    while (++x == 1) {
        if (y > 2)
            break;
        z = 3;
    }
    x = 4;
}



//void P0(int &z) {
//    int r0;
//
//    //x = y++  > 0;  // tmp=y; x=tmp>0; y=tmp+1;
//    //x = ++y  > 0;  // tmp=y; y=tmp+1; x=tmp>0;
//    //x = r0++ > 0;  // r0=r0+1; x=r0;
//    //x = ++r0 > 0;  // x=r0; r0=r0+1;
//
//
////    *y = ((++r0) + 1) * 2 % (--x) + 3;
//
//    while (true) {
//        if (*y > 10)
//            break;
//
//        int r0 = *y;
//        z = 10;
//        while (x < 10) {
//            if (z == 5)
//                break;
//        }
//
//        if (*y == 42) {
//            continue;
//        }
//        else {
//            //return;
//            break;
//        }
//        *y = 1;
//    }
//}
//
//void P1(int &x) {
//    z = 5;
//    while (*y > 0) {
//        while (x > 1) {
//            if (x > 2)
//                break;
//        }
//        if (x > 3)
//            break;
//    }
//    *y = 42;
//}
//
//
//exists (x == 0 || (x == 1 && z ==10 && P0:r0 == 10))
//
//
//
//// Manna and Pnueli 1995 (ben-ari)
//// https://mycourses.aalto.fi/pluginfile.php/536088/mod_resource/content/1/tutorial1.pdf
//
////{
////    int* want_p, want_q = 1;
////    int critical_var_1, critical_var_2;
////}
////
////void process_p(int &x, int &y) {
////    while (true) {
////        non_critical_section();
////
////        if (want_q == -1) {
////            want_p = -1;
////        }
////        else {
////            want_p = 1;
////        }
////
////        while (want_p == want_q) ;
////
////        // critical_section:
////        {
////
////        }
////
////        want_p = 0;
////    }
////}
////
////
////void process_q(int &x, int &y) {
////    while (true) {
////        non_critical_section();
////
////        want_q = (want_p == -1) ? 1 : -1;
////
////        while (want_p != want_q) ;
////
////        // critical_section:
////        {
////            critical_var_2
////        }
////
////        want_p = 0;
////    }
////}
//
//
