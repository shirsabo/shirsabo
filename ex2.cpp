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
using namespace std;
template <class type> class node {
public:
    string key;
    type obj;
    node<type>* prev;
    node<type>* next;
    node (string keyIn,type objIn) {
        this->key= keyIn;
        this->obj = objIn;
    }
    node (){
    }
};
template <class type> class CacheManager {
private:
    int capacity;
    int totalItemsInCache;
    FILE* disk;
    node <type*>* head;
    node <type*>* tail;
    unordered_map< string, node<type*>> mp;
public:
     void writeToFile(type obj,string key) {
        // Object to write in file
        ofstream file_obj;
        // Opening file in append mode
        file_obj.open(obj.class_name +key+".txt", ios::app);
        // Writing the object's data in file
        file_obj.write((char*)&obj, sizeof(obj));
        file_obj.close();
    }
  type readToFile() {
        ifstream file_obj;
        type* objPtr = new type();
        type obj = *objPtr;
        // Opening file in input mode
        file_obj.open(obj.class_name+".txt", ios::in);
        // Object of class contestant to input data in file
        // Reading from file into object "obj"
        file_obj.read((char*)&(obj), sizeof(obj));
        file_obj.close();
        return obj;
    }
    void insert(string key, type obj) {
        type* objIn = new type();
        *objIn = obj;
        writeToFile(*objIn,key);
        type* objTemp =  new type();
        *objTemp =readToFile();
        //node <type*> nodeIn= new ::node<type*>(key,new type(obj));
        node<type*>* nodeIn = new ::node<type*>(key,objIn);
        auto search = this->mp.find(key);
        if ( search != this->mp.end()) {
           node<type*>* temp =  &(search->second);
           ((search->second).obj)=  objIn;
         moveToHead(nodeIn,temp);
            return;
        }
        addToFront(nodeIn);
        put(key,*objIn,nodeIn);
    }
    void addToFront(node<type*>* nodeIn) {
        // Wire up the new node being to be inserted
        if(this->capacity!=this->totalItemsInCache) {
            nodeIn->prev = head;
            nodeIn->next = head->next;
            auto search = this->mp.find(nodeIn->key);
            if ( search != this->mp.end()) {
                (search->second).prev= head;
                (search->second).next= head->next;
            }
            node<type*>* temp =head->next;
            (head->next)->prev = nodeIn;
            head->next = nodeIn;
            search = this->mp.find((temp)->key);
            if ( search != this->mp.end()) {
                (search->second).prev = nodeIn;
            }
            search = this->mp.find(head->key);
            if ( search != this->mp.end()) {
                (search->second).next=nodeIn;
            }
            this->totalItemsInCache++;
        }else {
            node<type*>* temp = tail->prev;
            removeFromList(temp);
            mp.erase(temp->key);
            addToFront(nodeIn);
        }
    }
    void removeFromList(node<type*>* nodeIn) {
        node<type*>* savedPrev = nodeIn->prev;
        node<type*>* savedNext = nodeIn->next;
        savedPrev->next = savedNext;
        savedNext->prev = savedPrev;
        cout<<savedPrev->key;
        auto search = this->mp.find(savedPrev->key);
        if ( search != this->mp.end()) {
            (search->second).next= savedNext;
        }
        search = this->mp.find(savedNext->key);
        if ( search != this->mp.end()) {
            (search->second).prev= savedPrev;
        }
       -- this->totalItemsInCache;
    }
    void moveToHead(node<type*> * nodeIn,node<type*>*temp) {
        removeFromList(temp);
        addToFront(nodeIn);
    }
    type get(string key) {
        auto search = mp.find(key);
        if ( search == mp.end()) {
            throw " not found"; // we should throw an exception here, but for Leetcode's sake
        }
        // Item has been accessed. Move to the front of the cache
        moveToHead(&(search->second),&(search->second));
        return (*((search -> second).obj));
    }
    //void foreach(for you to figure out);
    CacheManager(int capacityIn){
        this->capacity = capacityIn;
        this->totalItemsInCache= 0;
        // Cache starts empty and capacity is set by client
        // Dummy head and tail nodes to avoid empty states
        head = new node<type*>();
        tail = new node<type*>();
        head->key="HEAD";
        tail->key="TAIL";
        // Wire the head and tail together
        head->next = tail;
        tail->prev = head;
        mp.insert({"HEAD",*(head)});
        mp.insert({"TAIL",*(tail)});
    }
    void put(string keyIn, type obj,node<type*>* node1) {
        auto search = mp.find(keyIn);
        if ( search== mp.end()) {
            mp.insert({keyIn,*(node1)});
            // If over capacity remove the LRU item
            if (totalItemsInCache > capacity) {
                cout<<"overload"<<endl;
            }
        } else {
            // If item is found in the cache, just update it and move it to the head of the list
        }
    }
};
#endif //EX2_EX2_H
