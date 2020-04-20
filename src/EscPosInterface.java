

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * This is the stand alone class that handles what to send to the printer to print a given text.
 * It starts by sending 2 bytes (constant INIT) to tell the printer to get ready,
 * followed by the given text in bytes, and ends by sending 5 bytes (constant END) to tell the
 * printer that it shoudl print what it received.
 * Tested with USB device on Linux. Writes directly on the devices file in /dev
 * Check online documentation about ESC/POS specifications if you want to understand
 * what each byte means.
 **/
public class EscPosInterface {

	//implemented as a singleton for the original project
	private static EscPosInterface instance = null;
	
	//the linux path to the printer device (/dev/...)
	private String printerDevicePath = null;
	
	//bytes to initiate the printer
	private static final byte[] INIT = {
			0x1b,
			0x40
	};
	
	//bytes to end and print whatever the printer received
	private static final byte[] END = {
			0x0a,
			0x1d,
			0x56,
			0x41,
			0x03
	};
	
	private EscPosInterface () {
	}
	
	//return the only instance
	public static EscPosInterface getInstance () {
		if (instance == null) {
			instance = new EscPosInterface();
		}
		return instance;
	}
	
	//sets the device path
	public void setPrinterDevicePath (String path) {
		printerDevicePath = path;
		if (printerDevicePath != null) {
			printerDevicePath = printerDevicePath.trim();
		}
	}
	
	//print a given text
	//the width is printer dependent
	public void printReceipt (String receipt) {
		if (printerDevicePath != null && !printerDevicePath.isEmpty()) {
			try {
				Files.write(Paths.get(printerDevicePath), concatenateByteArrays(INIT, receipt.getBytes(), END), StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	//concatenates arrays of bytes into a single array
	private byte[] concatenateByteArrays (byte[] ... byteArrays) {
		int length = 0;
		for (byte[] byteArray : byteArrays) {
			length += byteArray.length;
		}
		byte[] result = new byte[length];
		int i = 0;
		for (byte[] byteArray : byteArrays) {
			for (byte byteData : byteArray) {
				result[i] = byteData;
				++i;
			}
		}
		return result;
	}
}
