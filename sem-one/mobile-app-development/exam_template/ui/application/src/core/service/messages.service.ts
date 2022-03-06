import ApiService from "./api.service";
import {Environment} from "../environment/Environment";
import {MessageModel} from "../model/message.model";
import axios from "axios";

export class MessagesService{
    public static getMessagesForSender = (sender: string) => {
        return ApiService.get<MessageModel[]>(`${Environment.apiUrl}message?sender=${sender}`);
    }

    public static deleteOne = (messageId: number) => {
        return axios.delete(`${Environment.apiUrl}message/${messageId}`);
    }
}
