export enum LogType{
    INFO,
    WARNNING,
    DANGER
}

export abstract class Logger{
    private static buildMessage(message : string, logType : LogType){
        return "%c" + LogType[logType] + " -> "+ new Date().toLocaleString() + " : " + message;
    }

    public static info(message : string) : void{
        console.log(this.buildMessage(message, LogType.INFO), "color: #00ff00");
    }

    public static warning(message : string) : void{
        console.log(this.buildMessage(message, LogType.WARNNING), "color: #ede73b");
    }

    public static danger(message : string) : void{
        console.log(this.buildMessage(message, LogType.DANGER), "color: #ff0000");
    }
}
