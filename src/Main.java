import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;


public class Main {
    public static void main(String[] args) {
        Random random = new Random();
        random.start();
    }

}

class Random{

    private OutputStream outputStream;
    private SerialPort serialPort;
    private final String port = "dev/ttyUSB0/";
    private final int rate = 9600;

    public void start(){
        System.out.println("Empezando");
        initConnection();
        sendData("Hola");
    }


    public void initConnection(){
        CommPortIdentifier commPortIdentifier = null;
        Enumeration puertoEnum = CommPortIdentifier.getPortIdentifiers();

        System.out.println(puertoEnum.hasMoreElements());

        while (puertoEnum.hasMoreElements()){

            CommPortIdentifier currentPort = (CommPortIdentifier) puertoEnum.nextElement();
            if(port.equals(currentPort.getName())){
                commPortIdentifier = currentPort;
                break;
            }
        }

        if (commPortIdentifier == null){
            throw new NullPointerException("El puerto no existe.");
        }

        try {
            serialPort = (SerialPort) commPortIdentifier.open(this.getClass().getName(), 2000);
            serialPort.setSerialPortParams(rate, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
            outputStream = serialPort.getOutputStream();
        }catch (PortInUseException | UnsupportedCommOperationException | IOException e){
            System.out.println("error, el puerto ya está en uso");
        }

    }

    public void sendData(String data){
        try {
            outputStream.write(data.getBytes());
        }catch (IOException e){
            System.out.println("Error de entrada/salida");
        }
    }


}