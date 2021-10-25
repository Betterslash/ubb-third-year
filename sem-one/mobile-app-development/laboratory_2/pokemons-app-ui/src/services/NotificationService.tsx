import {Storage} from "@capacitor/storage";
import {NotificationModel} from "../model/NotificationModel";

export class NotificationService {
    private static readonly NOTIFICATIONS_KEY : string = 'notifications';

    public static getNotifications(){
        return Storage.get({key: this.NOTIFICATIONS_KEY});
    }

    public static addNotification(notification : NotificationModel){
        return Storage.get({key : this.NOTIFICATIONS_KEY}).then(async result => {
            if (result.value) {
                const currentNotifications = JSON.parse(result.value) as NotificationModel[];
                const newState = currentNotifications.filter(e => e.id !== notification.id);
                newState.push(notification);
                await Storage.set({key: this.NOTIFICATIONS_KEY, value: JSON.stringify(newState)});
                return currentNotifications;
            }else{
                const currentNotifications = [] as NotificationModel[];
                currentNotifications.push(notification);
                await Storage.set({key: this.NOTIFICATIONS_KEY, value: JSON.stringify(currentNotifications)});
                return currentNotifications;
            }
        });
    }

    public static deleteNotification(notificationId: string){
        return Storage.get({key : this.NOTIFICATIONS_KEY}).then(async result => {
            if (result.value) {
                const currentNotifications = JSON.parse(result.value) as NotificationModel[];
                const newState = currentNotifications.filter(e => e.id !== notificationId);
                await Storage.set({key: this.NOTIFICATIONS_KEY, value: JSON.stringify(newState)});
                return currentNotifications;
            }
        });
    }
}
