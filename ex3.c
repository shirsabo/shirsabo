#include <stdio.h>
#include <stdlib.h>
#include "string.h"
#include <ctype.h>
#include <stdbool.h>
int  iterateLine(char* line);
bool isDigit (char c);
        int main() {
    FILE *f;
    ssize_t read;
    size_t lenght = 0;
    char * line = NULL;
    f= fopen("switch.c", "r");
    if (f ==NULL){
        printf("error opening file\n");
       // exit(1);
    }
    printf("___________________________\n");
    long num ;

    while ((read = getline(&line, &lenght, f)) != -1){
       if(strstr(line,"case ")) {
          num= iterateLine(line);

          printf("%d\n",num);
       }
    }
    fclose(f);
    return 0;
}
int iterateLine(char * line) {
    char * t; // first copy the pointer to not change the original
    long mult =1;
    long sum = 0;
    int flagMinus=0;
    int i=0;
    int size=2;
    char* number = (char *)malloc(2 * sizeof(char));
    for (; *line!= '\0';line++) {
        char c = *line;
        if (*line =="-"){
            flagMinus=1;
        }
        if (isDigit(*line)){
            i++;
            if(i==1){
               number[0] = *line;
            }
            else{
                number = (char *)realloc(number,(size + 1 )* sizeof(char));
                number[i-1]= *line;
            }
            size++;
        }
    }
    return atoi(number);
}
bool isDigit (char c) {
    if (isalpha(c)) {
        return false;
    } else if (isdigit(c)) {
        return true;
    } else {
        return false;
    }
}
