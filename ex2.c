#include <stdlib.h>
#include <stdio.h>
#include <stdbool.h>
//
// Created by osboxes on 12/2/19.
//
void checkCases(int k,FILE* ptr);
bool powerOfTwo(int k);
int checkExponent(int k);
int  multNotPow2(int m,int n,FILE* ptr,int flag,int flagRightBit);
void  multPow2(int n,FILE* ptr,int flag);
int main (int argc,char **argv) {
int k  = atoi(argv[1]);
 FILE* ptr = fopen("kefel.s","w+");
 if(ptr) {
     fputs(".section .text\n",ptr);
     fputs(".globl kefel\n",ptr);
     fputs("kefel: ",ptr);
 }
checkCases(k,ptr);
    return 0;
}
bool powerOfTwo(int n) {
   bool isPower = ((n != 1) && ((n & (n - 1))==0));
    return  isPower;
}
void checkCases(int k,FILE* ptr) {
    // k= -8323100;
    int j = 0;
    int exp = 0;
    int temp = k;
    int tempMask = temp;
    int flagMinus = 0;
    unsigned int mask = 1;
    int flagRightBit = 0;
    int flagEntry = 0;
    if (k < 0) {
        flagMinus = 1;
        temp = -1 * temp;
        tempMask = temp;
    }
    switch (k) {
        case -1:
            fputs("movl %edi,%eax\n", ptr);
            fputs("neg %eax\n", ptr);
            break;
        case 0:
            fputs("movl %edi,%eax\n", ptr);
            fputs("movl $0,%eax\n", ptr);
            break;
        case 1:
            break;
        case 3:
            fputs("movl %edi,%eax\n", ptr);
            fputs("leal (%edi,%eax,2),%eax\n", ptr);
            break;
        case 5:
            fputs("movl %edi,%eax\n", ptr);
            fputs("leal (%edi,%eax,4),%eax\n", ptr);
            break;
        case 9:
            fputs("movl %edi,%eax\n", ptr);
            fputs("leal (%edi,%eax,8),%eax\n", ptr);
            break;
        default:
            if (temp % 9 == 0) {
                j = temp / 9;
                if (powerOfTwo(j)) {
                    exp = checkExponent(j);
                    fputs("movl %edi,%eax\n", ptr);
                    fputs("leal (%edi,%eax,8),%eax\n", ptr);
                    fprintf(ptr, "%s%d%s\n", "shl $", exp, ",%eax");
                    break;
                }
            }
            if (temp % 3 == 0) {
                j = temp / 3;
                if (powerOfTwo(j)) {
                    exp = checkExponent(j);
                    checkExponent(j);
                    fputs("movl %edi,%eax\n", ptr);
                    fputs("leal (%eax,%eax,2),%eax\n", ptr);
                    fprintf(ptr, "%s%d%s\n", "shl $", exp, ",%eax");
                    break;
                }
            }
            if (temp % 5 == 0) {
                j = temp / 5;
                if (powerOfTwo(j)) {
                    exp = checkExponent(j);
                    fputs("movl %edi,%eax\n", ptr);
                    fputs("leal (%eax,%eax,4),%eax\n", ptr);
                    fprintf(ptr, "%s%d%s\n", "shl $", exp, ",%eax");
                    break;
                }

            }
            if (powerOfTwo(temp)) {
                int e =checkExponent(temp);
                fputs("movl %edi,%eax\n", ptr);
                fprintf(ptr, "%s%d%s\n", "shl $", e, ",%eax");
                break;
            }
            else  if (!powerOfTwo(temp)) {
                int i;
                int n = 0;
                int m = 0;
                for (i = 0; tempMask != 0; i++, tempMask = tempMask >> 1) {
                    if (mask & tempMask) {
                        m = i;
                        n = i;
                        i++;
                        tempMask = tempMask >> 1;
                        for (; tempMask != 0 && ((mask & tempMask)); i++, tempMask = tempMask >> 1) {
                            n = i;
                        }
                        flagRightBit = multNotPow2(m, n, ptr, flagEntry, flagRightBit);
                        flagEntry = 1;
                        n = 0;
                        m = 0;
                    }
                }
                break;
            }
    }
    if (flagMinus) {
        fputs("neg %eax\n", ptr);
    }
    fputs("ret\n", ptr);
}
int checkExponent(int k) {
    int counter =0;
    while(k!=1){
      k =  k/2;
      counter++;
    }
    return counter;
}
int  multNotPow2(int m,int n,FILE* ptr,int flag,int flagRightBit){
    if(flag==0){
        if (n==m){
            if (n==0){
                return 1;
            }
            multPow2(n,ptr,flag);
        }else if ((n-m)==1)
        {
            if(m==0){
                fputs("movl %edi,%ecx\n",ptr);
                fprintf(ptr, "%s%d%s\n", "shl $", n, ",%ecx");
                fputs("leal (%edi,%ecx),%eax\n",ptr);
                return 0;
            }
            //case A
            fputs("movl %edi,%eax\n",ptr);
            fprintf(ptr, "%s%d%s\n", "shl $", n, ",%eax");
            fputs("movl %edi,%ecx\n",ptr);
            if(m!=0){
                fprintf(ptr, "%s%d%s\n", "shl $", m, ",%ecx");
            }
            fputs("addl %ecx,%eax\n",ptr);
        }else{
            fputs("movl %edi,%eax\n",ptr);
            fprintf(ptr, "%s%d%s\n", "shl $", n+1, ",%eax");
            fputs("movl %edi,%ecx\n",ptr);
            if(m!=0){
                fprintf(ptr, "%s%d%s\n", "shl $", m, ",%ecx");
            }
            fputs("sub %ecx,%eax\n",ptr);
        }
    }else{
        if (n==m){
            multPow2(n,ptr,flag);
        }else if ((n-m)==1)
        {
            if(flagRightBit){
                fputs("movl %edi,%ecx\n",ptr);
                fputs("movl %edi,%ebx\n",ptr);
                fprintf(ptr, "%s%d%s\n", "shl $", n, ",%ecx");
                if(m!=0){
                    fprintf(ptr, "%s%d%s\n", "shl $", m, ",%ebx");
                }
                fputs("addl %ebx,%ecx\n",ptr);
                fputs("leal (%edi,%ecx),%eax\n",ptr);
            }else{
                //case A
                fputs("movl %edi,%ecx\n",ptr);
                fputs("movl %edi,%ebx\n",ptr);
                fprintf(ptr, "%s%d%s\n", "shl $", n, ",%ecx");
                if(m!=0){
                    fprintf(ptr, "%s%d%s\n", "shl $", m, ",%ebx");
                }
                fputs("leal (%ebx,%ecx),%eax\n",ptr);
            }
        }else{
            fputs("movl %edi,%ecx\n",ptr);
            fputs("movl %edi,%ebx\n",ptr);
            fprintf(ptr, "%s%d%s\n", "shl $", n+1, ",%ecx");
            if(m!=0){
                fprintf(ptr, "%s%d%s\n", "shl $", m, ",%ebx");
            }
            fputs("sub %ebx,%ecx\n",ptr);
            if(flagRightBit){
                fputs("leal (%edi,%ecx),%eax\n",ptr);
                return 0;
            }
            fputs("addl %ecx, %eax\n",ptr);
        }
    }
    return 0;
}
    void  multPow2(int n,FILE* ptr,int flag){
    if(flag==0){
        fprintf(ptr, "%s%d%s\n", "shl $",n, ",%eax");
    }
    else{
        fputs("movl %edi,%ecx\n",ptr);
        fprintf(ptr, "%s%d%s\n", "shl $",n, ",%ecx");
        fputs("addl %ecx,%eax\n",ptr);
    }

}

