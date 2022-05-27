# How to run application
order for the system to function, the *docker-compose.yml* fine needs to be loaded before starting the other components / applications. It is also recommended to run the maven 

As a follow-up, it's advised to start each component in a specific order, due to some minor ~~bugs~~ features, that might stop the application to function as intended. The order is as follows:

1. "``docker-compose up``" needs to be run from the [ /docker ] folder via the command-line.
2. The main method of *core:MESEventHandler*
3. The main method of *UI:UIApplication* (GUI available at http://localhost:8080/ once started)
4. The main method of *AGV:AGVClient*
5. The main method of *Warehouse:WarehouseClient*
6. The main method of *AssemblyStation:AssemblyStationClient* 

- restart *AGV:AGVClient* if not shown as connected in the GUI.