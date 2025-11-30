#Connect4

Project Overview

Connect4 is a two-player Connect Four game implemented in Java, built for my CS2 course final. The project uses a client-server architecture: one program acts as the server (managing game logic and communicating moves), and another acts as the client (allowing players to connect, send moves, and receive updates). Players take turns dropping “discs” into a vertically suspended grid; the server tracks the board state and determines win/draw conditions. While the code accomplishes basic functionality, the focus was on learning networking (sockets), inter-process communication, and core game logic rather than polish or advanced UI — so it remains a little rough around the edges, but fully functional as a simple networked Connect Four game.

Technologies & Techniques Used

Java — core language for both client and server.

Socket programming — using java.net.Socket and java.net.ServerSocket to send/receive player moves over TCP. 


Object-oriented design — classes for board, players, game logic, and communication handling.

Console-based interaction enough to input moves, display the board, and update status.
