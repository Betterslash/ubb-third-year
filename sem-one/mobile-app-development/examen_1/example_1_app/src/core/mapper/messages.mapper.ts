import {Message} from "../model/message.model";

export class MessagesMapper{
    public static mapMessagesList = (messages: Message[]) : Map<string, Message[]> => {
        const result : Map<string, Message[]> = new Map<string, Message[]>();

        messages.forEach(e => {
            const newMessagesList = result.get(e.sender);
            if(newMessagesList == undefined){
                result.set(e.sender, [] as Message[]);
            }
            newMessagesList?.push(e);
        });
        return result;
    }
}
