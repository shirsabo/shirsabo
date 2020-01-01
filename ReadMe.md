# Assignment 3 
in this project we were asked to connect the server of "Flightgear" and read values from it and also to connect as a Client and actually send the server updates about variables in the game's algorithm, all that according to "fly.txt".

-this project has a serious amount of code and because of that this file will make everything clear to you.
### The main
>The lexer
```sh
string *lexer(char *argv[]);
```
in this function we creates a "Mega" string that will contain the text file in one String seperated with ",", and returns String * .
```sh
word1,word2,......,wordK
```
Functions that we creat in order to edit the text file:
```sh
 void enterLine(char token, deque <string> *deque, size_t pos, size_t *prev, string line, int *sizeDeque):
```
 in case we have an equals sign or parenthesis - entering all the line to the deque after removing the spaces
 ```sh
void editConditionParser(string s, deque <string> *deque, int *sizeDeque) :
```
entering the line to the deque as needed for the while or the if command.
 ```sh
 string edit(string s)
 ```
entering the line to the deque as needed for the while or the if command .
>Creating the maps
```sh
   createMap(&mp, varTable, server_map, offWhileServer);
```
in this function we make all the Commands know the adresses of the maps:
```sh
 unordered_map<string, Var *> *varTable
```
```sh
unordered_map<string, Var *> *server_map
```
also, we take care of the map of the Comands:
```sh
unordered_map<string, Command *> *pMap
```
we insert to the map  <key,command> of any possible kind: "if"," Print","Sleep",etc.
```sh
For example:
    pMap->insert(pair<string, Command *>("openDataServer", openCommand));
```
Note: in this program only single Command* is Created for each type of Command.
>The parser
```sh
  void parser(unordered_map<string, Command *> *mp, string *array, int size, int *offWhileServer)
```
one of the main functions of this program, in here all the threads created:
| Thread | Function |
| ------ | ------ |
| thread t1(&OpenServerCommand::acceptence, ref((c1)), &(array[index + 1])) | waiting until acceptence from server |
|  thread t2(&OpenServerCommand::initializeServerMap, ref((c1))) |  initialize server map|
|thread t3(&OpenServerCommand::dataEntryPoint, ref((c1))) | while loop that is "alive" after acceptence until exit 0, in this thread the program gets updates from server|
| thread t4(&ConnectCommand::connection, ref(m2), &array[index + 1]) | make a connection as a client to the simulator|
after creating threads the program calls to 
```sh
   iterateParser(size, mp, &index, array);
```
    while (not empty) {
        auto pos = mp->find(array[*index]);
        if (pos == mp->end()) {
        throw "problem with lexer;
        } else {
            Command *c = pos->second;
            *index += c->execute(&array[*index + 1]); 

as we can see ' here we are calling to the "Execute" function, and by that all the command are executed.
```sh
  void deleteProject(unordered_map<string, Var *> *serverMap, unordered_map<string, Var *> *varTable,unordered_map<string, Command *> *mp) ;
```
 deleting the commands and the vars
### Var.cpp
 * a var has a name, sim, direction and a value.
 * we use the value of the var in order to know the values of the vars defined in the text file.
 * if the direction in towards outside we will want to update the flight simulator according to the
 * values of the vars we created in our program.
 * if the direction in towards inside we will get the value of the var from the flight simulator.
 * we might create a var and want to assign a value we choose, in this case we will see the equals sign and use the shunting yard in order to calculate it.
 
```sh
Var::Var(string nameIn, string simIn, string inoutIn)
```
### SleepCommand.cpp
```sh
SleepCommand::SleepCommand(unordered_map<string, Var *> *pMap)
```
Note: the sleep command knows the var table in case of usig shunting yard.
```sh
int SleepCommand::execute(string *s)
```
 calculating the number of seconds we want to sleep by the shunting yard.
 
 * making the thread sleep that long.

* [AngularJS] - HTML enhanced for web apps!
* [Ace Editor] - awesome web-based text editor
* [markdown-it] - Markdown parser done right. Fast and easy to extend.
* [Twitter Bootstrap] - great UI boilerplate for modern web apps
* [node.js] - evented I/O for the backend
* [Express] - fast node.js network app framework [@tjholowaychuk]
* [Gulp] - the streaming build system
* [Breakdance](https://breakdance.github.io/breakdance/) - HTML to Markdown converter
* [jQuery] - duh

And of course Dillinger itself is open source with a [public repository][dill]
 on GitHub.

### Installation

Dillinger requires [Node.js](https://nodejs.org/) v4+ to run.

Install the dependencies and devDependencies and start the server.

```sh
$ cd dillinger
$ npm install -d
$ node app
```

For production environments...

```sh
$ npm install --production
$ NODE_ENV=production node app
```

### Plugins

Dillinger is currently extended with the following plugins. Instructions on how to use them in your own application are linked below.

| Plugin | README |
| ------ | ------ |
| Dropbox | [plugins/dropbox/README.md][PlDb] |
| GitHub | [plugins/github/README.md][PlGh] |
| Google Drive | [plugins/googledrive/README.md][PlGd] |
| OneDrive | [plugins/onedrive/README.md][PlOd] |
| Medium | [plugins/medium/README.md][PlMe] |
| Google Analytics | [plugins/googleanalytics/README.md][PlGa] |


### Development

Want to contribute? Great!

Dillinger uses Gulp + Webpack for fast developing.
Make a change in your file and instantaneously see your updates!

Open your favorite Terminal and run these commands.

First Tab:
```sh
$ node app
```

Second Tab:
```sh
$ gulp watch
```

(optional) Third:
```sh
$ karma test
```
#### Building for source
For production release:
```sh
$ gulp build --prod
```
Generating pre-built zip archives for distribution:
```sh
$ gulp build dist --prod
```
### Docker
Dillinger is very easy to install and deploy in a Docker container.

By default, the Docker will expose port 8080, so change this within the Dockerfile if necessary. When ready, simply use the Dockerfile to build the image.

```sh
cd dillinger
docker build -t joemccann/dillinger:${package.json.version} .
```
This will create the dillinger image and pull in the necessary dependencies. Be sure to swap out `${package.json.version}` with the actual version of Dillinger.

Once done, run the Docker image and map the port to whatever you wish on your host. In this example, we simply map port 8000 of the host to port 8080 of the Docker (or whatever port was exposed in the Dockerfile):

```sh
docker run -d -p 8000:8080 --restart="always" <youruser>/dillinger:${package.json.version}
```

Verify the deployment by navigating to your server address in your preferred browser.

```sh
127.0.0.1:8000
```

#### Kubernetes + Google Cloud

See [KUBERNETES.md](https://github.com/joemccann/dillinger/blob/master/KUBERNETES.md)


### Todos

 - Write MORE Tests
 - Add Night Mode

License
----

MIT

