//
// Created by osboxes on 11/4/19.
//
#ifndef EX1_H
#define EX1_H
#include <string>
#include <iostream>
#include "Expression.h"
#include<map>
#include <vector>

class BinaryOperator: public Expression {
public:
    BinaryOperator(Expression *right, Expression *left);
    virtual ~BinaryOperator();
    // right and left expressions
    Expression*getLeft();
    Expression*getRight();
private:
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
    Value(const double val);

    double calculate();
    virtual ~Value();
    double getVal();

private:
    const double val;
};
class Variable: public Expression {
public:
   Expression&  operator ++ () ;
   Expression& operator++(int);
    Expression& operator --() ;
    Expression&   operator +=(double  var) ;
    Expression&    operator =(double  var) ;
  void   increase ();
  void decrease();
   Variable(string , double );
    ~Variable();
    double calculate();
    string getName();
    double getVal();
private:
    string name;
    double value;
};
class UnaryOperator: public Expression {
public:
    UnaryOperator(Expression *exp);
    virtual ~UnaryOperator();
    Expression * getExp();
private:
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
class Interpeter {
public:

    void setVariables(const string);
    void setPlacement(string s);
    void insertMap();
private:
    map<string,string > vars;
    vector<string> g1;

};
#endif //PROJECT_EXPRESSION_H
