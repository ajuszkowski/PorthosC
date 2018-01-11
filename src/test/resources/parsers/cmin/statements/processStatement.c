process pX {
    int a = 1;
}
bug_on(a == 2);
//process p0 {
//    rcu_read_lock();
//    r1 = READ_ONCE(y);
//    WRITE_ONCE(x, 1);
//    rcu_read_unlock();
//}
//
//process p1 {
//    r2 = READ_ONCE(x);
//    synchronize_rcu();
//    WRITE_ONCE(z, 1);
//}
//
//process p3 {
//    rcu_read_lock();
//    r3 = READ_ONCE(z);
//    WRITE_ONCE(y, 1);
//    rcu_read_unlock();
//}

//bug_on(r1 == 1 && r2 == 1 && r3 == 1);