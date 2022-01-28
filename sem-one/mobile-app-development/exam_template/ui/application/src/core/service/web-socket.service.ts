import {Environment} from "../environment/Environment";
import {Logger} from "../util/logger.util";

export class WebSocketService{
    private static readonly wsInstance: WebSocket = new WebSocket(Environment.wsUrl);

    public static getInstance = (): WebSocket => {
        return WebSocketService.wsInstance;
    }

    public static onConnect = (): void => {
        WebSocketService.wsInstance.onopen = function(e) {
            Logger.info(`${WebSocketService.name} connected ...`)
            WebSocketService.wsInstance.send("My name is John");
        };
    }

    public static onMessage = (): void => {
        WebSocketService.wsInstance.onmessage = function(e) {
            Logger.info(`${WebSocketService.name} received ... ${e}`);
        };
    }

    public static onClose = (): void => {
        WebSocketService.wsInstance.onclose = function (e) {
            if(e.wasClean){
                Logger.info(`${WebSocketService.name} sucessfully closed `);
            }else{
                Logger.warning(`${WebSocketService.name} unsucessfully closed `);
            }
        }
    }

    public static onError = (): void => {
        WebSocketService.wsInstance.onerror = function(error) {
            Logger.danger(`${WebSocketService.name} has error :  ${error}`);
        };
    }
}
