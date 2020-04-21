# Simple ESC/POS Printer Interface In Java For Linux

This is a simple Java stand alone class to act as a interface for a ESC/POS type ticket printer to print simple texts. For Linux only.

This Java class is extracted from my "Simple Point Of Sale With Ticket Printing Java Linux" project, in case someone is looking for something like this.

I made this long time ago for a friend to be used in a small shop, installed in a laptop with Ubuntu OS, and a ESC/POS type ticket printer. The printer looks like Epson TM-U220D model, with Epson logo, but I think it's a knockoff because official Epson drivers doesn't work.

# What it does

It sends required initial signals, user's text, and ending signals to print the user's text.

# How it does

First, it concatenates the initial signal in bytes, user's text converted from string to bytes, and ending signal in bytes, and writes the array of bytes to the printer's Linux USB device file.

The device file looks like this: /dev/usb/lp0

Initial signals, to tell the printer to be ready:
1. 0x1b
2. 0x40

Ending signals, to tell the printer that it can print the text:
1. 0x0a
2. 0x1d
3. 0x56
4. 0x41
5. 0x03

See online ESC/POS specifications to understand what these bytes means, I certantly don't.
