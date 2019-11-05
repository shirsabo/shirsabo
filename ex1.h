//
// Created by osboxes on 11/4/19.
//
#ifndef EX1_H
#define EX1_H
#include <string>
#include <iostream>
#include "Expression.h"
class BinaryOperator: public Expression {
public:
    BinaryOperator(Expression *right, Expression *left);
    virtual ~BinaryOperator();
    // right and left expressions
    Expression * right;
    Expression * left;
};
class Plus: public BinaryOperator{

public:

    Plus(Expression *right, Expression *left);

    double calculate();

    virtual ~Plus();
};
class Minus: public BinaryOperator{

public:

    Minus(Expression *right, Expression *left);

    double calculate();

    virtual ~Minus();
};
class Mul: public BinaryOperator{

public:

    Mul(Expression *right, Expression *left);

    double calculate();

    virtual ~Mul();
};
class Div: public BinaryOperator{

public:
    Div(Expression *right, Expression *left);

    double calculate();

    virtual ~Div();
};
class Value: public Expression {
public:
    double calculate();

    virtual ~Value();

private:
    const double val;
};
class Variable: public Expression {
public:
    string name;
    double value;
   Variable   operator ++ () ;
   Variable operator --() ;
   Variable   operator +=(double  var) ;
  Variable   operator =(double  var) ;
  void   increase ();
  void decrease();
   Variable(string , double );
    ~Variable();
    double calculate();
};
class UnaryOperator: public Expression {
public:
    UnaryOperator(Expression *exp);
    virtual ~UnaryOperator();
    Expression *exp;
};

class UPlus: public UnaryOperator {
public:
    UPlus(Expression *exp);

    double calculate();

    virtual ~UPlus();
};
class UMinus: public UnaryOperator {
public:
    UMinus(Expression *exp);

    double calculate();

    virtual ~UMinus();
};
#endif //PROJECT_EXPRESSION_H
