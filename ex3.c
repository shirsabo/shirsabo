#include <stdio.h>
#include <stdlib.h>
#include "string.h"
#include <ctype.h>
#include <stdbool.h>
int  iterateLine(char* line);
bool isDigit (char c);
long* maxAndMin(long * numbers,int sizeOfNumbers,long* minMax );
void printCommand(char* command,FILE* ptr);
int deffineSign(char* sign,FILE* ptr);
void cutString(char* command,char*sign,FILE* ptr,int i);
void copy_string(char d[], char s[]) ;
void printToFile(char* src,char*des,char* sign,FILE* ptr,int i);
char* deffineString(char*s);
char* longtostring(char* line);
int time =0;
        int main() {
    FILE *f;
    ssize_t read;
    size_t lenght = 0;
    long minMax[2];
    char * line = NULL;
    int sizeOfnumbers =1;
    f= fopen("switch.c", "r");
    if (f ==NULL){
        printf("error opening file\n");
       // exit(1);
    }
    printf("File opened\n");
    FILE* ptr = fopen("switch.s","w+");
    if(ptr) {
        fputs(".section .text\n",ptr);
        fputs(".globl switch2\n",ptr);
        fputs("switch2:\n",ptr);
        fputs("movl $0,%eax\n", ptr);
    }else{
        printf("error creating switch.s\n");
    }
    long num ;
    int index = 0;
    long * numbers = (long*)malloc(2 * sizeof(long));
    int t = sizeof(numbers)/ sizeof(numbers[0]);
  for(time;time<2;time++) {
      if (time==1) {
              fprintf(ptr, "movq $%d%s%s\n", minMax[0],",","%rdx");
              fprintf(ptr, "cmpq $%d%s%s\n", minMax[1]-minMax[0],",","%rdx");
      }
      while ((read = getline(&line, &lenght, f)) != -1) {
         // printf("%s\n", line);
         if(strstr(line, "default")){
             if(time==1){
                 fprintf(ptr, ".Ldefualt :\n");
                 printCommand(line, ptr);
             }
         }
          if (strstr(line, "case ")) {
              num = iterateLine(line);
              if (sizeOfnumbers == 1) {
                  numbers[0] = num;
              } else {
                  numbers = (long *) realloc(numbers, (sizeOfnumbers) * sizeof(long));
                  numbers[sizeOfnumbers - 1] = num;
              }
              if(time==1){
                  fprintf(ptr, ".L%d :\n", num);
              }
              sizeOfnumbers++;

          }
          if ((strstr(line, "=") && !strstr(line, "long")) || strstr(line, "+=") || strstr(line, "-=") ||
              strstr(line, "*=") || strstr(line, "<<=") || strstr(line, ">>=")) {
              if(time==1){
                  printCommand(line, ptr);
              }
          }
          //printf("%s\n",line);
      }
      sizeOfnumbers--;
      if(time==0){
          long *minMaxValues = maxAndMin(numbers, sizeOfnumbers, minMax);
          printf("min and max\n");
          printf("%d\n", minMaxValues[0]);
          printf("%d\n", minMaxValues[1]);
          printf("---------------\n");
          fclose(f);
          f = fopen("switch.c","r");
      }

  }
    fclose(f);
    return 0;
}
int iterateLine(char * line) {
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
                number = (char *)realloc(number,(size ) * sizeof(char));
                printf("hereeeeeeeeeeeeeeeeeeeee");
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
long* maxAndMin(long * numbers,int sizeOfNumbers,long * minMx ){
    printf("---------------\n");
    printf("Elements in array\n");
    int i=0;
    long min,max;
    for (i;i<sizeOfNumbers;i++) {
        if(i==0){
            max= numbers[0];
            min= numbers[0];
        }
        else{
            if (numbers[i]< min) {
                min = numbers[i];
            }else if(numbers[i]> max){
                max = numbers[i];
            }
        }
        printf("%d\n", numbers[i]);
    }
    printf("---------------\n");
    minMx[0]=min;
    minMx[1]=max;
    return minMx;
        }
void printCommand(char* command,FILE* ptr) {
    int signType = deffineSign(command,ptr);


}
int deffineSign(char* sign,FILE* ptr) {
    char *sign1 = "=";
    char *sign2 = "+=";
    char *sign3 = "-=";
    char *sign4 = "*=";
    char *sign5 = "<<=";
    char *sign6 = ">>=";
    if (strstr(sign, sign2)) {
        printf("sign: 2\n");
      cutString(sign,sign2,ptr,2);
        return 2;
    } else if (strstr(sign, sign3)) {
       printf("sign:3\n");
     cutString(sign,sign3,ptr,3);
        return 3;
    } else if (strstr(sign, sign4)) {
        printf("sign:4\n");
      cutString(sign,sign4,ptr,4);
        return 4;
    } else if (strstr(sign, sign5)) {
        printf("sign:5\n");
      cutString(sign,sign5,ptr,5);
        return 5;
    } else if (strstr(sign, sign6)) {
        printf("sign:6\n");
      cutString(sign,sign6,ptr,6);
        return 6;
    } else if (strstr(sign, sign1)) {
        printf("sign: 1\n");
        cutString(sign,sign1,ptr,1);
        return 1;
    } else {
        printf("sign not found!]\n");
    }
}

void cutString(char* command,char*sign,FILE* ptr,int i) {

// split by space or ';'
    char *copy = malloc(strlen(command) + 1);
    copy_string(copy, command);
    int counter = 0;
// split by space or ';'
    char* token = strtok(copy,  sign);
    char *des = malloc(strlen(token) + 1);
    copy_string(des, token);
    char* src;
    //printToFile(token,sign,ptr,i);
        printf("%s\n", token);
        token = strtok(NULL,  sign);
       src= (char*)malloc(strlen(token) + 1);
        copy_string(src, token);
        printToFile(src,des,sign,ptr,i);
}
void copy_string(char d[], char s[]) {
    int c = 0;
    while (s[c] != '\0') {
        d[c] = s[c];
        c++;
    }
    d[c] = '\0';
}
void printToFile(char* src,char*des,char* sign,FILE* ptr,int i) {
char *src1 = deffineString(src);
char* des1 = deffineString(des);
    char *sign1 = "=";
    char *sign2 = "+=";
    char *sign3 = "-=";
    char *sign4 = "*=";
    char *sign5 = "<<=";
    char *sign6 = ">>=";
    if (strstr(sign, sign2)) {
        fprintf(ptr, "addq %s%s%s\n",src1,",",des1);

    } else if (strstr(sign, sign3)) {
        fprintf(ptr, "subq %s%s%s\n",src1, ",",des1);

    } else if (strstr(sign, sign4)) {
        fprintf(ptr, "imulq %s%s%s\n",src1,",",des1);

    } else if (strstr(sign, sign5)) {
        fprintf(ptr, "salq %s%s%s\n",src1,",",des1);

    } else if (strstr(sign, sign6)) {
        fprintf(ptr, "sarq %s%s%s\n",src1,",",des1);

    } else if (strstr(sign, sign1)) {
        fprintf(ptr, "movq %s%s%s\n",src1,",",des1);

    } else {
        printf("sign not found!]\n");
    }
}
char* deffineString(char*s) {
    char *sCopy;
    if (strstr(s, "*p1")) {
        sCopy = (char *) malloc(strlen("(%rbx)") + 1);
        copy_string(sCopy,"(%rbx)");

    } else if (strstr(s, "*p2")) {
        sCopy = (char *) malloc(strlen("(%rcx)") + 1);
        copy_string(sCopy,"(%rcx)");

    } else if (strstr(s, "result")) {
        sCopy = (char *) malloc(strlen("%rax") + 1);
        copy_string(sCopy,"%rax");

    } else {
        return longtostring(s);
    }
    return sCopy;
}
char* longtostring(char* line) {
    int flagMinus = 0;
    int i = 0;
    int size = 3;
    char *number = (char *) malloc(3 * sizeof(char));
    for (; *line != '\0'; line++) {
        char c = *line;
        if (*line == "-") {
            flagMinus = 1;
        }
        if (isDigit(*line)) {
            i++;
            if (i == 1) {
                number[0]='$';
                number[1] = *line;
            } else {
                number = (char *) realloc(number, (size) * sizeof(char));
                number[i] = *line;
            }
            size++;
        }
    }
    return number;
}
