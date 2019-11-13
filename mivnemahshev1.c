#include <stdlib.h>
#include <stdio.h>
#include "bytes.h"
#include <string.h>
#include <math.h>
static int count = 0;
byte_t *create_bytes(char *file_name)
{
    FILE * fp;
    char c1;
    int i=0;
    fp = fopen(file_name,"r+");
    FILE* temp=fp;
    if (fp){
        for (c1 = getc(fp); c1 != EOF; c1 = getc(fp)){
            if (c1 == '\n') {
                count = count + 1;
                printf("%d\n",count);
            }// Increment count if this character is newline
        }
        rewind(fp);
        int i=0;
        int j=0;
        int c;
        byte_t * students= malloc(count*sizeof(byte_t));
        for (j;j<count;j++) {
            byte_t mask = 0;
            i=0;
            for (i = 0, c1 = getc(fp); c1 -'\n'!=0; c1 = getc(fp), i++) {
                if (c1-'\r'==0) {
                    continue;
                }
                if (c1==44){
                    --i;
                    continue;
                }
                c= c1-'0';
               mask = (mask |((c)<<2*i));
            }
            students[j]= mask;
        }
         i =0;
        fclose(fp);
        return students;
    }else{
        printf("error opening file\n");
        return NULL;

    }
}
void print_bytes(byte_t *byte_array, FILE* out)
{
    if(out){
        int i=0;
        for(i;i<count;i++){
            byte_t x= byte_array[i];
            fprintf(out,"%02x",byte_array[i]);
        }
        fprintf(out,"\n");
    }
    else{
        printf("error!");
    }
}
void set_stud(byte_t *byte_array, int i, int j, int k)
{
 byte_t x=byte_array[i-1];
 byte_t mask=255;
 switch(j){
     case 1:
         mask=mask<<2;
         byte_array[i-1]=(byte_array[i-1])&mask;
         byte_array[i-1]=byte_array[i-1]|k;
         break;
         //shifts on all cases 2-4
     case 2:
         mask=243;
         byte_array[i-1]=(byte_array[i-1])&mask;
         byte_array[i-1]=byte_array[i-1]|(k<<2);
         break;
     case 3:
         mask=207;
         byte_array[i-1]=(byte_array[i-1])&mask;
         byte_array[i-1]=byte_array[i-1]|(k<<4);
         break;
     case 4:
         mask=63;
         byte_array[i-1]=(byte_array[i-1])&mask;
         byte_array[i-1]=byte_array[i-1]|(k<<6);
         break;
 }


}
float average_stud(byte_t *byte_array, int i)
{
    int n=0;
    int j=0;
    byte_t mask = 0x3;
    byte_t sum = 0;
    byte_t tempX=byte_array[i-1];
    for(j;j<4;j++){
        mask= mask;
       sum += (tempX)&mask;
        tempX=tempX>>2;
    }
    int sum1=0;
    int x=1;
    byte_t mask1=0X1;
    n=0;
    for(n;n<4;n++,x=2*x){
        sum1+=(sum&mask1)*x;
       sum= sum>>1;
    }
    return (float)sum1/(float)4;
}
float average_ans(byte_t *byte_array, int j)
{
    int i=0;
    byte_t mask0 = 0x3;
    byte_t sum = 0;
    for(;i<count;i++) {
        byte_t  x =byte_array[i];
        switch (j) {
            case 1:
                sum+=mask0&x;
                break;
                //shifts on all cases 2-4
            case 2:x=x>>2;
                sum+=mask0&x;
                break;
            case 3:x=x>>4;
               sum+=mask0&x;
                break;
            case 4:x=x>>6;
                sum+=mask0&x;
                break;
        }
    }
    byte_t maskOne=0X1;
    int sumInt=0;
    int k=1;
    for (i=0;i<8;i++,k=2*k){
        sumInt+=(sum&maskOne)*k;
        sum= sum>>1;
    }
    return (float)sumInt/(float)count;
}
