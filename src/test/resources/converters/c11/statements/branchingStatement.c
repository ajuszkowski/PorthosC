void method() {
    int *x, &y;
    if (*x == 1) {
        *y = 2;
//       //if (x == 2)
//        &x = *y;
    }
//    else if (*x > 2) {
//        &y = 3;
//    }
    else {}
    *x = y + 4;
}