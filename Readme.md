A SIMPLE NAPSTER 
STYLE PEER TO PEER 
FILE SHARING SYSTEM 
USER MANUAL 


INTRODUCTION: 

This implementation is Java based Peer to Peer (P2P) File Sharing System, which 
allows the end-users to share resources (Files). It is a Centralized P2P file sharing 
system, where the server doesn’t have the actual files; in turn the peers share the files 
between themselves, the server acts as an Indexing Server. The P2P system has two 
components: 

1. A Central Indexing Server 

This functioning of this server is to index all the contents of the peers that register 
with it. It also facilitates search function to peers, to get the file location requested 
by the clients. 

2. A Peer 

The peer acts as a server and client both. As a Client, it registers itself with the 
indexing server; it also specifies the user the filename along with the peer which 
holds that file. The peer list is obtained from the Index Sever, the user can pick 
anyone from the list and then the client connects to the peer and gets the file. As a 
Server, it waits for file requests from other peers and sends the requested file. 

Implementation’s File Structure: 

Folder Name: 01_GROUP10_PA1.zip 

. sharedFolder 
. centralized-p2p-system 
o src 
. server 
. P2PServer.java 
. RandomFileGenerator.java 


. Client 
. P2PClient.java 
. PeerFileDownload.java 


Before RUN: 

1. Edit P2PClient.java file, to change the path according to your system, because 
it is the path where the shared folder is located, and file will be downloaded 
to this folder, line (16 & 93) 
2. Demo: 
a. Currently Line(16) is: String fileDirectory = 
"E:/Java/Workspace/PA1_CentralizedP2PSystem/sharedFolder/"; 
You can change this to where you want to store, and follow the next 
step b. 
b. Copy the sharedFolder, to the path which is specified by you, above 
c. Edit PeerFileDownload.java file, to change the path here because it is 
the path where the shared folder and its file will be present for the other 
peer to download from the instructions are mentioned in the comments 
as well. 





HOW TO RUN: 

Using Command Prompt: 

1. Extract the 01_GROUP10_PA1.zip 
2. Open cmd 
3. Goto \01_GROUP10_PA1\centralized-p2p-system\src\server\ 
4. Run the cmd: javac *.java 
5. Goto \01_GROUP10_PA1\centralized-p2p-system\src\client\ 



6. Run the cmd: javac *.java 
7. Now type cd .. 
8. You will be in the path: \01_GROUP10_PA1\centralized-p2p-system\src\ 
9. Now to run server code: java server.P2PServer 
10. Now to run client code: java client.Client 1100 


Pass the portNo as the cmd line argument 

11. You can have as many clients you want, don’t forget to specify port number, 
else the program will stop functioning. 


IMPORTANT 

The port number passed as cmd line arg, will be used as the folder to download/read 
the files for the peer(s) i.e. port number will be the folder name, when the peer asks 
to obtain a file, the peerID will be specified as what is returned by the search option. 

Port Number 5004, is already used for server. 

The folder will be: C(Any drive):/sharedFolder/1100 

While downloading files, the peer who has requested the download, need not have 
the folder, it will be created automatically. 

But, for the source folder from where it is getting downloaded, the folder must be 
present inside sharedFolder 


