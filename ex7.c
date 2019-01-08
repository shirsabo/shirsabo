import sys
import os
def readfile():
    f = open(sys.argv[1], 'r')
    data = {}
    for line in f:
        line = line.strip('\n')
        x = line.split(",")
        for i in range(1, len(x)):
            x[i] = x[i].lstrip(" ")
            if not x[i] in data:
                data[x[i]] = {x[0]}
            else:
                data[x[i]].add(x[0])
    print(data)
    f.close()
    return data
def  mainmenu(dictionary):
    option = 1
    while option:
        print("Please select an option:")
        print("1) Query by movies")
        print("2) Query by actor")
        print("3) Query by Insert a new movie")
        print("4)Save and Exit")
        print("5) Exit")
        option = int(input())
        if option == 1:
            movies(dictionary)
        elif option == 2:
            actors(dictionary)
        elif option == 3:
            dictionary = newmovie(dictionary)
        elif option == 4:
            print("hello")
        elif option == 5:
            exit(1)
def main():
    dictionary = readfile()
    mainmenu(dictionary)
def  checkmovie(movie,dictionary):
    for key in dictionary:
        if key == movie:
            return 1
    else:
        return 0
def movies(dictionary):
    union = []
    print("Please select two movies and an operator( &, |, ^ ) separated with ',':")
    moviesinput = input()
    setmovies = moviesinput.split(",")
    for j in range(0, len(setmovies)):
        setmovies[j] = setmovies[j].lstrip(' ')
    first = checkmovie(setmovies[0], dictionary)
    second = checkmovie(setmovies[1], dictionary)
    if (first==1 and second==1)== False:
        print("Error\n")
        return
    actorsfirstmovie = set(dictionary[setmovies[0]])
    actorssecondmovie = set(dictionary[setmovies[1]])
    if setmovies[2] == "^":
        xor = actorsfirstmovie ^ actorssecondmovie
        xor = sorted(xor)
        if len(xor) == 0:
            print(" There are no actors in this group")
            return
        print(", ".join(xor))
    elif setmovies[2] == "&":
        cut = actorsfirstmovie & actorssecondmovie
        cut = sorted(cut)
        if len(cut) == 0:
            print(" There are no actors in this group")
            return
        print(", ".join(cut))
    elif setmovies[2] == "|":
        union = actorsfirstmovie | actorssecondmovie
        union = sorted(union)
        if len(union) == 0:
            print(" There are no actors in this group")
            return
        print(", ".join(union))
    else:
        print("Error\n")
def actors(dictionary):
    print("Please select an actor:")
    players = set()
    actor = input()
    for key in dictionary:
        actors= dictionary[key]
        if actor in actors:
            l = set(dictionary[key])
            players = players | l
    players.remove(actor)
    if len(players) == 0:
        print("There are no actors in this group")
        return
    sortedplayers = sorted(set(players))
    print(", ".join(sortedplayers))
def newmovie(dictionary):
    print("Please insert a new movie:")
    new = input()
    new = new.split(",")
    lenght = len(new)
    if lenght <= 2:
        print("Error\n")
        return
    for j in range(0, len(new)):
        new[j] = new[j].lstrip(' ')
    movie = new[0]
    new = set(new)
    new.remove(movie)
    for key in dictionary:
        if key == movie:
            current = dictionary[key]
            new = current | new
            dictionary[key] = new
    dictionary[movie] = new
    print(dictionary)
    return dictionary
def savedata():
    print("savedata")

if __name__ == "__main__":
     main()

















