import sys
def readfile():
    f = open('movies.txt', 'r')
    data = {}
    for line in f:
        line = line.strip('\n')
        x = line.split(",")
        for i in range(1, len(x)):
            if not x[i] in data:
                data[x[i]] = {x[0]}
            else:
                data[x[i]].add(x[0])
    print(data)
def  mainmenu():
 print("Please select an option:\n")
 print("1) Query by movies\n")
 print("2) Query by actor\n")
 print("3) Query by Insert a new movie\n")
 print("4)Save and Exi\n")
 print("5) Exi")

def main ():
    readfile()
    mainmenu()
if __name__ == "__main__":
     main()
