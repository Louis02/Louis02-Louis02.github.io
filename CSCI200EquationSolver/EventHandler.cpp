#include "EventHandler.h"
#include "Operation.h"

#include <string>
#include <vector>
#include <iostream>
#include <fstream>


//constructor for Event Handler
//sets the expression to the read input function
//calls the read expression function with the input
EventHandler::EventHandler(){
    
    readFile();
    _ex = readInput();
    readExpression(_ex);

    //checks to make sure there is a correct amount of parenthesis
    int numOpen = 0;
    int numClose = 0;
    for(size_t i = 0; i < _pOperators->size(); i++){
        if(_pOperators->at(i) == "("){
            numOpen++;
        }
        else if(_pOperators->at(i) == ")"){
            numClose++;
        }
    }
    if(numOpen != numClose){
        std::cout << "Syntax error with the number of parenthesis" << "\n";
    }

    std::cout << "The answer is: " << evaluateExpression() << "\n";
}

void EventHandler::readFile(){
    std::ifstream fin;
    fin.open("expression.txt");
    if(fin.fail()){
        std::cout << "Couldn't open file\n";
        return;
    }
    int lines;
    fin >> lines;
    for(int i = 0; i < lines; i++){
        std::string ex;
        std::cout << "First file expression: ";
        fin >> _ex;
        std::cout << _ex << "\n";
        readExpression(_ex);
        std::cout << "The answer is: " << evaluateExpression() << "\n";
        delete _pOperators;
        _pOperators = nullptr;
        delete _pNums;
        _pNums = nullptr;
        delete _pOrder;
        _pOrder = nullptr;
        _pOperators = new std::vector<std::string>;
        _pNums = new std::vector<double>;
        _pOrder = new std::vector<int>;
    }
}

//asks the user to enter an expression and then saves it as a string and returns the string
std::string EventHandler::readInput(){  
    std::string ex;
    std::cout << "Rules:\n1) You must enter a whole number for the exponent\n2) Enter negative numbers as 0-2\n3) You can use (), *, /, +, -\n4) Don't use parenthesis around exponents\n";
    std::cout << "Enter an expression: ";
    std::getline(std::cin, ex);
    return ex;
}
// 7/6/2
//calculates the expression
double EventHandler::evaluateExpression(){
    Operation op;
    while(_pNums->size() > 1 && _pOperators->size() > 1){
        *_pOrder = orderOperators();
        int pos = _pOrder->at(0);
        if(_pOperators->at(pos) == "("){
            handleParenthesis(pos, true);
        }
        else{
            op.setFirst(_pNums->at(pos-1));
            op.setSecond(_pNums->at(pos+1));
            op.setOperator(_pOperators->at(pos));
            _pNums->at(pos-1) = op.execute();
            _pNums->erase(_pNums->begin() + pos);
            _pNums->erase(_pNums->begin() + pos); 
            _pOperators->erase(_pOperators->begin() + pos);
            _pOperators->erase(_pOperators->begin() + pos);
            _pOrder->erase(_pOrder->begin());
        }
    }
    //std::cout << _ex << "\n";
    return _pNums->at(0);
}

//returns the correct order for the order of operations based on inputs
std::vector<int> EventHandler::orderOperators(){
    std::vector<int> order;
    std::vector<std::vector<int>> hold;
    
    //changes the operations to scores then adds them to the holding vector along with their index
    for(size_t i = 0; i < _pOperators->size(); i++){
        std::string curString = _pOperators->at(i);
        std::vector<int> t;
        t.push_back(0);
        t.push_back(0);
        if(curString == "("){
            t.at(0) = 1;
            t.at(1) = (int)i;
            hold.push_back(t);
        }
        else if(curString == ")"){
            t.at(0) = 2;
            t.at(1) = (int)i;
            hold.push_back(t);
        }
        else if(curString == "^"){
            t.at(0) = 3;
            t.at(1) = (int)i;
            hold.push_back(t);
        }
        else if(curString == "*" || curString == "/"){
            t.at(0) = 4;
            t.at(1) = (int)i;
            hold.push_back(t);
        }
        else if(curString == "+" || curString == "-"){
            t.at(0) = 5;
            t.at(1) = (int)i;
            hold.push_back(t);
        }
    }

    //sorts the holding vector
    for(size_t i = 0; i < hold.size(); i++){
        for(size_t j = i+1; j < hold.size(); j++){
            int jV = hold.at(j).at(0);
            int iV = hold.at(i).at(0);
            if(jV < iV){
                std::vector<int> placeHolder = hold.at(i);
                hold.at(i) = hold.at(j);
                hold.at(j) = placeHolder;
            }
        }
    }

    //adds all the indexes to be returned
    for(size_t i = 0; i < hold.size(); i++){
        order.push_back((int)(hold.at(i).at(1)));
    }
    return order;
}

//takes in the input string and adds all the numbers to the pNums vector and operators to the _pOperators vector
void EventHandler::readExpression(std::string ex){
    std::string sNumToAdd = "";
    std::string operatorToAdd = "";
    //loops through the expression and adds everything to the appropriate vectors
    for(size_t i = 0; i < ex.length(); i++){
        char curChar = ex.at(i);
        //checks to see if curChar is a number or a .
        if((curChar > 47 && curChar < 58) || curChar == 46){
            //adds the current number to the string holder for numbers
            sNumToAdd += curChar;
        }
        //checks to see if curChar is an operator
        if((curChar > 39 && curChar < 46) || curChar == 47 || curChar == 37 || curChar == 94 || curChar == 40 || curChar == 41 || i == (ex.length()-1)){
            //adds the string of numbers as a double to _pNums
            if(sNumToAdd.size() > 0){
                double numToAdd = std::stod(sNumToAdd);
                sNumToAdd = "";
                _pNums->push_back(numToAdd);
                _pOperators->push_back("null");
                if(i == (ex.length())){
                    break;
                }
            }
            //checks to make sure nothing is added to the operators at the end of the expression
            if(i != (ex.length())-1 || curChar == ')'){
                //adds the current operator to the operators function
                operatorToAdd += curChar;
                _pOperators->push_back(operatorToAdd);
                _pNums->push_back(-1.23456789);
                operatorToAdd = "";
            }
        }
    }
}

void EventHandler::handleParenthesis(int &pos, bool del){
    
    Operation op;
    if(del){
        _pNums->erase(_pNums->begin() + pos);
        _pOperators->erase(_pOperators->begin() + pos);
    }
    else{
        pos++;
    }
    while(_pOperators->at(pos+1) != ")"){
        *_pOrder = orderOperators();
        if(_pOperators->at(pos) == "("){
            pos = _pOrder->at(0);
            handleParenthesis(pos, false);
            return;
        }
        else if(_pNums->at(pos) != -1.23456789){
            op.setFirst(_pNums->at(pos));
            op.setSecond(_pNums->at(pos+2));
            op.setOperator(_pOperators->at(pos+1));
            _pNums->at(pos) = op.execute();
            _pNums->erase(_pNums->begin() + pos+1);
            _pNums->erase(_pNums->begin() + pos+1);
            _pOperators->erase(_pOperators->begin() + pos);
            _pOperators->erase(_pOperators->begin() + pos);
            _pOrder->erase(_pOrder->begin());
        }
    }
    _pOperators->erase(_pOperators->begin() + pos+1);
    _pNums->erase(_pNums->begin() + pos+1);
    pos = _pOrder->at(0);
    return;
}