#include <iostream>
using namespace std;
unsigned int russianPeasant(unsigned int a, unsigned int b)
{
    int result = 0; 
    while (b > 0)
    {
        if (b & 1)
            result = result + a;	
        a = a << 1;
        b = b >> 1;
    }
    return result;
}
int main()
{
    unsigned int a,b;
    cout << "Enter two numbers to multiply" << endl;
    cin >> a >> b;
    cout << "product: "<< russianPeasant(a, b) << endl;
    return 0;
}