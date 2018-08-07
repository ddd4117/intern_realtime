package com.example.demo.tcp_connection;

import com.example.demo.common.item.daegu_info.DaeguIncidient;
import com.example.demo.common.item.ext.ExternalCarInfo;
import com.example.demo.manager.DataManager;
import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

public class TCP_Connection_Thread implements Runnable {
    ServerSocket serverSocket = null;
    HashMap clients;

    public TCP_Connection_Thread() {
        try {
            serverSocket = new ServerSocket(5000);
            clients = new HashMap();
            Collections.synchronizedMap(clients);
            System.out.println("Connection Ready.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void sendToAll(String msg) {
        Iterator it = clients.keySet().iterator();
        while (it.hasNext()) {
            try {
                DataOutputStream out = (DataOutputStream) clients
                        .get(it.next());
                out.writeUTF(msg);
                out.flush();
            } catch (IOException e) {
            }
        } // while
    } // sendToAll

    public void run() {
        Socket socket = null;
        while (true) {
            try {
                socket = serverSocket.accept();
                System.out.println("[" + socket.getInetAddress() + ":"
                        + socket.getPort() + "]" + "is connected.");
                ServerReceiver thread = new ServerReceiver(socket);
                thread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class ServerReceiver extends Thread {
        Socket socket;
        DataInputStream in;
        DataOutputStream out;

        ServerReceiver(Socket socket) {
            this.socket = socket;
            try {
                in = new DataInputStream(socket.getInputStream());
                out = new DataOutputStream(socket.getOutputStream());
            } catch (IOException e) {
            }
        }

        public boolean isDuplicate(double x, double y) {
            HashMap<String, DaeguIncidient> hashMap = DataManager.getInstance().getIncidientHashMap();
            for (String key : hashMap.keySet()) {
                DaeguIncidient incidient = hashMap.get(key);
                if(incidient.getIncidientcode() != 1) continue; // it is not a accident code

                double dis = distance(incidient.getCoordy(), incidient.getCoordx(), y, x);
                if (dis < 50) {
                    return true;
                }
            }
            for (ExternalCarInfo car : DataManager.getInstance().getExternalAccident()) {
                double dis = distance(car.getY(), car.getX(), y, x);
                if (dis < 50) {
                    return true;
                }
            }
            return false;
        }

        public void run() {
            String name = "";
            try {
                name = in.readUTF();
                Gson gson = new Gson();
                sendToAll("#" + name + "is connected.");
                clients.put(name, out);
                System.out.println("현재 서버접속자 수는 "
                        + clients.size() + "입니다.");

                while (in != null) {
                    String msg = in.readUTF();
                    ExternalCarInfo externalCarInfo = gson.fromJson(msg, ExternalCarInfo.class);
                    System.out.println(gson.toJson(externalCarInfo));
                    switch (externalCarInfo.getType()) {
                        case "accident":
                            boolean flag = isDuplicate(externalCarInfo.getX(), externalCarInfo.getY());
                            /* not duplicated */
                            if (!flag) {
                                DataManager.getInstance().getExternalAccident().add(externalCarInfo);
                                System.out.println("add external information");
                            }
                            break;
                        case "sudden case":
                            DataManager.getInstance().getExternalSuddenCase().add(externalCarInfo);
                            System.out.println("add sudden case information");
                            break;
                        default:
                            System.out.println("exception-");
                            break;
                    }
                }
            } catch (IOException e) {
                // ignore
            } finally {
                sendToAll("#" + name + "is disconnected.");
                clients.remove(name);
                System.out.println("[" + socket.getInetAddress() + ":"
                        + socket.getPort() + "]"
                        + "에서 접속을 종료하였습니다.");
                System.out.println("현재 서버접속자 수는 "
                        + clients.size() + "입니다.");
            } // try
        } // run
    } // ReceiverThread


    /**
     * 두 지점간의 거리 계산
     *
     * @param lat1 지점 1 위도
     * @param lon1 지점 1 경도
     * @param lat2 지점 2 위도
     * @param lon2 지점 2 경도
     * @return
     */
    private static double distance(double lat1, double lon1, double lat2, double lon2) {

        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));

        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        dist = dist * 1609.344;

        return (dist);
    }

    // This function converts decimal degrees to radians
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    // This function converts radians to decimal degrees
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }
}
