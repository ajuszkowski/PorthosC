int x;
int * x;
unsigned int x;
unsigned int* x;
_Atomic(int* x);
_Atomic(int x);
_Atomic int* x;
int* _Atomic x;

char x;
unsigned char x;
short x;
short int x;
long x;
long int x;
long long x;
long long int x;

const int x;
const int* x;
volatile int x;
volatile int* x;
restrict int x;
restrict int* x;

void* x;

auto x = 1;

int x = 1;
int x = 1, y = 2;

struct X x;
enum X x;

custom_type x;  // after `typedef custom_type int;`
extern custom_type x;

