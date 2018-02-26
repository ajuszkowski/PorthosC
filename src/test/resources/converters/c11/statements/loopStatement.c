void method() {
    while (x == 0) {
        y = 1;
        if (x > 2) {
            break;
        }
        x = y;
    }

    while (x > 3) {
        y = x;
        while (x > 4) {
            if (y > 5) {
                continue;
            }
            else
                y = 6;

            if (x == 7) {
                break;
            }
            x = 8;
        }
        y = 9;
    }
    while (10) ;
    x = 11;
    ;
}