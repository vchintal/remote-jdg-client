# Simple JDG (Hotrod) Client 

## Prerequisite

1. Download the JDG server binary from the either the [Customer Portal](https://access.redhat.com/jbossnetwork/restricted/listSoftware.html?product=data.grid&downloadType=distributions) or from [developers.redhat.com](https://developers.redhat.com/download-manager/file/jboss-datagrid-7.1.0-server.zip)
2. Unzip the folder to a chosen path. Let us refer to the unzipped path ending with `jboss-datagrid-7.1.0-server` as `$JDG_HOME`. On Linux this can be achieved with the following command:
    ```sh
    export JDG_HOME=/path/to/jboss-datagrid-7.1.0-server
    ```
3. If using the **Customer Portal**, download and save the [Red Hat JBoss Data Grid 7.1.2 Server Update](https://access.redhat.com/jbossnetwork/restricted/softwareDownload.html?softwareId=56221). Run the server update by following the steps below

    ```sh 
    # Start the JDG server in one terminal with the following command
    $JDG_HOME/bin/standalone.sh 
    
    # In another terminal with JDG_HOME environment variable set, run 
    # the CLI command 
    $JDG_HOME/bin/cli.sh --connect --controller=127.0.0.1:9990
    
    # Once logged into CLI run the following command
    [standalone@127.0.0.1:9990 /] patch apply /path/to/jboss-datagrid-7.1.1-server-patch.zip
    
    # Then shutdown the JDG instance 
    [standalone@127.0.0.1:9990 /] shutdown
    ```

## Executing the application

### Start the JDG server in Domain mode

With the `JDG_HOME` environment variable set as show above, in the root of current project, run the command:

```sh
# The following command would start the JDG server in background and in domain mode  
mvn wildfly:start
```  
### Execute the main class

```sh 
mvn compile exec:exec
```

### Verify output

You should see the following output of the following form:

```sh
13:46:15.217 [main] INFO  org.everythingjboss.jdg.server.JDGRemoteClientConsoleApp - The size of the remote cache is : 2
```

### Shutdown JDG (in domain mode)

```sh 
mvn wildfly:shutdown
``` 

## Important Notes

* The domain configuration used to start the JDG instances can be found in folder `src/main/resources/domain/configuration`
* `mvn clean` clears out any other folders in `src/main/resources/domain` that got created because of the server start(s)/stop(s)
* Any CLI commands that need to be run should placed in folder `src/main/resources` with the name `commands.cli`. The CLI commands can then be executed, while the server is up, by running the following command:
   ```sh 
   mvn wildfly:execute-commands
   ```
* Any changes (via CLI or management console) to the server configuration is retained and can be viewed in the XML files in the configuration folder mentioned in first bullet point 