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
    void removeFromList(node<type*>* nodeIn) {
        node<type>* savedPrev = nodeIn->prev;
        node<type>* savedNext = nodeIn->next;
        savedPrev->next = savedNext;
        savedNext->prev = savedPrev;
    }
    void moveToHead(node<type*> * nodeIn) {
        removeFromList(nodeIn);
        addToFront(nodeIn);
    }
};
template <class type> class CacheManager {
private:
    int capacity;
    int totalItemsInCache;
    FILE* disk;
    node <type*>* head;
    node <type*>* tail;
    unordered_map<string, int> map;
public:
    void insert(string key, type obj) {
        node<type*>* nodeIn = new ::node<type*>(key,&obj);
        addToFront(nodeIn);
    }
    void addToFront(node<type*>* node) {
        // Wire up the new node being to be inserted
        node->prev = head;
        node->next = head->next;
        head->prev = node;
        head->next = node;
    }
    type get(string key) {
    }
    //void foreach(for you to figure out);
    CacheManager(int capacityIn){
        this->capacity = capacityIn;
        this->totalItemsInCache= 0;
        // Cache starts empty and capacity is set by client
        // Dummy head and tail nodes to avoid empty states
        head = new node<type*>();
        tail = new node<type*>();
        // Wire the head and tail together
        head->next = tail;
        tail->prev = head;
    }
    void put(string key, type obj) {
        if (map.find(key) == map.end()) {
            // Item not found, create a new entry
            node<type*> * newNode = new node<type*>(key,&obj);
            newNode->key = key;
            newNode->value =obj;
            // Add to the hashtable and the actual list that represents the cache
            map.insert(key,newNode);
            addToFront(newNode);
            totalItemsInCache++;
            // If over capacity remove the LRU item
            if (totalItemsInCache > capacity) {
                cout<<"overload"<<endl;
            }
        } else {
            // If item is found in the cache, just update it and move it to the head of the list

        }

    }
    /*
 type get(int key) {
        if (node == null) {
            return -1; // we should throw an exception here, but for Leetcode's sake
        }

        // Item has been accessed. Move to the front of the cache
        moveToHead(node);

        return node.value;
    }*/
};
#endif //EX2_EX2_H
