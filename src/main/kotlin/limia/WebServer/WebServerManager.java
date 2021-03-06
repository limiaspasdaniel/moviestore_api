package limia.WebServer;

import limia.Routing.RoutingService;

import static spark.Spark.*;

/**
 * Created by macbook on 8/4/17.
 */
class WebServerManager {

    public static void start(int port) {
        port(port);
        System.out.println(String.format("Starting web server on port %s",port));
    }

    public static void registerRoutingService(RoutingService routingService) {
        routingService.initializeRoutes();
    }

    public static void enableGlobalFilters() {
        before((req, res) -> {
            res.type("application/json");
            res.status(200);
        });

    }
}
