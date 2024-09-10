#include "bst.h"

#include <iostream>


//constructors
bst::bst(){
    root = new node;
}

bst::bst(int input){
    root = new node;
    root->value = input;
}


//adds a value
void bst::add(int input){
    addRecursion(root, input);
}
void bst::addRecursion(node* root, int input){
    if(root == nullptr){
        return;
    }

    if(input < root->value){
        if(root->left != nullptr){
            addRecursion(root->left, input);
        }
        else{
            root->left = new node;
            root->left->value = input;
        }
    }
    else if(input > root->value){
        if(root->right != nullptr){
            addRecursion(root->right, input);
        }
        else{
            root->right = new node;
            root->right->value = input;
        }
    }

}


//print all values
void bst::print(){
    printRecursion(root);
}
void bst::printRecursion(node* root){
    if(root == nullptr){
        return;
    }

    printRecursion(root->left);
    std::cout << root->value << "\n";
    printRecursion(root->right);
}


//removes a value
void bst::remove(int value){
    removeRecursion(root, value);
}
void bst::removeRecursion(node* root, int value){
    if(root == nullptr){
        std::cout << "There was no node with value: " << value << "\n";
        return;
    }

    //if the left child is value
    if(value < root->value){
        //if we are removing the left child
        if(root->left->value == value){
            //if the left child's left child exists 
            if(root->left->left != nullptr){
                //if the right child also exists (two children case)
                if(root->left->right != nullptr){

                }
                //if only the left child exists (one child case)
                if(root->left->right == nullptr){
                    node* hold = root->left->left;
                    delete root->left;
                    root->left = nullptr;
                    root->left = hold;
                }
            }
            //if only the left child's right child exists (one child case)
            else if(root->left->right != nullptr){
                node* hold = root->left->right;
                delete root->left;
                root->left = nullptr;
                root->left = hold;
            }
            //the left child has no children (no children case)
            else{
                delete root->left;
                root->left = nullptr;
            }
        }
        else{
            removeRecursion(root->left, value);
        }
    }
    //if the right child is value
    else if(value > root->value){
        //if we are removing the right child
        if(root->right->value == value){
            //if the right child's left child exists 
            if(root->right->left != nullptr){
                //if the right child also exists (two children case)
                if(root->right->right != nullptr){

                }
                //if only the left child exists (one child case)
                if(root->right->right == nullptr){
                    node* hold = root->right->left;
                    delete root->right;
                    root->right = nullptr;
                    root->right = hold;
                }
            }
            //if only the right child's right child exists (one child case)
            else if(root->right->right != nullptr){
                node* hold = root->right->right;
                delete root->right;
                root->right = nullptr;
                root->right = hold;
            }
            //the right child has no children (no children case)
            else{
                delete root->right;
                root->right = nullptr;
            }
        }
        else{
            removeRecursion(root->left, value);
        }
    }
}

