//
// Created by osboxes on 11/4/19.
//
#include "ex1.h"
#include<iostream>
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
    double left =this->getLeft()->calculate();
    double right=this->getRight()->calculate();
    return left+right;
};
double Mul:: calculate() {
    double left =this->getLeft()->calculate();
    double right=this->getRight()->calculate();
    return this->getLeft()->calculate()* this->getRight()->calculate();
};
double Div:: calculate() {
    double leftRes= this->getLeft()->calculate();
    double righttRes= this->getRight()->calculate();
    if (righttRes==0) {
        throw "Division by zero condition!";
    }
    return leftRes /righttRes;
};
double Minus:: calculate() {
    double left =this->getLeft()->calculate();
    double right=this->getRight()->calculate();
    return left-right;
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
    return this->getExp()->calculate();
};
//Variable---------------------------------
Expression& Variable:: operator ++() {
     this->increase();
     return  *this;
};
Expression& Variable:: operator ++(int n) {
    Expression& e = *(new Variable(this->name,++this->value));
    return  e;
};
Expression& Variable:: operator --(int n) {
    Expression& e = *(new Variable(this->name,--this->value));
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
delete this->right;
delete this->left;
}
Plus::~Plus() {
}
Minus::~Minus() {
}
Mul::~Mul() {
}
Div::~Div() {
}
Value::~Value(){
}
UnaryOperator::~UnaryOperator() {
delete this->exp;
}
UPlus::~UPlus() {
}
UMinus::~UMinus() {
}
Variable::~Variable() {
}
void  Interpreter::setVariables(string s) {
    string buffer = s;
    string delimiter = ";";
    size_t pos = 0;
    string token;
    while ((pos = s.find(delimiter)) != std::string::npos) {
        token = s.substr(0, pos);
        // Check if element 22 exists in vector
        this->g1.push_back(token);
        s.erase(0, pos + delimiter.length());
    }
    this->g1.push_back(s);
    this->insertMap();
    varsCreator();
}
void  Interpreter::setPlacement(string s) {
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
    for (std::map<string,string>::iterator it=vars.begin(); it!=vars.end(); ++it){
        if(it->first == token){
            it->second =s;
            return;
        }
    }
    right=s;
    if(isnumber(s)){
        this->vars.insert(std::pair<string,string>(left,right));
    }
    else{
        throw "Error with giving the value!";
        return;
    }
}
void  Interpreter::insertMap(){
    for(std::size_t i=0; i<g1.size(); ++i)
        setPlacement(g1[i]);
}
bool  Interpreter::isnumber(string str) {
    int flagpoint=  0;
    int flagMinus=0;
    int x;
    for(int i=0;i<str.length();i++) {
        x = int(str[i]);
        if(i==0 && str[i] == '-'){
            flagMinus=1;
            continue;
        }
        if(i>0&&str[i] == '-') {
            return false;
        }
        if (x >= 48 && x <= 57) {
            continue;
        } else if ((i > 0) &&  str[i] == '.' && flagpoint == 0) {
             flagpoint = 1;
            continue;
        } else {
            return false;
        }
    }
    return true;
}
map<string,string> Interpreter::getVars() {
    return this->vars;
}
void  Interpreter::varsCreator() {
    map<string, string>::iterator it;
    it=this->getVars().begin();
    for (int j= 0; j<this->vars.size(); j++,it++)
    {
        double value = stod(it->second);
        this->variables.push_back(*(new Variable(it->first,value)));
    }
}
Expression*  Interpreter::interpret(string str) {
        str = checkUnary(str);
        int size = str.size();
        int check = 0;
        for (int i = 0; i < size; i++) {
            string buffer;
            int flag = 0;
            char c = str[i];
            char c1;
            std::string s(1, c);
            check = checkChar(c);
            switch (check) {
                //Alphabetic
                case 1 :
                    buffer.push_back(c);
                    i++;
                    while ((i < size) && (checkChar(str[i]) != 3 && (str[i] != '(' && str[i] != ')'))) {
                        buffer.push_back(str[i]);
                        i++;
                    }
                    i--;
                    if (vars.find(buffer) == vars.end()) {
                        throw "variable not found";
                        return nullptr;
                    }//then its really a var!
                    queueOfStrokes.push(buffer);
                    break;
                    //number
                case 2 :
                    buffer.push_back(c);
                    i++;
                    while (flag <= 1 && (i < size) && (checkChar(str[i]) != 3 && str[i] != '(' && str[i] != ')')) {
                        if (str[i] == '.') {
                            flag++;
                        }
                        buffer.push_back(str[i]);
                        i++;
                    }
                    if (flag > 1) {
                        throw "Error with the given number!";
                    }
                    i--;
                    queueOfStrokes.push(buffer);
                    break;
                    //Binary operator
                case 3:
                    if (s == "(") {
                        stackOfStrokes.push(s);
                        break;

                    } else if (s == ")") {
                        while (!stackOfStrokes.empty() && stackOfStrokes.top() != "(") {
                            queueOfStrokes.push(stackOfStrokes.top());
                            stackOfStrokes.pop();
                        }
                        if (!stackOfStrokes.empty() && ((stackOfStrokes.top() == "("))) {
                            stackOfStrokes.pop();
                            break;
                        }
                        queueOfStrokes.push(stackOfStrokes.top());
                        stackOfStrokes.pop();
                        break;
                    }
                    if ((!stackOfStrokes.empty()) && (stackOfStrokes.top() == "(")) {
                        stackOfStrokes.push(s);
                        break;
                    }
                    //if the current is higher precedence than top
                    if ((!stackOfStrokes.empty()) && comparePrecedence(s, stackOfStrokes.top())) {
                        stackOfStrokes.push(s);
                        break;
                    }
                    if (!stackOfStrokes.empty()) {
                        queueOfStrokes.push(stackOfStrokes.top());
                        stackOfStrokes.pop();
                        stackOfStrokes.push(s);
                        break;
                    } else {
                        stackOfStrokes.push(s);
                    }
                    break;
                case 4:
                    if ((!stackOfStrokes.empty()) && comparePrecedence(s, stackOfStrokes.top())) {
                        stackOfStrokes.push(s);
                        break;
                    }
                    if (!stackOfStrokes.empty()) {
                        queueOfStrokes.push(stackOfStrokes.top());
                        stackOfStrokes.pop();
                        stackOfStrokes.push(s);
                        break;
                    } else {
                        stackOfStrokes.push(s);
                    }
                    break;
                default:
                    throw "error with prefix!";
            }
        }
        while (!stackOfStrokes.empty()) {
            queueOfStrokes.push(stackOfStrokes.top());
            stackOfStrokes.pop();
        }
        node *t = buildTree(this->queueOfStrokes);
        return readTreePreorder(t);
}
int Interpreter::checkChar(char c) {
    if(c==36||c==38){
        return 4;
    }
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
int  Interpreter::precedence(string s)  {
    //unarry operator!
    if(s=="+"||s=="-"){
        return 1;
    }
    if(s=="*"||s=="/"){
        return 2;
    }
    if(s=="$"||s=="&"){
        return 3;
    }
    else{
        return 0;
    }
}
bool  Interpreter::comparePrecedence(string current,string top){
    if(!stackOfStrokes.empty()){
        return precedence(top) < precedence(current);
    }
    return true;
}
string  Interpreter::checkUnary(string s) {
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
                if (*it==40||*it=='/'||*it=='*'){
                    it++;
                    if (*it=='-'){
                        *it='$';
                        continue;
                    }
                    *it='&';
                    continue;
                }
                else{
                    it++;
                    continue;
                }
            }
        }
    }
    return s;
}
node*  Interpreter::buildTree(queue<string>q) {
    node * node0, *node1, *node2;
    stack<node *> st;
    int temp = q.size();
    for (int i=0; i< temp; i++)
    {
        // If operand, push into stack
        string s = q.front();
        q.pop();
        char c= s[0];
        int res=checkChar(c);
        if (res==1||res==2)
        {
            node0 = buildNode(s);
            st.push(node0);
        }
        else // operator
        {
            if(res==4){
                node0 = buildNode(s);
                node1 = st.top();
                st.pop();
                node0->left = node1;
                node0->right = NULL;
                st.push(node0);
                continue;
            }
            node0 = buildNode(s);
            // Pop two top nodes
            if(!st.empty()) {
                node1 = st.top();
                st.pop();
                if(!st.empty()) {
                    node2 = st.top();
                    st.pop();
                }else{
                    throw "bad input";
                }
                //  make the 2 nodes children of node0
                node0->right = node1;
                node0->left = node2;
            }else{
                throw "bad input";
            }
            // Add this subexpression to stack
            st.push(node0);
        }
    }
    //  only element will be root of expression
    // tree
    node0 = st.top();
    st.pop();
    return node0;
}
node*  Interpreter::buildNode(string s){
    node *temp = new node;
    temp->left = temp->right = NULL;
    temp->value = s;
    return temp;
}
Expression*  Interpreter::readTreePreorder(node *n) {
    if (n) {
        if (n->left != NULL && n->right != NULL) {
            if (n->value == "*") {
                return new Mul((readTreePreorder(n->left)), readTreePreorder(n->right));
            }
            if (n->value == "/") {
                return new Div((readTreePreorder(n->left)), readTreePreorder(n->right));
            }
            if (n->value == "+") {
                return new Plus((readTreePreorder(n->left)), readTreePreorder(n->right));
            }
            if (n->value == "-") {
                return new Minus((readTreePreorder(n->left)), readTreePreorder(n->right));
            }
        } else {
            if (n->value == "$") {
                return new UMinus((readTreePreorder(n->left)));
            }
            if (n->value == "&") {
                return new UPlus((readTreePreorder(n->left)));
            } else {
                if (isnumber(n->value)) {
                    return new Value(stod(n->value));
                }
                std::map<std::string, string>::iterator it = vars.begin();
                // Iterate over the map using Iterator till end.
                while (it != vars.end()) {
                    if (it->first == n->value) {
                        return new Variable(it->first, stod(it->second));
                    }
                    it++;
                }
            }
        }
    }
}
//-------------------------------------------------------------------------
