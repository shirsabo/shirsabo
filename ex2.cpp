//
// Created by osboxes on 11/21/19.
//
#ifndef EX2_EX2_H
#define EX2_EX2_H
#include <string>
#include <iostream>
#include <fstream>
#include <map>
#include <unordered_map>
#include <stdio.h>
using namespace std;
template <class type> class node {
public:
    string key;
    type *obj;
    node<type>* prev;
    node<type>* next;
    node (string keyIn,type *objIn) {
        this->key= keyIn;
        this->obj = objIn;
    }
    virtual ~node() {
        free(obj);
    }
    node (){
    }
};
template <class type> class CacheManager {
private:
    int capacity;
    int totalItemsInCache;
    node <type>* head;
    node <type>* tail;
    unordered_map<string, node<type>*> mp;
public:
    void writeToFile(type *obj,string key) {
        // Object to write in file
        ofstream file_obj;
        // Opening file in append mode
        file_obj.open(obj->class_name +key+".txt");
        file_obj.write((char*)obj, sizeof(type));

        file_obj.close();
    }
    type *readToFile(string key) {
        ifstream file_obj;
        type *objPtr = new type();
        // Opening file in input mode
        string s = objPtr->class_name+key;
        file_obj.open(s+".txt");
        file_obj.read((char*)objPtr, sizeof(type));
        file_obj.close();
        return objPtr;
    }
    void insert(string key, type obj) {
        type *objIn = new type(obj);
        insert(key, objIn);
    }
    void insert(string key, type *objIn) {
        writeToFile(objIn,key);
        //node <type*> nodeIn= new ::node<type>(key,new type(obj));
        node<type>* nodeIn = new ::node<type>(key,objIn);
        auto search = this->mp.find(key);
        if ( search != this->mp.end()) {
            node<type>* temp =  (search->second);
            ((search->second)->obj)=  objIn;
            moveToHead(nodeIn,temp);
            return;
        }
        addToFront(nodeIn);
        put(key,nodeIn);
    }
    void addToFront(node<type>* nodeIn) {
        // Wire up the new node being to be inserted
        if(this->capacity!=this->totalItemsInCache) {
            nodeIn->prev = head;
            nodeIn->next = head->next;
            auto search = this->mp.find(nodeIn->key);
            if ( search != this->mp.end()) {
                (search->second)->prev= head;
                (search->second)->next= head->next;
            }
            node<type>* temp =head->next;
            (head->next)->prev = nodeIn;
            head->next = nodeIn;
            search = this->mp.find((temp)->key);
            if ( search != this->mp.end()) {
                (search->second)->prev = nodeIn;
            }
            search = this->mp.find(head->key);
            if ( search != this->mp.end()) {
                (search->second)->next=nodeIn;
            }
            this->totalItemsInCache++;
        }else {
            node<type>* temp = tail->prev;
            removeFromList(temp);
            mp.erase(temp->key);
            delete temp;
            addToFront(nodeIn);
        }
    }
    void removeFromList(node<type>* nodeIn) {
        node<type>* savedPrev = nodeIn->prev;
        node<type>* savedNext = nodeIn->next;
        savedPrev->next = savedNext;
        savedNext->prev = savedPrev;
        auto search = this->mp.find(savedPrev->key);
        if ( search != this->mp.end()) {
            (search->second)->next= savedNext;
        }
        search = this->mp.find(savedNext->key);
        if ( search != this->mp.end()) {
            (search->second)->prev= savedPrev;
        }
        -- this->totalItemsInCache;
    }
    void moveToHead(node<type>* nodeIn,node<type>*temp) {
        removeFromList(temp);
        addToFront(nodeIn);
    }
    type get(string key) {
        //type* objIn;// = new type()
        type t;
        auto search = mp.find(key);
       // std::ifstream p (s+".txt");
        if ( search == mp.end()) {
           if(fopen(( t.class_name +key+".txt").c_str(),"r")) {
              type *objIn = readToFile(key);
              insert(key,objIn);
              return *objIn;
           }
            throw "key not exists both in cache and disk!"; // we should throw an exception here, but for Leetcode's sake
        }
        moveToHead((search->second),(search->second));
        return *(((search -> second)->obj));
    }
    template <typename Func> void foreach(Func f) {
        node<type> *tmp;
        tmp = head->next;
        while (tmp!=this->tail)
        {
           f(*(tmp->obj));
            tmp = tmp->next;
        }
    }
    CacheManager(int capacityIn){
        this->capacity = capacityIn;
        this->totalItemsInCache= 0;
        head = new node<type>();
        tail = new node<type>();
        head->key="HEAD";
        tail->key="TAIL";
        // Wire the head and tail together
        head->next = tail;
        tail->prev = head;
        mp.insert({"HEAD",(head)});
        mp.insert({"TAIL",(tail)});
    }
    void put(string keyIn,node<type>* node1) {
        mp[keyIn] = node1;
               // If over capacity remove the LRU item
            if (totalItemsInCache > capacity) {
                cout<<"overload"<<endl;
            }
    }
    virtual ~CacheManager() {
        node<type>* headIn = head;
        while(headIn->next){
            node<type>* temp = headIn->next;
            delete(headIn);
            headIn= temp;
        }
        delete (tail);
    }
};
#endif //EX2_EX2_H
