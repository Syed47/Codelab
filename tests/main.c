#include "util.c"

int main()
{
    char buffer[10] = {0};
    scanf("%s", buffer);
    buffer[9] = 0;
    sayHello(buffer);
    return 0;
}