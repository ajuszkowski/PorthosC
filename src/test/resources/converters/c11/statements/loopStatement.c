void method() {
    while (x == 0) { //1
        y = 1; //2
        if (x > 2) { //3
            break;
        }
        x = y; //4
    }

    while (x > 3) { //2
        y = x; //3
        while (x > 4) { //4
            if (y > 5) { //5
                continue;
            }
            else
                y = 6; //6

            if (x == 7) { //7
                break;
            }
            x = 8; //8
        }
        y = 9; //5
    }
    while (10) ; //3
    x = 11; //4
    ;
}