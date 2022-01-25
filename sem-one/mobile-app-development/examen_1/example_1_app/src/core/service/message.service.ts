import axios, {AxiosResponse} from 'axios';
import {Message} from "../model/message.model";
import {Environment} from "../../environment/Environment";


export class MessageService{
    public static getMessages = () : Promise<AxiosResponse<Message[]>> => {
        return axios.get<Message[]>(`${Environment.apiUrl}message`);
    }

    public static filterMessages = (messages: Map<string, Message[]>) : string[] => {
        const data = messages;
        let result: Message[] = [] as Message[];
        console.log(messages);
        const sendersArray : string[] = [] as string[];
        messages.forEach((value: Message[], key: string) => {
            sendersArray.push(key);
        })

        sendersArray.forEach(e => {
            let value = data.get(e)
                ?.filter(() => data.get(e)
                    ?.map(e => e.read)
                    .includes(false)) || [] as Message[];
            value = value.sort((a, b) => b.created - a.created);
            result.push(value[0]);
        });
        console.log(result);
        result = result.sort((a, b) => b.created - a.created);
        const usersWithUnread = [] as string[];
        messages.forEach((value: Message[], key: string) => {
            if(!result.map(t => t.sender).includes(key)){
                usersWithUnread.push(key);
            }
        });
        return result.map(e => e.sender).concat(usersWithUnread);
    }

    public static readMessage = (message: Message) => {
        message.read = true;
        return axios.put<Message>(`${Environment.apiUrl}message/${message.id}`, message);
    }
}
