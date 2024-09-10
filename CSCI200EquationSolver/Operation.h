#ifndef OPERATION_H
#define OPERATION_H

#include <string>

class Operation{
public:
    /**
    * @brief Operation constructor
    * @desc Initializes appropriate variables to 0
    */
    Operation();

    /**
    * @brief Parameterized Constructor
    * @desc Sets first last and operator to correct inputs
    * @param f the first number
    * @param s the second number
    * @param op the operator
    */
    Operation(double f, double s, std::string op);

    /**
    * @brief Calls the correct function
    * @desc Calls the correct function based on the operator and returns the answer
    * @return double
    */
    double execute();

    /**
    * @brief Adds the numbers
    * @desc Adds the first and second numbers and returns the answer
    * @return double
    */
    double add();

    /**
    * @brief Subtracts the numbers
    * @desc Subtracts the first and second numbers and returns the answer
    * @return double
    */
    double subtract();

    /**
    * @brief Multiplies the numbers
    * @desc Multiplies the first and second numbers and returns the answer
    * @return double
    */
    double mult();

    /**
    * @brief Divides the numbers
    * @desc Divides the first and second numbers and returns the answer
    * @return double
    */
    double divide();

    /**
    * @brief Takes the powr of the numbers
    * @desc Takes the power of the first and second numbers and returns the answer
    * @return double
    */
    double power();

    /**
    * @brief Sets the first number
    * @desc Sets first to the input
    * @param f the first number
    * @return void
    */
    void setFirst(const double f);

    /**
    * @brief Sets the second number
    * @desc Sets second to the input
    * @param s the second number
    * @return void
    */
    void setSecond(const double s);

    /**
    * @brief Sets the operator
    * @desc Sets operator to the input
    * @param op the operator
    * @return void
    */
    void setOperator(const std::string op);

    /**
    * @brief Returns the first number
    * @desc Returns the first number
    * @return double
    */
    double getFirst() const;

    /**
    * @brief Returns the second number
    * @desc Returns the second number
    * @return double
    */
    double getSecond() const;

    /**
    * @brief Returns the operator
    * @desc Returns the operator
    * @return double
    */
    std::string getOperator() const;

private:
    std::string _operator;
    double _first;
    double _second;
};

#endif