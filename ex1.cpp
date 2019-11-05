//
// Created by osboxes on 11/4/19.
//
#include "ex1.h"
//constructors--------------------------------------------
BinaryOperator::BinaryOperator(Expression *right, Expression *left) : right(right), left(left) {
}

Plus::Plus(Expression *right, Expression *left) : BinaryOperator(right, left) {}

Minus::Minus(Expression *right, Expression *left) : BinaryOperator(right, left) {}

Mul::Mul(Expression *right, Expression *left) : BinaryOperator(right, left) {}

Div::Div(Expression *right, Expression *left) : BinaryOperator(right, left) {}

UnaryOperator::UnaryOperator(Expression *exp) : exp(exp) {}

UPlus::UPlus(Expression *exp) : UnaryOperator(exp) {}

UMinus::UMinus(Expression *exp) : UnaryOperator(exp) {}
//calculate()-------------------------------------------------
double Plus:: calculate() {
    return left->calculate()+right->calculate();
};
double Mul:: calculate() {
    return left->calculate()*right->calculate();
};
double Div:: calculate() {
    double leftRes= left->calculate();
    double righttRes= right->calculate();
    if ( righttRes==0) {
        cout<<"divide by zero"<<endl;
    }
    return leftRes /righttRes;
};
double Minus:: calculate() {
    return left->calculate()-right->calculate();
};
double Value:: calculate() {
    return val;
};
double Variable::calculate() {
    return value;
};
double UMinus:: calculate() {
    return  -1 * exp->calculate();
};
double UPlus:: calculate() {
    return  1 * exp->calculate();
};
//Variable---------------------------------
 Variable Variable:: operator ++() {
     this->increase();
     return  *this;
};
 void Variable::increase() {
     this->value++;
 };
void Variable::decrease()  {
    this->value--;
};
Variable Variable:: operator--() {
    this->decrease();
    return *this;
};
Variable Variable:: operator +=(double val) {
    this->value+= val;
    return *this;
};
Variable Variable:: operator =(double val) {
    this->value = val;
    return *this;
};
Variable::Variable( string s, double val) {
    this->name = s;
    this->value= val;
};
BinaryOperator::~BinaryOperator() {

}

Plus::~Plus() {

}

Minus::~Minus() {

}

Mul::~Mul() {

}

Div::~Div() {

}

Value::~Value() {

}

UnaryOperator::~UnaryOperator() {

}

UPlus::~UPlus() {

}

UMinus::~UMinus() {

}
Variable::~Variable() {
}

//-------------------------------------------
