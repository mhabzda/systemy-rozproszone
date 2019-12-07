package server;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.stream.ActorMaterializer;
import akka.stream.Materializer;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class Server {
    public static Materializer MATERIALIZER;

    public static void main(String[] args) throws Exception {
        File configFile = new File("server.conf");
        Config config = ConfigFactory.parseFile(configFile);

        final ActorSystem system = ActorSystem.create("server_system", config);
        final ActorRef server = system.actorOf(Props.create(ServerActor.class), "server");
        MATERIALIZER = ActorMaterializer.create(system);

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            String line = br.readLine();
            if (line.equals("q")) {
                break;
            }
        }

        system.terminate();
    }
}
