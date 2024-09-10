#include "bst.h"

#include <iostream>

int main(){
    bst b(10);
    b.add(6);
    b.add(11);
    b.add(8);
    b.add(1);
    b.add(99);
    b.add(56);
    b.print();
    b.remove(6);
    std::cout << "\n";
    b.print();
}
