#ifndef EVENTHANDLER_H
#define EVENTHANDLER_H

#include <string>
#include <vector>

class EventHandler{
public:
    /**
    * @brief EventHandler Constructor
    * @desc intializes member data and calls functions to calculate expression
    */
    EventHandler();

    /**
    * @brief reads the expression you input
    * @desc takes the inputted expression and reads it, dividing it up into the correct vectors 
    * @param ex the expression that will be read
    * @return void
    */
    void readExpression(std::string ex);

    /**
    * @brief Asks and reads input
    * @desc asks the user to input an expression and then saves it
    * @return string
    */
    std::string readInput();

    /**
    * @brief Evaluates the inputted expression
    * @desc turns the pNums vector into the single correct answer and returns it
    * @return double
    */
    double evaluateExpression();

    /**
    * @brief Sorts out the correct order of operation
    * @desc gives back a vector that contains the order that the operators should be calculated in based on the expression
    * @return std::vector<int>
    */
    std::vector<int> orderOperators();

    /**
    * @brief Calculates whats inside the parenthesis
    * @desc Takes in a portion of the expression and then alters it into the correct answer for the parenthesis
    * @param pos the position of the parenthesis
    * @param del tells if the first parenthesis should be deleted
    * @return void
    */
    void handleParenthesis(int &pos, bool del);

    /**
    * @brief Reads from the file
    * @desc takes in and calculates all the answers from the file saved
    * @return void
    */
    void readFile();
private:
    std::string _ex;
    std::vector<std::string>* _pOperators = new std::vector<std::string>;
    std::vector<double>* _pNums = new std::vector<double>;
    std::vector<int>* _pOrder = new std::vector<int>;
};

#endif