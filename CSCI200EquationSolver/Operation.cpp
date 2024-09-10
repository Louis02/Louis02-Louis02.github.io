#include "Operation.h"
#include <string>
#include <iostream>

Operation::Operation(){
    _first = 1;
    _second = 1;
    _operator = "+";
}

Operation::Operation(double f, double s, std::string op){
    _first = f;
    _second = s;
    _operator = op;
}

double Operation::execute(){
    if(_operator == "+"){
        return add();
    }
    if(_operator == "-"){
        return subtract();
    }
    if(_operator == "*"){
        return mult();
    }
    if(_operator == "/"){
        return divide();
    }
    if(_operator == "^"){
        return power();
    }
    return 0.0;
}

double Operation::add(){
    return _first + _second;
}

double Operation::subtract(){
    return _first - _second;
}

double Operation::mult(){
    return _first * _second;
}

double Operation::divide(){
    return _first / _second;
}

double Operation::power(){
    double ans = _first;
    if(_second < 0){
        _first = 1/_first;
        ans = 1/ans;
    }
    for(int i = 1; i < _second; i++){
        ans *= _first;
    }
    if(_second == 0){
        ans = 1;
    }
    return ans;
}

void Operation::setFirst(const double f){
    _first = f;
}

void Operation::setSecond(const double s){
    _second = s;
}

void Operation::setOperator(const std::string op){
    _operator = op;
}

double Operation::getFirst() const{
    return _first;
}

double Operation::getSecond() const{
    return _second;
}

std::string Operation::getOperator() const{
    return _operator;
}