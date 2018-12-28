/******************************************
*Student name: shir sabo
*Student ID:206481665
*Submit Info:saboshi1
*Exercise name: ex6
******************************************/
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#define MAX_USER_NAME 10
#define MIN_USER_NAME 3
#define MAX_PASSWORD 15
#define MIN_PASSWORD 3


typedef struct
{
	char userid[9];
	char* firstname;
	char* lastname;
	char* age;
	char gender;
	char* username;
	char* password;
	char* hobbies;
	char* description;
}user;

typedef struct
{
	user*data;
	struct node* next;
}node;

node* FindPlace(char *new_last_name, node* head);
void readFile(user*** males, user**females, node** head);
void AddUser();
user* LogIn(user** malesptr, node* head,int * flag);
user* CheckWomen(node* head,char* password, char*username);
user* CheckMen(user**malesptr, char*password,char*username);
user* CheckWomen(node* head,char* password,char*username) {
	 node* current = head;  // Initialize current 
	while (current != NULL)
	{
		if (strcmp(current->data->username, username) == 0)
		{
			return current->data;
		}
		current = current->next;
	}
	return NULL;
}
user* CheckMen(user**malesptr, char*password, char*username) {
	int i;
	for (i = 0; i < (sizeof(malesptr) / sizeof(user*)); i++) {
		if (strcmp(malesptr[i]->username, username) == 0)
		{
			return malesptr[i];
		}

	}
	return NULL;


}

user* LogIn(user** malesptr, node* head, int *flag)
{
	user** PointerToMale = malesptr;
	node* HeadToWomen = head;
	char username[MAX_USER_NAME+1];
	char password[MAX_PASSWORD+1];
	user* existwomen;
	user* existmen;
	int i;
	printf("Please enter your username:\n");
	scanf("%s", username);
	printf("Please enter your password:\n");
	scanf("%s", password);
	existwomen=CheckWomen(HeadToWomen, password, username);
	if (existwomen != NULL)
	{
		if (strcmp(existwomen->password, password) == 0)
		{
			*flag = 0;
			return existwomen;
		}
		else {
			printf("Bad password\n");
			return NULL;
		}
	}
	else {
		existmen=CheckMen(PointerToMale, password, username);
		if (existmen != NULL)
		{
			if (strcmp(existmen->password, password) == 0)
			{
				return existmen;
			}
			else {
				printf("Bad password\n");
				return NULL;
			}
		}
		else {

			printf("user do not exist please try again\n");
			*flag = 1;
		}
		
	}
}
void AddUser() {

}

node*FindPlace(char *new_last_name, node* head) {

	node *prev = NULL;
	node *current = head;
	while (current != NULL) {
		if (strcmp(new_last_name, current->data->lastname) < 0) {
			break;
		}

		prev = current;
		current = current->next;
	}

	return prev;
}




void readFile(user*** males, user**females,node** head){

	int c;
	int i;
	int j;
	int info;
	int* ptr_id;
	int* ptr_name;
	int* ptr_family;
	FILE* fpointer;
	char str[300];
	int strIndex;
	user* ptr;
	user** pointer;
	int pointer_index;
	int maleCounter=0;
	user** temp=NULL;
	node* after;
	
	fpointer = fopen("input.txt", "r");
	if (fpointer == NULL)
	{
		exit(1);
	}

	// Start reading from the file - read the fist char in the line
	strIndex = 0;
	str[strIndex] = '0';
	//str[strIndex] = fgetc(fpointer);
	
	while (1)
	{
		ptr = (user*)(malloc(sizeof(user)));
		if (ptr == NULL)
		{
			exit(1);
		}
		for (info = 0 ,pointer_index=0; info <= 8; info++, pointer_index++)
		{
			strIndex = -1;
			while (str[strIndex] != ';' && str[strIndex] != '\n')
			{
				strIndex++;
				str[strIndex] = fgetc(fpointer);
				if (str[strIndex] == EOF)
				{
					return;
				}
				
			}
			str[strIndex] = '\0';
			switch (info)
			{


			case 0:
				strcpy(ptr->userid, str);
				break;
			case 1:
				ptr->firstname = (char*)malloc(sizeof(char)*(strlen(str)));
				if (ptr->firstname == NULL) {
					exit(1);

				}
				strcpy(ptr->firstname, str);
				break;
			case 2:
				ptr->lastname = (char*)malloc(sizeof(char)*(strlen(str)));
				if (ptr->lastname == NULL) {
					exit(1);

				}
				strcpy(ptr->lastname, str);
				break;
			case 3:
				ptr->age = (char*)malloc(sizeof(char)*(strlen(str)));
				if (ptr->age == NULL) {
					exit(1);

				}
				strcpy(ptr->age, str);
				break;
			case 4:
				ptr->gender = str[0];
				break;
			case 5:
				ptr->username = (char*)malloc(sizeof(char)*(strlen(str)));
				if (ptr->username == NULL) {
					exit(1);

				}
				strcpy(ptr->username, str);
				break;
			case 6:
				ptr->password = (char*)malloc(sizeof(char)*(strlen(str)));
				if (ptr->password == NULL) {
					exit(1);

				}
				strcpy(ptr->password, str);
				break;

			case 7:
				ptr->hobbies = (char*)malloc(sizeof(char)*(strlen(str)));
				if (ptr->hobbies == NULL) {
					exit(1);

				}
				strcpy(ptr->hobbies, str);
				break;

			case 8:
				ptr->description = (char*)malloc(sizeof(char)*(strlen(str)));
				if (ptr->description == NULL) {
					exit(1);

				}
				strcpy(ptr->description, str);
				break;
			}
			

		} // finished user
		if (ptr->gender == 'M') {
			maleCounter++;
			if (maleCounter == 1) {
				*males = (user**)malloc(sizeof(user*));
				if (*males == NULL) {
					exit(1);

				}
				temp = *males;
			}
			else {
				*males = (user**)realloc(temp, (maleCounter )* sizeof(user*));
				if (*males == NULL) {
					exit(1);

				}

				temp = *males;
			}
			*(*males + maleCounter-1) = ptr;
		}
		if (ptr->gender == 'F') {
			node *new_entry = (node *)malloc(sizeof(node));
			if (NULL == new_entry) {
				exit(1);
			}
			new_entry->data = ptr;//conects the data to the struct user
			after = FindPlace(new_entry->data->lastname, *head);
			if (NULL == after) {
				if (NULL == *head) {
					new_entry->next = NULL;
				}
				else
				{
					new_entry->next = *head;
				}
				*head = new_entry;
			}
			else    // add it in the middle
			{
				new_entry->next = after->next;
				after->next = new_entry;
			}


			}

			

		}

	}


int main()
{
	user** malesPtr = NULL;
	user*femalelist = NULL;
	node* head = NULL;
	user* logged;
	int indicator = 0;
	 readFile(&malesPtr, &femalelist, &head);//by adress
	 printf("%s\n", (*malesPtr)->firstname);
	 printf("%s\n", (*(malesPtr+1))->firstname);

	 printf("succes\n");
	int option = 0;
	while (option != 3)
	{
		printf("Welcome! please choose an option\n");
		printf("1 - Log in\n");
		printf("2 - New member\n");
		printf("3 - Exit \n");
		scanf("%d" ,&option);
		if (option == 1)
		{
			
			logged=LogIn(malesPtr,head,&indicator);
			if (indicator == 1) {
				logged = LogIn(malesPtr, head, &indicator);
			}
			else if (logged != NULL)
			{
				printf("Hello %s\n", logged->username);

			}

		}
	    else if (option == 2)
		{
			exit(1);
		}
	    else if (option == 3)
		{
			exit(1);

		}
		else
		{
			printf("Bad choice, please try again\n");

		} 

	}
	return 0;
}

