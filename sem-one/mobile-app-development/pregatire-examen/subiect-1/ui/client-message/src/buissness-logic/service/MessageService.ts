import {Environment} from "../../environment/Environment";
import {Message} from "../model/Message";

const axios = require('axios');

export class MessageService{

    public static getAllMessages(){
        return axios.get(`${Environment.apiUrl}message`);
    }

    public static sortMessages(messages: Message[]) : Message[]{
        const readMessages : Message[] = messages.filter(e => e.read);
        const unreadMessages: Message[] = messages.filter(e => !e.read);
        return this.sortMessagesByDate(readMessages, unreadMessages);
    }

    public static sortMessagesByDate(readMessages: Message[], unreadMessages: Message[]) : Message[]{
        const sortedReadMessages: Message[] = readMessages.sort((a, b) => a.created - b.created);
        const sortedUnreadedMessages: Message[] = unreadMessages.sort((a, b) => a.created - b.created);
        return sortedReadMessages.concat(sortedUnreadedMessages);
    }

}
