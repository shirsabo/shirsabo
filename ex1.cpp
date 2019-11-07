//
// Created by osboxes on 11/4/19.
//
#include "ex1.h"
#include<iostream>
#include <regex>

//constructors--------------------------------------------
BinaryOperator::BinaryOperator(Expression *left, Expression *right) : right(right), left(left) {
}

Plus::Plus(Expression *right, Expression *left) : BinaryOperator(right, left) {}

Minus::Minus(Expression *right, Expression *left) : BinaryOperator(right, left) {}

Mul::Mul(Expression *right, Expression *left) : BinaryOperator(right, left) {}

Div::Div(Expression *right, Expression *left) : BinaryOperator(right, left) {}

UnaryOperator::UnaryOperator(Expression *exp) : exp(exp) {}

UPlus::UPlus(Expression *exp) : UnaryOperator(exp) {}

UMinus::UMinus(Expression *exp) : UnaryOperator(exp) {}
Variable::Variable( string s, double val) {
    this->name = s;
    this->value= val;
};

Value::Value(const double val) : val(val) {}

//calculate()-------------------------------------------------
double Plus:: calculate() {
    return this->getLeft()->calculate()+ this->getRight()->calculate();
};
double Mul:: calculate() {
    return this->getLeft()->calculate()* this->getRight()->calculate();
};
double Div:: calculate() {
    double leftRes= this->getLeft()->calculate();
    double righttRes= this->getRight()->calculate();
    if ( righttRes==0) {
        cout<<"divide by zero"<<endl;
    }
    return leftRes /righttRes;
};
double Minus:: calculate() {
    this->getLeft()->calculate()-this->getRight()->calculate();
};
double Value:: calculate() {
    return val;
};
double Variable::calculate() {
    return value;
};
double UMinus:: calculate() {
    return  -1 *this->getExp()->calculate();
};
double UPlus:: calculate() {
    return  1 * this->getExp()->calculate();
};
//Variable---------------------------------
Expression& Variable:: operator ++() {
     this->increase();
     return  *this;
};
Expression& Variable:: operator ++(int n) {
    Expression& e = *(new Variable(this->name,this->value++));
    return  e;
};
Expression& Variable:: operator--() {
    this->decrease();
    return *this;
};
Expression& Variable:: operator +=(double val) {
    this->value+= val;
    return *this;
};
Expression& Variable:: operator =(double val) {
    this->value = val;
    return *this;
};
void Variable::increase() {
    this->value++;
};
void Variable::decrease()  {
    this->value--;
};
Expression* BinaryOperator::getLeft() { return left;}
Expression* BinaryOperator::getRight() { return right;}
double Value::getVal() { return val;}
string Variable::getName() { return name;}
double  Variable::getVal() { return value;}
Expression* UnaryOperator::getExp() { return exp;}
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
void Interpeter::setVariables(string s) {
    string buffer = s;
    string delimiter = ";";
    size_t pos = 0;
    string token;
    while ((pos = s.find(delimiter)) != std::string::npos) {
        token = s.substr(0, pos);
        this->g1.push_back(token);
        std::cout << token << std::endl;
        s.erase(0, pos + delimiter.length());
    }
    this->g1.push_back(s);
    this->insertMap();
    varsCreator();
}
void Interpeter::setPlacement(string s) {
    string left;
    string right;
    string buffer = s;
    string delimiter = "=";
    size_t pos = 0;
    string token;
    while ((pos = s.find(delimiter)) != std::string::npos) {
        token = s.substr(0, pos);
        left=token;
        s.erase(0, pos + delimiter.length());
    }
    right=s;
    if(isnumber(s)){
        this->vars.insert(std::pair<string,string>(left,right));

    }
    else{
        cout<<"error giving value!"<<endl;
        return;
    }

}
void Interpeter::insertMap(){
    for(std::size_t i=0; i<g1.size(); ++i)
        setPlacement(g1[i]);
}
bool Interpeter::isnumber(string str) {
    int flag=  0;
    int x = int(str[0]);
    for(int i=0;i<str.length();i++)
        if(x>=48&&x<=57){
            continue;
        }else  if ((i>0)&&(const char*)str[i]=="."&&flag==0)
                int flag = 1;
            else{
                cout<<"false"<<endl;
                return false;
            }

    cout<<"true"<<endl;
    return true;
}
map<string,string>Interpeter::getVars() {
    return this->vars;
}
void Interpeter::varsCreator() {
    map<string, string>::iterator it;
    it=this->getVars().begin();
    for (int j= 0; j<this->vars.size(); j++,it++)
    {
        double value = stod(it->second);
        cout<<value<<endl;
        this->variables.push_back(*(new Variable(it->first,value)));
    }
}
Expression* Interpeter::interpret(string str) {
int size= str.size();
int check=0;
for(int i=0;i<size;i++) {
   char c = str[i];
   std::string s(1,c);
  check = checkChar(c);
    switch(check) {
        //Alphabetic
        case 1 :
            if (vars.find(s)==vars.end()){
                cout<<"variable not found"<<endl;
                return nullptr;
            }//then its really a var!
            queueOfStrokes.push(s);
            break;
            //number
        case 2 :
            queueOfStrokes.push(s);
            break;
            //operator
        case 3:
            if(s=="("){
                stackOfStrokes.push(s);
                break;

            }else if(s==")"){
                while(!stackOfStrokes.empty()&&stackOfStrokes.top()!="("){
                    queueOfStrokes.push(stackOfStrokes.top());
                    stackOfStrokes.pop();
                }
                if(!stackOfStrokes.empty()&&((stackOfStrokes.top()=="("))){
                    stackOfStrokes.pop();
                    break;
                }
                queueOfStrokes.push(stackOfStrokes.top());
                stackOfStrokes.pop();
                break;
            }
            if(stackOfStrokes.top()=="("){
                stackOfStrokes.push(s);
                break;
            }
            //if the current is higher precedence than top
            if(precedence(s)){
                stackOfStrokes.push(s);
                break;
            }
            if(!stackOfStrokes.empty()){
                queueOfStrokes.push(stackOfStrokes.top());
                stackOfStrokes.pop();
                stackOfStrokes.push(s);
                break;
            }else {
               stackOfStrokes.push(s) ;
            }
           break;
        default:cout<<"error with prefix"<<endl;
    }

}
    while(!stackOfStrokes.empty()){
        queueOfStrokes.push(stackOfStrokes.top());
        stackOfStrokes.pop();
    }
    while(!queueOfStrokes.empty()){
        cout<<queueOfStrokes.front();
        queueOfStrokes.pop();
    }
    cout<<" "<<endl;
}
int Interpeter::checkChar(char c) {
    // CHECKING FOR ALPHABET
    if ((c >= 65 && c <= 90)
        || (c >= 97 && c <= 122)) {
        return 1;
    }else if (c >= 48 && c <= 57){
        return 2;
    }
    // operator
    else if((c!=44&&c!=46) && c >=40 && c <=47) {
        return 3;
    }

}
int Interpeter::precedence(string s)  {
    if (s[0] == '*' || s[0] == '/') return 0;
    return 1;
}
bool Interpeter::comparePrecedence(string current,string top){
    return precedence(top) < precedence(current);
}
bool Interpeter::checkUnary(string s) {
    int i=0;
    string::iterator it ;
    for (it=s.begin();it!=s.end();it++,i++) {
        if ((*it =='-')||(*it=='+')) {
            if (it==s.begin()){
                if (*it=='-'){
                    *it='$';
                    continue;
                }
                *it='&';
                continue;

            }
            else {
                it--;
                if (*it==40){
                    it++;
                    if (*it=='-'){
                        cout<<"unary minus works!"<<endl;
                        *it='$';
                        cout<<s<<endl;
                        continue;
                    }
                    *it='&';
                    cout<<"unary plus works!"<<endl;
                    continue;
                }
                else{
                    continue;
                }
            }
        }
    }

   //to do
}
//-------------------------------------------------------------------------
