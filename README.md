# BCT Dashboard

## Development Environment

*  Java JDK 8
*  Docker 17.12+
*  Docker-Compose 1.18+
*  Node 9.4+
*  npm 5.6.0+

## IntelliJ Configuration

### New IntelliJ users

You will need to install IntelliJ, the Scala plugin, and tell IntelliJ where to find your JDK:

* download and install [IntelliJ for your platform][3]
* install the [Scala plugin][4]
* once you have created the bct-server module below, go to `File > Project Structure` and link your JDK.

### Project Creation

Begin with the creating a new project with `File > New > Project from Existing Sources`.  Use the following choices for
the dialog boxes:

*  choose the root directory of the project
*  choose `Create project from existing sources` (don't specify a framework)
*  uncheck any of the Scala source directories

Each of of the modules will be individually imported.  Start with the `bct-server`.

*  choose `File > New > Module from Existing Sources`
*  select the `bct-server` directory
*  select `Import module from existing model` and choose `sbt`
*  choose the defaults until you can click `Finish`

Next will be the `bct-client`:

*  choose `File > New > Module from Existing Sources`
*  select the `bct-client` directory
*  select `Create module from existing sources`
*  choose the defaults until you can click `Finish`

Next will be the `bct-docs`:

*  choose `File > New > Module from Existing Sources`
*  select the `bct-docs` directory
*  select `Create module from existing sources`
*  choose the defaults until you can click `Finish`

Next will be the `bct-components`:

*  choose `File > New > Module from Existing Sources`
*  select the `bct-components` directory
*  select `Create module from existing sources`
*  choose the defaults until you can click `Finish`

Finally import the `_docker` module:

*  choose `File > New > Module from Existing Sources`
*  select the `_docker` directory
*  select `Create module from existing sources`
*  choose the defaults until you can click `Finish`

## Docker Configuration

All services will be enabled through the top level `docker-compose.yml` file.

## Server Configuration

In the IntelliJ Side panel for the bct-server module:

* browse to `bct-server/src/main/scala/com/cgbc/dashboard/Main`
* ctrl-click `Main` and choose `Run 'Main'`

## Client Configuration

From within the bct-components directory

*  run `npm install`
*  run `npm link`

The bct-components directory will be added to your node modules

From within the bct-client directory

*  run `npm install`
*  run `npm link bct-components`
*  run `npm start`

The client application will be running on port:3000

From within the api-demo directory

*  run `npm install`
*  run `npm link bct-components`
*  run `npm start`

The demo application will be running on port:3001

From within the bct-docs directory

*  run `npm install`
*  run `npm link bct-components`
*  run `npm run catalog-start`

The [Catalog][1] application will be running on port:4000
For further instructions, visit the [Catalog Documentation][2]

Add a hosts record to your OS pointing `api-demo` at your machine. The bct-client project will reference api-demo:3001
from an iframe.

### Scripts

*  `./reset_ip.sh` to create the `.env` files with your workstation IP address.  Execute whenever your network changes.
*  `npm run dev:init` to initialise the Docker environment
*  `npm run dev:reset` to completely refresh the Docker environment
*  `npm run dev:reset-linux` to completely refresh the Docker environment in Linux
*  `npm run dev:restart` to restart the Docker environment without wiping data

File > Settings > Editor > Code Style > Typescript
    Punctuation > Use Single Quotes Always
    Spaces > Within > ES6 Import/Export

### Troubleshooting

* check the bct-client and bct-server are listening on port 3000 and 9000 respectively:

    `lsof -n -i:<port> | grep LISTEN`

* check that `docker-compose` is picking up the correct IP address for `devhost`:

    `docker-compose config`

* check that the `extra_hosts` value for `devhost` has been mapped correctly to the nginx container:

    `docker container ps`
    `docker exec -ti <nginx container> sh`
    `/ # cat /etc/hosts`

[1]: http://catalog.style
[2]: https://docs.catalog.style
[3]: https://www.jetbrains.com/idea/download/
[4]: https://www.jetbrains.com/help/idea/discover-intellij-idea-for-scala.html
