Embedded XINU
ARM Playground
Version: 1.0

1. What is the ARM Playground?
2. What is Embedded XINU?
3. How do I get started?
4. Links

-------------------------------
1. What is the ARM Playground?
-------------------------------

The ARM Playground is a stripped-down version of the Embedded XINU operating
system, intended to enable students to learn assembly language programming
on a real ARM processor.  Rather than simulate a RISC device, the ARM
Playground environment allows a simple main program to be linked with
a fully operational kernel image, which can then be uploaded and executed
directly on an embedded ARM system.

-------------------------
2. What is Embedded XINU?
-------------------------

XINU ("XINU Is Not Unix", a recursive acronym) is a Unix-like operating system
originally developed by Douglas Comer for instructional purposes at Purdue
University in the 1980s.

Embedded XINU is a reimplementation of the original XINU operating system
developed at Marquette University by Dennis Brylow.  Embedded XINU runs on the
embedded processors in inexpensive devices such as the Raspberry Pi board, and
Linksys WRT54GL routers.  It is widely used for courses and research in the
areas of Operating Systems, Hardware Systems, Embedded Systems, and Compilers.

------------------------
3. How do I get started?
------------------------

   The file main.S contains a template for beginning an assembly language
program.  Write your program in ARM assembly language between the comments
denoting the start and end.
   To compile your program into executable machine language, execute the
command

	make

If there are no errors in your code, a new kernel image called "xinu.boot"
will be created in this directory.  To upload and execute your program,

	arm-console

which contacts the central server, requests a backend machine from the pool
of available targets, uploads your kernel, and power-cycles the remote
ARM system to start your program.  The process takes several seconds,
but once you see the XINU banner, you can interact with your program
normally.

--------
4. Links
--------

The Embedded XINU Wiki
----------------------
        The home of the Embedded XINU project
        http://xinu.mscs.mu.edu/

Dr. Brylow's Embedded XINU Lab Infrastructure Page
--------------------------------------------------
        More information about the Embedded XINU Lab at Marquette University
        http://www.mscs.mu.edu/~brylow/xinu/

