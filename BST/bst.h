#ifndef BST_H
#define BST_H

#include "node.h"

class bst{
public:
    //constructor
    bst();

    //constructor that takes in root's value
    bst(int input);

    //adds value to bst. Takes in root and doesn't allow duplicate values to be added
    void add(int input);

    //print all of the values in the tree in order
    void print();

    //removes a value based on its given value
    void remove(int value);

private:
    node* root;
    void addRecursion(node* root, int input);
    void printRecursion(node* root);
    void removeRecursion(node* root, int value);
};

#endif